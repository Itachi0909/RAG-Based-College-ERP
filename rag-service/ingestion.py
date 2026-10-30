import json
import os
from pathlib import Path

from dotenv import load_dotenv
from google import genai
from google.genai import types
from pypdf import PdfReader
from docx import Document

from text_chunker import chunk_text
from vector_store import store_chunks

load_dotenv()

api_key = os.getenv("GEMINI_API_KEY")

if not api_key:
    raise RuntimeError("GEMINI_API_KEY was not found in .env")

client = genai.Client(api_key=api_key)

UPLOAD_DIR = Path("uploaded_documents")



def extract_document_pages(file_path: Path) -> list[dict]:
    """Extract document text while preserving PDF page numbers."""

    if file_path.suffix.lower() == ".pdf":
        reader = PdfReader(str(file_path))

        return [
            {
                "page_number": page_number,
                "text": page.extract_text() or "",
            }
            for page_number, page in enumerate(reader.pages, start=1)
        ]

    if file_path.suffix.lower() == ".docx":
        document = Document(str(file_path))

        parts = [
            paragraph.text
            for paragraph in document.paragraphs
            if paragraph.text.strip()
        ]

        for table in document.tables:
            for row in table.rows:
                parts.append(
                    " | ".join(cell.text.strip() for cell in row.cells)
                )

        return [
            {
                "page_number": None,
                "text": "\n".join(parts),
            }
        ]

    raise ValueError("Only PDF and DOCX files are supported.")



def ingest_document(document_id: str) -> dict:
    """Extract, chunk, embed, and store an uploaded document."""

    pdf_path = UPLOAD_DIR / f"{document_id}.pdf"
    docx_path = UPLOAD_DIR / f"{document_id}.docx"

    if pdf_path.is_file():
        file_path = pdf_path
    elif docx_path.is_file():
        file_path = docx_path
    else:
        raise FileNotFoundError("Uploaded document not found.")

    # Read original filename from metadata JSON.
    metadata_path = UPLOAD_DIR / f"{document_id}.json"

    if metadata_path.is_file():
        metadata = json.loads(
            metadata_path.read_text(encoding="utf-8")
        )
        original_filename = metadata["original_filename"]
    else:
        original_filename = file_path.name

    # Extract document text while preserving page numbers.
    pages = extract_document_pages(file_path)

    all_chunks = []
    page_numbers = []

    for page in pages:
        page_text = page["text"].strip()

        if not page_text:
            continue

        page_chunks = chunk_text(
            page_text,
            chunk_size=1000,
            overlap=200,
        )

        all_chunks.extend(page_chunks)

        page_numbers.extend(
            [page["page_number"]] * len(page_chunks)
        )

    if not all_chunks:
        raise ValueError("No extractable text found in the document.")

    # Generate embeddings.
    embedding_response = client.models.embed_content(
        model="gemini-embedding-001",
        contents=all_chunks,
        config=types.EmbedContentConfig(
            task_type="RETRIEVAL_DOCUMENT",
        ),
    )

    embeddings = [
        item.values
        for item in embedding_response.embeddings
    ]

    if len(embeddings) != len(all_chunks):
        raise RuntimeError("Embedding count does not match chunk count.")

    # Store chunks, embeddings, and source metadata.
    stored_count = store_chunks(
        document_id=document_id,
        filename=original_filename,
        chunks=all_chunks,
        embeddings=embeddings,
        page_numbers=page_numbers,
    )

    return {
        "document_id": document_id,
        "filename": original_filename,
        "total_chunks": len(all_chunks),
        "stored_chunks": stored_count,
        "status": "INDEXED",
    }