import os
import pandas as pd
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
import shutil
import spotipy
from datetime import datetime
from spotipy.oauth2 import SpotifyClientCredentials
from joblib import load

class RecommendSystem:
    def __init__(self, user_name, init_directory_path, finish_directory_path, spotify_data_path, model_path, client_id, client_secret):
        self.user_name = user_name
        self.init_directory_path = init_directory_path
        self.finish_directory_path = finish_directory_path
        self.user_directory_path = 'Real_last/User_Song_Recommend'
        self.spotify_data_path = spotify_data_path
        self.spotify_data = pd.read_csv(spotify_data_path)
        self.model = load(model_path)
        self.client_id = client_id
        self.client_secret = client_secret
        self.sp = spotipy.Spotify(client_credentials_manager=SpotifyClientCredentials(client_id=self.client_id, client_secret=self.client_secret))
        self.number_cols = ['valence', 'year', 'acousticness', 'danceability', 'duration_ms', 'energy', 'explicit',
                            'instrumentalness', 'key', 'liveness', 'loudness', 'mode', 'popularity', 'speechiness', 'tempo']

    def get_song_info(self, song_name, artist_name):
        query = f"track:{song_name} artist:{artist_name}"
        result = self.sp.search(q=query, type='track', limit=1)
        if result['tracks']['items']:
            track = result['tracks']['items'][0]
            song_url = track['external_urls']['spotify']
            image_url = track['album']['images'][0]['url'] if track['album']['images'] else None
            return song_url, image_url
        else:
            return None, None

    def get_mean_vector(self, song_list, number_cols):
        song_vectors = [self.spotify_data[self.spotify_data['name'] == song['name']][number_cols].values for song in song_list if not self.spotify_data[self.spotify_data['name'] == song['name']].empty]
        if not song_vectors:
            return np.zeros(len(number_cols))
        else:
            song_matrix = np.array([vector[0] for vector in song_vectors])
            return np.mean(song_matrix, axis=0)

    def flatten_dict_list(self, dict_list):
        names = [d['name'] for d in dict_list]
        return {'name': names}

    def recommend_songs_from_playlist(self, playlist_df, number_cols, n_songs=20):
        metadata_cols = ['name', 'artists']
        song_list = playlist_df.to_dict(orient='records')
        scaler = self.model.steps[0][1]
        song_center = self.get_mean_vector(song_list, number_cols)
        rec_songs = pd.DataFrame()

        if 'cluster_label' in playlist_df.columns:
            cluster_label = playlist_df['cluster_label'].mode().iloc[0]
            cluster_songs = self.spotify_data[self.spotify_data['cluster_label'] == cluster_label]
            scaled_data = scaler.transform(cluster_songs[number_cols])
            scaled_song_center = scaler.transform(pd.DataFrame([song_center], columns=number_cols))
            similarities = cosine_similarity(scaled_song_center, scaled_data)[0]
            index = np.argsort(similarities)[::-1]
            rec_songs = cluster_songs.iloc[index]
            rec_songs = rec_songs[~rec_songs['name'].isin(self.flatten_dict_list(song_list)['name'])]
            rec_songs = rec_songs.head(n_songs)

        if rec_songs.empty:
            print("Warning: No unique recommendations found. Recommending additional songs based on similarity.")
            additional_songs = self.spotify_data.iloc[np.argsort(cosine_similarity(scaled_song_center, scaler.transform(self.spotify_data[number_cols]))[0])[::-1][:n_songs]]
            additional_songs = additional_songs[~additional_songs['name'].isin(self.flatten_dict_list(song_list)['name'])]
            rec_songs = pd.concat([rec_songs, additional_songs])

        return rec_songs[metadata_cols].to_dict(orient='records')

    def process_recommendations(self):
        csv_files = [f for f in os.listdir(self.init_directory_path) if f.endswith('.csv')]
        all_recommended_songs = []
        current_date = datetime.now().strftime('%Y-%m-%d')

        for csv_file in csv_files:
            file_path = os.path.join(self.init_directory_path, csv_file)
            playlist_df = pd.read_csv(file_path)

            if 'keyword' in playlist_df.columns:
                keyword = playlist_df['keyword'].iloc[0]
            else:
                keyword = 'Unknown'

            recommended_songs = self.recommend_songs_from_playlist(playlist_df, self.number_cols, n_songs=1)

            for song in recommended_songs:
                song_url, image_url = self.get_song_info(song['name'], song['artists'])
                song['url'] = song_url
                song['image'] = image_url
                song['keyword'] = keyword
                # song['date'] = current_date
                song['date'] = '2024-05-23'

                # 추천한 곡을 데이터에서 제거
                self.spotify_data = self.spotify_data[self.spotify_data['name'] != song['name']]

            dictionary_data = {
                'SongDate' : current_date,
                'SongKeyword' : keyword,
                'SongTitle' : song['name'],
                'SongWriter' : song['artists'],
                'SongImage' : image_url, 
                'SongUrl' : song_url
            }

            all_recommended_songs.extend(recommended_songs)
            finish_file_path = os.path.join(self.finish_directory_path, csv_file)
            shutil.move(file_path, finish_file_path)

        all_recommended_songs_file_path = os.path.join(self.user_directory_path, f'{self.user_name}_songs.csv')
        if os.path.exists(all_recommended_songs_file_path):
            existing_recommended_songs = pd.read_csv(all_recommended_songs_file_path)
            all_recommended_songs_df = pd.DataFrame(all_recommended_songs)
            all_recommended_songs_df = all_recommended_songs_df[['date', 'keyword'] + [col for col in all_recommended_songs_df.columns if col not in ['date', 'keyword']]]
            combined_recommended_songs = pd.concat([existing_recommended_songs, all_recommended_songs_df])
        else:
            combined_recommended_songs = pd.DataFrame(all_recommended_songs)
            combined_recommended_songs = combined_recommended_songs[['date', 'keyword'] + [col for col in combined_recommended_songs.columns if col not in ['date', 'keyword']]]

        combined_recommended_songs.to_csv(all_recommended_songs_file_path, index=False)

        # 데이터에서 추천한 곡을 제거한 후 다시 저장
        self.spotify_data.to_csv(self.spotify_data_path, index=False)

        for song in all_recommended_songs:
            print(f"Date: {song['date']}, Keyword: {song['keyword']}, Name: {song['name']}, Artists: {song['artists']}, URL: {song['url']}, Image: {song['image']}")

        return dictionary_data

# 테스트 코드
if __name__ == "__main__":
    user_name = input("이름을 입력하세요 : ")
    init_directory_path = 'Real_last/init_processing'
    finish_directory_path = 'Real_last/finish_processing'
    spotify_data_path = 'Real_last/Data_and_Model/processing_data_processed.csv'
    model_path = 'Real_last/Data_and_Model/song_cluster_pipeline2.pkl'
    
    client_id = '7ae275c4289c4961aa84d3944ea547ff'
    client_secret = '06cbce79bb0f474aae909c57e6001f45'

    recommend_system = RecommendSystem(user_name, init_directory_path, finish_directory_path, spotify_data_path, model_path, client_id, client_secret)
    recommend_system.process_recommendations()
