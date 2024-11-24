# /// script
# requires-python = ">=3.12"
# dependencies = [
#     "pandas>=2.0.0",
#     "pymongo>=4.6.0",
#     "requests>=2.31.0"
# ]
# ///

import logging
import pandas as pd
import requests
from pymongo import MongoClient

# Logging configuration
logging.basicConfig(level=logging.INFO, format="%(asctime)s - %(levelname)s - %(message)s")
logger = logging.getLogger()

def main():
    # Connecting to MongoDB
    collection = connect_to_mongodb()

    # Loading movies from pickle
    movies_df = pd.read_pickle("../artifacts/movies_list.pkl")
    movies_list = pd.DataFrame(movies_df).to_dict("records")

    logger.info(f"Movies loaded with a total of {len(movies_list)} records.")

    # Adding poster URLs to the `poster_url` field
    count_posters = 0
    logger.info("Adding poster URLs to documents...")
    for movie in movies_list:
        try:
            movie_id = movie.get("movie_id")
            if movie_id:
                movie["poster_url"] = fetch_poster(movie_id)
                count_posters += 1
            else:
                movie["poster_url"] = None
        except Exception as e:
            logger.warning(f"Failed to fetch poster for the movie {movie}: {e}")
            movie["poster_url"] = None

    logger.info(f"Total posters added: {count_posters}")
    logger.info(f"Total missing posters: {len(movies_list) - count_posters}")

    # Inserting in batches
    batch_size = 500
    logger.info(f"Inserting movies in batches of {batch_size}...")

    for i in range(0, len(movies_list), batch_size):
        batch = movies_list[i:i + batch_size]
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

def fetch_poster(movie_id):
    """
    Fetches the full poster path of a movie by ID using the TMDB API.
    """
    try:
        url = f""
        response = requests.get(url)
        response.raise_for_status()
        data = response.json()
        poster_path = data.get("poster_path")
        if poster_path:
            return f"https://image.tmdb.org/t/p/w500{poster_path}"
        else:
            return None
    except Exception as e:
        logger.error(f"Error fetching poster for movie_id {movie_id}: {e}")
        return None

if __name__ == "__main__":
    main()
