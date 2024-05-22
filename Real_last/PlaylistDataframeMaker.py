import pandas as pd
import spotipy
from spotipy.oauth2 import SpotifyClientCredentials
from spotipy.exceptions import SpotifyException
from datetime import datetime
import os
import shutil

class PlaylistDataframeMaker:
    def __init__(self, client_id, client_secret):
        self.client_id = client_id
        self.client_secret = client_secret
        self.sp = spotipy.Spotify(client_credentials_manager=SpotifyClientCredentials(client_id=self.client_id, client_secret=self.client_secret))
        self.save_dir = os.path.abspath('Real_last/init_processing')
        self.finish_dir = os.path.abspath('Real_last/finish_processing')

    def search_playlist_uris_by_keyword(self, keyword, limit=4):
        results = self.sp.search(q=keyword, type='playlist', limit=limit)
        playlist_uris = [playlist['uri'] for playlist in results['playlists']['items']]
        return playlist_uris

    def get_playlist_urls_with_description(self, playlist_uris, keyword):
        playlist_infos = []
        for uri in playlist_uris:
            playlist_info = self.sp.playlist(uri)
            followers = playlist_info['followers']['total']
            last_update = playlist_info['tracks']['items'][0]['added_at'] if playlist_info['tracks']['items'] else None
            description = playlist_info.get('description', '')

            if keyword.lower() in description.lower():
                keyword_found = True
            else:
                keyword_found = False

            playlist_infos.append({
                'url': playlist_info['external_urls']['spotify'],
                'followers': followers,
                'last_update': last_update,
                'description': description,
                'keyword_found': keyword_found
            })
            
        return playlist_infos

    def sort_playlists(self, playlist_infos, keyword):
        playlists_with_keyword = [info for info in playlist_infos if info['keyword_found']]
        playlists_without_keyword = [info for info in playlist_infos if not info['keyword_found']]

        if playlists_with_keyword:
            sorted_playlists = sorted(playlists_with_keyword, key=lambda x: x['followers'], reverse=True)
            sorted_playlists.extend(sorted(playlists_without_keyword, key=lambda x: x['followers'], reverse=True))
        else:
            sorted_playlists = sorted(playlist_infos, key=lambda x: x['followers'], reverse=True)
        return sorted_playlists

    def get_dataframe_from_playlist(self, playlist_link, keyword):
        playlist_URI = playlist_link.split("/")[-1].split("?")[0].split("&")[0]
        try:
            playlist_tracks = self.sp.playlist_tracks(playlist_URI, limit=40)["items"]
        except SpotifyException as e:
            print(f"Error occurred while fetching playlist {playlist_link}: {e}")
            return pd.DataFrame()

        df_list = []
        for item in playlist_tracks:
            track = item["track"]
            features = self.sp.audio_features(track["uri"])[0]
            if features is None:
                print(f"No features found for track: {track['name']} by {track['artists'][0]['name']}")
                continue

            artist_info = self.sp.artist(track["artists"][0]["uri"])

            df_list.append({
                'keyword': keyword,
                'valence': features["valence"],
                'year': track["album"]["release_date"][:4],
                'acousticness': features["acousticness"],
                'artists': ', '.join([artist['name'] for artist in track['artists']]),
                'danceability': features["danceability"],
                'duration_ms': features["duration_ms"],
                'energy': features["energy"],
                'explicit': track["explicit"],
                'id': track["id"],
                'instrumentalness': features["instrumentalness"],
                'key': features["key"],
                'liveness': features["liveness"],
                'loudness': features["loudness"],
                'mode': features["mode"],
                'name': track["name"],
                'popularity': track["popularity"],
                'release_date': track["album"]["release_date"],
                'speechiness': features["speechiness"],
                'tempo': features["tempo"]
            })
        return pd.DataFrame(df_list)

    def save_dataframe_to_csv(self, df, keyword):
        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
        filename = f"{keyword}_tracks_{timestamp}.csv"
        file_path = os.path.join(self.save_dir, filename)
        df.to_csv(file_path, index=False)
        print(f"Data saved to {file_path}")

    def search_and_save_playlists(self, keyword, limit=5):
        predefined_keywords = ["두려움", "놀람", "화남", "슬픔", "행복", "불쾌"]

        # Check for the presence of files containing the keyword in finish_processing
        files_in_finish = os.listdir(self.finish_dir)
        matching_files = [f for f in files_in_finish if keyword in f]

        if keyword in predefined_keywords and matching_files:
            for file in matching_files:
                finish_file_path = os.path.join(self.finish_dir, file)
                init_file_path = os.path.join(self.save_dir, file)
                shutil.copy(finish_file_path, init_file_path)
                print(f"Copied {finish_file_path} to {init_file_path}")
            return  # Skip further processing

        playlist_uris = self.search_playlist_uris_by_keyword(keyword, limit)
        playlist_infos = self.get_playlist_urls_with_description(playlist_uris, keyword)
        sorted_playlists = self.sort_playlists(playlist_infos, keyword)
        top_playlist_url = sorted_playlists[0]['url']
        df = self.get_dataframe_from_playlist(top_playlist_url, keyword)
        self.save_dataframe_to_csv(df, keyword)


# 테스트 코드
if __name__ == "__main__":
    client_id = '7ae275c4289c4961aa84d3944ea547ff'
    client_secret = '06cbce79bb0f474aae909c57e6001f45'
    keyword = input("검색할 키워드를 입력하세요: ")
    playlist_maker = PlaylistDataframeMaker(client_id, client_secret)
    playlist_maker.search_and_save_playlists(keyword)
