
from retrieval import retrieve_chunks

question = "What is my email?"

results = retrieve_chunks(
    question=question,
    top_k=1,
)

print("\nQuestion:", question)
print("\nRetrieved chunks:", len(results))

for index, result in enumerate(results, start=1):

    print(f"\n--- Result {index} ---")
    print("Text:", result["text"])
    print("Metadata:", result["metadata"])
    print("Distance:", result["distance"])
