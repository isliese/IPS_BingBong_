# 곡이름을 넣으면, 해당 노래의 특징 요소를 가져오는 코드 => 실행 잘 됨.
import time
import pandas as pd
import spotipy
from spotipy.oauth2 import SpotifyClientCredentials
from Class_Spotipy_feature_Dataframe import Spotify_audio_features

client_id = '7ae275c4289c4961aa84d3944ea547ff'
client_secret = '06cbce79bb0f474aae909c57e6001f45'

client_credentials_manager = SpotifyClientCredentials(client_id=client_id, client_secret=client_secret)
sp = spotipy.Spotify(client_credentials_manager=client_credentials_manager)

# 엑셀 파일 불러오기
artist_df = pd.read_excel("aespa.xlsx")

# 특정 행 선택하여 리스트로 변환
row_datas = artist_df.iloc[0:, 0].tolist()  # 인덱스 행을 제외한 첫 번째 열 가지고 오기

## 정보를 담을 리스트 초기화 부분
# 각 곡에 대한 특징 데이터를 저장할 리스트 초기화
acousticness_list = []
danceability_list = []
energy_list = []
liveness_list = []
loudness_list = []
valence_list = []
mode_list = []


# 각 곡에 대한 특징 데이터를 가져와서 리스트에 추가
for row_data in row_datas:
    # 각 곡의 특징 데이터 가져오기
    saf = Spotify_audio_features()
    features = saf.get_features(row_data)
    acousticness_list.append(features["acousticness"])
    danceability_list.append(features["danceability"])
    energy_list.append(features["energy"])
    liveness_list.append(features["liveness"])
    loudness_list.append(features["loudness"])
    valence_list.append(features["valence"])
    mode_list.append(features["mode"])

    # 요청 제한 사항으로 인하여, sleep하기   
    time.sleep(1)

# 데이터프레임 생성
feature = {
    'Acousticness': acousticness_list,
    'Danceability': danceability_list,
    'Energy': energy_list,
    'Liveness': liveness_list,
    'Loudness': loudness_list,
    'Valence': valence_list,
    'Mode': mode_list
}
feature_df = pd.DataFrame(feature)

# 두 데이터 프레임을 합치기
combined_df = pd.concat([artist_df, feature_df], axis=1)

# 데이터프레임을 엑셀 파일로 저장
combined_df.to_excel("aespa_top_tracks.xlsx", index=False)
print("엑셀 파일을 성공적으로 생성하였습니다.")

# 각 정보가 담긴 리스트 출력
print(f"=== {row_datas[9]} ===")
print(f"Acousticness: {acousticness_list[9]}")
print(f"Danceability: {danceability_list[9]}")
print(f"Energy: {energy_list[9]}")
print(f"Liveness: {liveness_list[9]}")
print(f"Loudness: {loudness_list[9]}")
print(f"Valence: {valence_list[9]}")
print(f"Mode: {mode_list[9]}")
