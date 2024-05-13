import pandas as pd
from spotipy.oauth2 import SpotifyClientCredentials
import spotipy
import time

client_id = '7ae275c4289c4961aa84d3944ea547ff'
client_secret = '06cbce79bb0f474aae909c57e6001f45'

# client_id = '48afee7a60024f16bd9daa25c4358c64'
# client_secret = '0235c436a1bb48279bc80e1e18973224'

client_credentials_manager = SpotifyClientCredentials(client_id=client_id, client_secret=client_secret)
sp = spotipy.Spotify(client_credentials_manager=client_credentials_manager)

# 아티스트 upi 리스트 정리
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

# 아티스트 URI
lz_uri = 'spotify:artist:1z4g3DjTBBZKhvAroFlhOM' # 레드벨벳

## 정보를 담을 리스트 초기화 부분
# 각 정보를 담을 리스트 초기화
track_names = []
artist_names = []
artist_ids = []
cover_arts = []
#preview_urls = []

# 아티스트의 상위 10개 곡 정보 가져오기
results = sp.artist_top_tracks(lz_uri)

# 해당 아티스트 상위 10개 곡 정보를 리스트에 추가
for track in results['tracks'][:10]:
    track_names.append(track['name'])
    artist_names.append(track['artists'][0]['name'])
    artist_ids.append(track['artists'][0]['id'])
    cover_arts.append(track['album']['images'][0]['url'])
    #preview_urls.append(track['preview_url'])

    # 각 API 요청 사이에 1초의 딜레이를 추가
    time.sleep(1)



# 데이터프레임 생성
df = {
    'Track Name': track_names,
    'Artist Name': artist_names,
    'Artist ID': artist_ids,
    'Cover Art': cover_arts,
    #'Preview URL': preview_urls,
}

artist_df = pd.DataFrame(df)

# 데이터프레임을 엑셀 파일로 저장
artist_df.to_excel("redvelvet.xlsx", index=False)
print("엑셀 파일을 성공적으로 생성하였습니다.")
#df.to_excel("blackpink_top_tracks.xlsx", index=False)


# 데이터프레임을 JSON 파일로 저장
# df.to_json("blackpink_top_tracks.json", orient="records")
# print("json 파일을 성공적으로 생성하였습니다.")



# 각 정보가 담긴 리스트 출력
print("Track Names:", track_names[0])
print("Artist Names:", artist_names[0])
print("Artist IDs:", artist_ids[0])
print("Cover Arts:", cover_arts[0])
#pnt("Preview URLs:", preview_urls[0])
