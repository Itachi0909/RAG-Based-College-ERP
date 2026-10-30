
def chunk_text(
    text: str,
    chunk_size: int = 1000,
    overlap: int = 200,
) -> list[str]:
    """
    Split text into smaller chunks with overlap.
    """

    # Validate the input parameters.
    if chunk_size <= 0:
        raise ValueError("chunk_size must be greater than zero.")

    if overlap < 0:
        raise ValueError("overlap cannot be negative.")

    if overlap >= chunk_size:
        raise ValueError("overlap must be smaller than chunk_size.")

    # Remove unnecessary whitespace.
    text = text.strip()

    if not text:
        return []

    chunks = []
    start = 0

    while start < len(text):
        end = min(start + chunk_size, len(text))
        chunk = text[start:end]
        chunks.append(chunk)
        
        if end == len(text):
            break
            
        start = end - overlap
        
    return chunks