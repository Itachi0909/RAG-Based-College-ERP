
from retrieval import retrieve_chunks
from answer_generator import generate_answer

question = "What is my address"

# Retrieve relevant document chunks.
results = retrieve_chunks(
    question=question,
    top_k=3,
)

print("\nRetrieved chunks:", len(results))

# Generate an answer using the retrieved context.
answer = generate_answer(
    question=question,
    retrieved_chunks=results,
)

print("\n========== RCET AI ASSISTANT ==========")
print("\nQuestion:", question)
print("\nAnswer:", answer)
print("\n======================================")
