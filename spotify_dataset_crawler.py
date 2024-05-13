import pandas as pd
import spotipy
from spotipy.oauth2 import SpotifyClientCredentials

client_id = '7ae275c4289c4961aa84d3944ea547ff'
client_secret = '06cbce79bb0f474aae909c57e6001f45'

# Authentication - without user
client_credentials_manager = SpotifyClientCredentials(client_id=client_id, client_secret=client_secret)
sp = spotipy.Spotify(client_credentials_manager=client_credentials_manager)

PLAYLIST_LINKS = [
    "https://open.spotify.com/playlist/37i9dQZEVXbNG2KDcFcKOF?si=1333723a6eff4b7f",
    "https://open.spotify.com/playlist/37i9dQZEVXbNxXF4SkHj9F",
    "https://open.spotify.com/playlist/37i9dQZF1DWV7EzJMK2FUI",
    "https://open.spotify.com/playlist/37i9dQZF1DWW46Vfs1oltB",
    "https://open.spotify.com/playlist/37i9dQZF1DXdTb8AG95jne",
    "https://open.spotify.com/playlist/37i9dQZF1DXe4qmDjDW0Ug",
    "https://open.spotify.com/playlist/37i9dQZF1DWUXxc8Mc6MmJ",
    "https://open.spotify.com/playlist/37i9dQZF1DX8j2fTnASZ3f",
    "https://open.spotify.com/playlist/37i9dQZF1DWUtMIvjJU4QQ"
]


def get_dataframe_from_playlist(playlist_link):
    playlist_URI = playlist_link.split("/")[-1].split("?")[0]
    track_uris = [x["track"]["uri"] for x in sp.playlist_tracks(playlist_URI)["items"]]

    df_list = []
    for item in sp.playlist_tracks(playlist_URI)["items"]:
        track = item["track"]

        features = sp.audio_features(track["uri"])[0]
        artist_info = sp.artist(track["artists"][0]["uri"])

        df_list.append({
            'artist_name': track["artists"][0]["name"],
            'track_name': track["name"],
            'album_name': track["album"]["name"],
            'artist_genre': artist_info["genres"],
            'artist_popularity': artist_info["popularity"],
            'track_popularity': track["popularity"],
            'artist_followers': artist_info["followers"]["total"],
            'danceability': features["danceability"],
            'energy': features["energy"],
            'key': features["key"],
            'loudness': features["loudness"],
            'speechiness': features["speechiness"],
            'acousticness': features["acousticness"],
            'instrumentalness': features["instrumentalness"],
            'liveness': features["liveness"],
            'valence': features["valence"],
            'tempo': features["tempo"],
            'duration_ms': features["duration_ms"],
            'time_signature': features["time_signature"],
            'uri': track["uri"],
            'release_date': track["album"]["release_date"],
            'album_image': track["album"]["images"][0]["url"],
            'id': track["id"]
        })

    return pd.DataFrame(df_list)


def save_dataframe_to_excel(df, filename):
    df.to_excel(filename, index=False)


def save_playlists(playlist_links):
    final_df = pd.DataFrame()

    for link in playlist_links:
        df = get_dataframe_from_playlist(link)
        final_df = pd.concat([final_df, df])

    final_df.drop_duplicates(subset=['uri'], inplace=True)

    save_dataframe_to_excel(final_df, "spotify_tracks.xlsx")


if __name__ == "__main__":
    save_playlists(PLAYLIST_LINKS)
