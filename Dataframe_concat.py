import pandas as pd

"""
artist_list = [blackpink, twice, redvelvet, newjeans, ive, lesserafim, aespa, gidle, nmixx,
               bts, straykids, seventeen, enhypen, nctdream, exo, bigbang]
 """

# # 엑셀의 데이터프레임을 판다스를 이용하여 df로 불러오기
# newjeans_df = pd.read_excel('newjeans.xlsx')
# ive_df = pd.read_excel('ive.xlsx')
# lesserafim_df = pd.read_excel('lesserafim.xlsx')
# aespa_df = pd.read_excel('aespa.xlsx')
# gidle_df = pd.read_excel('gidle.xlsx')
# nmixx_df = pd.read_excel('nmixx.xlsx')

# bts_df = pd.read_excel('bts.xlsx')
# straykids_df = pd.read_excel('straykids.xlsx')
# seventeen_df = pd.read_excel('seventeen.xlsx')
# enhypen_df = pd.read_excel('enhypen.xlsx')
# nctdream_df = pd.read_excel('nctdream.xlsx')
# exo_df = pd.read_excel('exo.xlsx')
# bigbang_df = pd.read_excel('bigbang.xlsx')

G_top_tracks = pd.read_excel('G_top_tracks.xlsx')
B_top_tracks = pd.read_excel('B_top_tracks.xlsx')
redvelvet_top_tracks = pd.read_excel('redvelvet_top_tracks.xlsx')
twice_top_tracks = pd.read_excel('twice_top_tracks.xlsx')
blackpink_top_tracks = pd.read_excel('blackpink_top_tracks.xlsx')


# # 데이터프레임을 행 방향으로 병합
# data_merged = pd.concat([newjeans_df,ive_df,lesserafim_df,aespa_df,gidle_df,nmixx_df],ignore_index=True)
# data_merged2 = pd.concat([bts_df,straykids_df,seventeen_df,enhypen_df,nctdream_df,exo_df,bigbang_df],ignore_index=True)

data_merged3 = pd.concat([G_top_tracks,B_top_tracks,redvelvet_top_tracks,twice_top_tracks,blackpink_top_tracks],ignore_index=True)



# # 병합된 데이터 저장
# data_merged.to_excel('Ggroup_top_chart.xlsx')
# data_merged2.to_excel('Bgroup_top_chart.xlsx')
data_merged3.to_excel('Kpop_topsong.xlsx')
