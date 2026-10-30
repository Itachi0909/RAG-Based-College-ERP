
import os

from dotenv import load_dotenv
from google import genai
from google.genai import types

from vector_store import collection

load_dotenv()

api_key = os.getenv("GEMINI_API_KEY")

if not api_key:
    raise RuntimeError("GEMINI_API_KEY not found.")

client = genai.Client(api_key=api_key)


def retrieve_chunks(
    question: str,
    top_k: int = 3,
    document_id: str | None = None,
) -> list[dict]:

    if not question.strip():
        raise ValueError("Question cannot be empty.")

    if top_k <= 0:
        raise ValueError("top_k must be greater than zero.")

    # Convert the question into an embedding.
    response = client.models.embed_content(
        model="gemini-embedding-001",
        contents=question,
        config=types.EmbedContentConfig(
            task_type="RETRIEVAL_QUERY",
        ),
    )

    query_embedding = response.embeddings[0].values

    # Prepare optional document filtering.
    query_filter = None

    if document_id:
        query_filter = {
            "document_id": document_id
        }

    # Search ChromaDB for similar document chunks.
    results = collection.query(
        query_embeddings=[query_embedding],
        n_results=top_k,
        where=query_filter,
        include=[
            "documents",
            "metadatas",
            "distances",
        ],
    )

    retrieved_chunks = []

    if not results["documents"]:
        return retrieved_chunks

    for index, document in enumerate(results["documents"][0]):

        metadata = results["metadatas"][0][index]
        distance = results["distances"][0][index]

        retrieved_chunks.append({
            "text": document,
            "metadata": metadata,
            "distance": distance,
        })

    return retrieved_chunks
