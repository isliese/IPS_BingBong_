""" 
해당 클래스는 
spotipy에 함수의 아이디 비번을 설정하는 함수 (=__init__(self)),
트랙 정보 기반, 감정과 관련된 정보들을 가지고 오는 함수 (=spe_get_feature())
트랙 정보 기반, 모든 정보를 가지고 오는 함수 (= all_get_feature())
를 포함하는 spotipy api 기반의 클래스입니다.
"""
import spotipy
from spotipy.oauth2 import SpotifyClientCredentials

class Spotify_audio_features:
    def __init__(self):
        # 초기 설정
        cid = '7ae275c4289c4961aa84d3944ea547ff'
        secret = '06cbce79bb0f474aae909c57e6001f45'

        # 함수의 아이디와 비번을 설정하는 작업
        client_credentials_manager = SpotifyClientCredentials(client_id=cid, client_secret=secret)
        self.sp = spotipy.Spotify(client_credentials_manager=client_credentials_manager)
    
    
    # 트랙 정보를 기반으로, 감정 기반의 정보들만 가져오는 함수
    def get_features(self, song):
        # 트랙 정보를 입력하기
        track_info = self.sp.search(q=song, type='track', market='JP') # 여기서 q는 노래명을 입력, type은 track으로 진행
        track_id = track_info["tracks"]["items"][0]["id"]

        # 해당 노래의 특징을 가지고 오기
        features = self.sp.audio_features(tracks=[track_id])
        acousticness = features[0]["acousticness"]
        danceability = features[0]["danceability"]  # 춤추기에 적합한 노래일 수록 값이 높음
        energy = features[0]["energy"]      # 빠르고 화려하고 노이즈가 많은 음악
        liveness = features[0]["liveness"]
        loudness = features[0]["loudness"]  # 음악의 밝음 정도
        valence = features[0]["valence"]
        mode = features[0]["mode"]

        result = {"acousticness" : acousticness,
                    "danceability" : danceability,
                    "energy" : energy,
                    "liveness" : liveness,
                    "loudness" : loudness,
                    "valence" : valence,
                    "mode" : mode}

        return result