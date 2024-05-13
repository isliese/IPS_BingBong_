from spotipy.oauth2 import SpotifyClientCredentials
import spotipy
import time

client_id = '7ae275c4289c4961aa84d3944ea547ff'
client_secret = '06cbce79bb0f474aae909c57e6001f45'

client_credentials_manager = SpotifyClientCredentials(client_id=client_id, client_secret=client_secret)
sp = spotipy.Spotify(client_credentials_manager=client_credentials_manager)

# 주어진 아티스트 URI 리스트
artist_uris = [
    'spotify:artist:41MozSoPIsD1dJM0CLPjZF', # 찰리푸스
    'spotify:artist:41MozSoPIsD1dJM0CLPjZF', # 블랙핑크
    'spotify:artist:7n2Ycct7Beij7Dj7meI4X0'  # 트와이스
]

# 각 정보를 담을 리스트 초기화
track_names = []
artist_names = []
artist_ids = []
cover_arts = []
preview_urls = []

# 모든 아티스트에 대한 상위 곡 정보 가져오기
all_top_tracks = [sp.artist_top_tracks(artist_uri)['tracks'][:10] for artist_uri in artist_uris]

# 각 아티스트의 상위 곡 정보를 순회하며 결과 리스트에 추가
for top_tracks in all_top_tracks:
    for track in top_tracks:
        track_names.append(track['name'])
        artist_names.append(track['artists'][0]['name'])
        artist_ids.append(track['artists'][0]['id'])
        cover_arts.append(track['album']['images'][0]['url'])

        # API 요청 사이에 1초 딜레이 추가
        time.sleep(1)


# 결과 출력
print("Track Names:", track_names)
print("Artist Names:", artist_names)
print("Artist IDs:", artist_ids)
print("Cover Arts:", cover_arts)


"""
artist_uris = ['spotify:artist:41MozSoPIsD1dJM0CLPjZF', # 블랙핑크
              'spotify:artist:7n2Ycct7Beij7Dj7meI4X0',  # 트와이스
              'spotify:artist:1z4g3DjTBBZKhvAroFlhOM',  # 레드벨벳
              'spotify:artist:6HvZYsbFfjnjFrWF950C9d',  # 뉴진스
              'spotify:artist:6RHTUrRF63xao58xh9FXYJ',  # 아이브
              'spotify:artist:4SpbR6yFEvexJuaBpgAU5p',  # 르세라핌
              'spotify:artist:6YVMFz59CuY7ngCxTxjpxE',  # 에스파
              'spotify:artist:2AfmfGFbe0A0WsTYm0SDTx', # 아이들
              'spotify:artist:28ot3wh4oNmoFOdVajibBl', # 엔믹스
              'spotify:artist:3Nrfpe0tUJi4K4DXYWgMUX', # 방탄소년단
              'spotify:artist:2dIgFjalVxs4ThymZ67YCE', # 스트레이키즈
              'spotify:artist:7nqOGRxlXj7N2JYbgNEjYH', # 엔하이픈
              'spotify:artist:5t5FqBwTcgKTaWmfEbwQY9', # 세븐틴
              'spotify:artist:1gBUSTR3TyDdTVFIaQnc02', # 엔시티드림
              'spotify:artist:3cjEqqelV9zb4BYE3qDQ4O', # 엑소
              'spotify:artist:4Kxlr1PRlDKEB0ekOCyHgX'] # 빅뱅
"""
