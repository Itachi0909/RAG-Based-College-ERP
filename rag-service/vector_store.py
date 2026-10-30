
import chromadb
from pathlib import Path

# Store ChromaDB data permanently on disk.
CHROMA_DIR = Path("chroma_db")
CHROMA_DIR.mkdir(parents=True, exist_ok=True)

client = chromadb.PersistentClient(
    path=str(CHROMA_DIR)
)

# Create the collection if it doesn't already exist.
collection = client.get_or_create_collection(
    name="rcet_documents",
    metadata={"hnsw:space": "cosine"},
)



def store_chunks(
    document_id: str,
    filename: str,
    chunks: list[str],
    embeddings: list[list[float]],
    page_numbers: list[int | None],
) -> int:
    """
    Store document chunks, embeddings, and source metadata.
    """

    if len(chunks) != len(embeddings):
        raise ValueError(
            "The number of chunks must match the number of embeddings."
        )

    if len(chunks) != len(page_numbers):
        raise ValueError(
            "The number of chunks must match the number of page numbers."
        )

    if not chunks:
        return 0

    ids = [
        f"{document_id}_chunk_{index}"
        for index in range(len(chunks))
    ]

    metadatas = [
        {
            "document_id": document_id,
            "filename": filename,
            "chunk_index": index,
            "page_number": page_numbers[index] if page_numbers[index] is not None else -1,
        }
        for index in range(len(chunks))
    ]

    collection.upsert(
        ids=ids,
        documents=chunks,
        embeddings=embeddings,
        metadatas=metadatas,
    )

    return len(chunks)
