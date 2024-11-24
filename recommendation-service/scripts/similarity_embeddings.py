# /// script
# requires-python = ">=3.12"
# dependencies = [
#     "pandas>=2.0.0",
#     "pymongo>=4.6.0"
# ]
# ///

import logging
import pandas as pd
from pymongo import MongoClient

# Logging configuration
logging.basicConfig(level=logging.INFO, format="%(asctime)s - %(levelname)s - %(message)s")
logger = logging.getLogger()

def main():
    # Connecting to MongoDB
    collection = connect_to_mongodb()

    # Load embeddings from a pickle file
    embeddings_df = pd.read_pickle("")
    logger.info(f"Embeddings loaded with shape: {embeddings_df.shape}")

    # Convert DataFrame to a list of dictionaries
    embeddings_list = embeddings_df.apply(
        lambda row: {"embedding_id": row.name, "vector": row.tolist()}, axis=1
    ).tolist()

    # Insert in batches
    batch_size = 1000
    logger.info(f"Inserting embeddings in batches of {batch_size}...")

    for i in range(0, len(embeddings_list), batch_size):
        batch = embeddings_list[i:i + batch_size]
        try:
            collection.insert_many(batch, ordered=False)
            logger.info(f"Batch {i // batch_size + 1}: {len(batch)} documents successfully inserted.")
        except Exception as e:
            logger.error(f"Error inserting batch {i // batch_size + 1}: {e}")

    logger.info("Processing complete.")

def connect_to_mongodb():
    """
    Connects to MongoDB and returns the collection.
    """
    client = MongoClient("")
    db = client[""]
    collection = db[""]
    logger.info("Connected to MongoDB")
    return collection

if __name__ == "__main__":
    main()
