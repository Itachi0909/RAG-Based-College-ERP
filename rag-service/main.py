
from pathlib import Path
from uuid import uuid4, UUID
from ingestion import ingest_document

from fastapi import FastAPI, File, HTTPException, UploadFile
from fastapi.middleware.cors import CORSMiddleware

from fastapi import FastAPI, File, HTTPException, UploadFile
from pypdf import PdfReader
from docx import Document
from io import BytesIO

from text_chunker import chunk_text

from pydantic import BaseModel, Field
from retrieval import retrieve_chunks
from answer_generator import generate_answer

app = FastAPI(
    title="RCET ERP RAG Service",
    description="AI chatbot for answering questions from uploaded ERP documents",
    version="1.0.0",
)

class ChatRequest(BaseModel):
    question: str = Field(..., min_length=1, max_length=2000)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:5173"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Folder where uploaded documents will be stored
UPLOAD_DIR = Path("uploaded_documents")
UPLOAD_DIR.mkdir(parents=True, exist_ok=True)

# Maximum file size: 10 MB
MAX_FILE_SIZE = 10 * 1024 * 1024

ALLOWED_EXTENSIONS = {".pdf", ".docx"}


@app.get("/")
def home():
    return {
        "message": "RCET ERP RAG Service is running!"
    }


@app.get("/health")
def health_check():
    return {
        "status": "healthy",
        "service": "rag-service",
    }


@app.post("/documents/upload")
async def upload_document(file: UploadFile = File(...)):
    if not file.filename:
        raise HTTPException(
            status_code=400,
            detail="No filename provided.",
        )

    # Validate file extension
    original_name = Path(file.filename).name
    extension = Path(original_name).suffix.lower()

    if extension not in ALLOWED_EXTENSIONS:
        raise HTTPException(
            status_code=400,
            detail="Only PDF and DOCX files are allowed.",
        )

    # Read at most 10 MB + 1 byte to detect oversized files
    content = await file.read(MAX_FILE_SIZE + 1)

    if not content:
        raise HTTPException(
            status_code=400,
            detail="The uploaded file is empty.",
        )

    if len(content) > MAX_FILE_SIZE:
        raise HTTPException(
            status_code=413,
            detail="File size must not exceed 10 MB.",
        )

    # Basic file signature validation
    if extension == ".pdf" and not content.startswith(b"%PDF-"):
        raise HTTPException(
            status_code=400,
            detail="The uploaded file does not appear to be a valid PDF.",
        )

    if extension == ".docx" and not content.startswith(b"PK"):
        raise HTTPException(
            status_code=400,
            detail="The uploaded file does not appear to be a valid DOCX file.",
        )

    # Generate a unique filename to avoid overwriting other uploads
    document_id = str(uuid4())
    stored_filename = f"{document_id}{extension}"
    file_path = UPLOAD_DIR / stored_filename

    try:
    # Save the uploaded document.
    file_path.write_bytes(content)

    # Save its original filename separately.
    metadata_path = UPLOAD_DIR / f"{document_id}.json"

    metadata_path.write_text(
        json.dumps({
            "document_id": document_id,
            "original_filename": original_name
        }),
        encoding="utf-8"
    )
    except OSError:
        raise HTTPException(
            status_code=500,
            detail="Could not save the uploaded document.",
        )

    return {
        "message": "Document uploaded successfully.",
        "document_id": document_id,
        "original_filename": original_name,
        "stored_filename": stored_filename,
        "size_bytes": len(content),
        "status": "UPLOADED",
    }

    
@app.post("/documents/{document_id}/extract")
def extract_document_text(document_id: str):
    # Validate the document ID before constructing a file path.
    try:
        document_id = str(UUID(document_id))
    except ValueError:
        raise HTTPException(
            status_code=400,
            detail="Invalid document ID.",
        )

    # Locate the uploaded document using its generated UUID.
    pdf_path = UPLOAD_DIR / f"{document_id}.pdf"
    docx_path = UPLOAD_DIR / f"{document_id}.docx"

    if pdf_path.is_file():
        file_path = pdf_path
        file_type = "PDF"
    elif docx_path.is_file():
        file_path = docx_path
        file_type = "DOCX"
    else:
        raise HTTPException(
            status_code=404,
            detail="Document not found. Upload the document first.",
        )

    try:
        if file_type == "PDF":
            reader = PdfReader(str(file_path))

            pages = []
            for page_number, page in enumerate(reader.pages, start=1):
                page_text = page.extract_text() or ""

                pages.append({
                    "page": page_number,
                    "text": page_text,
                })

            extracted_text = "\n\n".join(
                page["text"] for page in pages
            ).strip()

            page_count = len(reader.pages)

        else:
            document = Document(str(file_path))

            # Extract ordinary paragraphs.
            text_parts = [
                paragraph.text
                for paragraph in document.paragraphs
                if paragraph.text.strip()
            ]

            # Also extract text from DOCX tables.
            for table in document.tables:
                for row in table.rows:
                    cells = [
                        cell.text.strip()
                        for cell in row.cells
                    ]
                    text_parts.append(" | ".join(cells))

            extracted_text = "\n".join(text_parts).strip()
            page_count = None
            pages = None

    except Exception:
        raise HTTPException(
            status_code=400,
            detail="Could not extract text. The document may be corrupted or invalid.",
        )

    if not extracted_text:
        raise HTTPException(
            status_code=422,
            detail=(
                "No extractable text found. "
                "The document may be empty or contain scanned images only."
            ),
        )

    response = {
        "message": "Text extracted successfully.",
        "document_id": document_id,
        "file_type": file_type,
        "character_count": len(extracted_text),
        "extracted_text": extracted_text,
    }

    if file_type == "PDF":
        response["page_count"] = page_count
        response["pages"] = pages

    return response


@app.post("/documents/{document_id}/chunks")
def get_document_chunks(document_id: str):
    # Validate the document ID.
    try:
        document_id = str(UUID(document_id))
    except ValueError:
        raise HTTPException(
            status_code=400,
            detail="Invalid document ID.",
        )

    # Locate the uploaded document.
    pdf_path = UPLOAD_DIR / f"{document_id}.pdf"
    docx_path = UPLOAD_DIR / f"{document_id}.docx"

    if pdf_path.is_file():
        file_path = pdf_path
        file_type = "PDF"
    elif docx_path.is_file():
        file_path = docx_path
        file_type = "DOCX"
    else:
        raise HTTPException(
            status_code=404,
            detail="Document not found.",
        )

    try:
        # Extract text from the document.
        if file_type == "PDF":
            reader = PdfReader(str(file_path))
            text = "\n\n".join(
                page.extract_text() or ""
                for page in reader.pages
            ).strip()
        else:
            document = Document(str(file_path))

            text_parts = [
                paragraph.text
                for paragraph in document.paragraphs
                if paragraph.text.strip()
            ]

            for table in document.tables:
                for row in table.rows:
                    text_parts.append(
                        " | ".join(cell.text.strip() for cell in row.cells)
                    )

            text = "\n".join(text_parts).strip()

    except Exception:
        raise HTTPException(
            status_code=400,
            detail="Could not extract text from the document.",
        )

    if not text:
        raise HTTPException(
            status_code=422,
            detail="No extractable text found in the document.",
        )

    # Split the extracted text into chunks.
    chunks = chunk_text(
        text,
        chunk_size=1000,
        overlap=200,
    )

    return {
        "message": "Document chunked successfully.",
        "document_id": document_id,
        "file_type": file_type,
        "total_chunks": len(chunks),
        "chunks": [
            {
                "chunk_index": index,
                "character_count": len(chunk),
                "text": chunk,
            }
            for index, chunk in enumerate(chunks, start=1)
        ],
    }
    

@app.post("/documents/{document_id}/ingest")
def ingest_uploaded_document(document_id: str):
    # Validate the document ID.
    try:
        document_id = str(UUID(document_id))
    except ValueError:
        raise HTTPException(
            status_code=400,
            detail="Invalid document ID.",
        )

    try:
        result = ingest_document(document_id)
        return {
            "message": "Document processed and indexed successfully.",
            **result,
        }

    except FileNotFoundError as error:
        raise HTTPException(
            status_code=404,
            detail=str(error),
        )

    except ValueError as error:
        raise HTTPException(
            status_code=422,
            detail=str(error),
        )

    except Exception:
        raise HTTPException(
            status_code=500,
            detail=(
                "Document ingestion failed. Check the server terminal "
                "for the underlying error."
            ),
        )

@app.post("/chat")
def chat(request: ChatRequest):

    question = request.question.strip()

    if not question:
        raise HTTPException(
            status_code=400,
            detail="Question cannot be empty."
        )

    # Step 1: Retrieve relevant chunks
    retrieved_chunks = retrieve_chunks(
        question=question,
        top_k=3
    )

    # Step 2: Generate an answer using Gemini
    answer = generate_answer(
        question=question,
        retrieved_chunks=retrieved_chunks
    )

    # Step 3: Prepare source information
    sources = []

    for chunk in retrieved_chunks:
        metadata = chunk["metadata"]

        sources.append({
            "filename": metadata.get("filename"),
            "chunk_index": metadata.get("chunk_index"),
            "distance": chunk.get("distance")
        })

    return {
        "question": question,
        "answer": answer,
        "sources": sources,
        "retrieved_chunks": len(retrieved_chunks)
    }