# 메인 함수 - 음악 추천 데이터 입력

from PlaylistDataframeMaker import PlaylistDataframeMaker
from DataframeProcessing import DataframeProcessing
from RecommendSystem import RecommendSystem

class MusicRecommendationSystem:
    def __init__(self, request_key):
        self.client_id = '7ae275c4289c4961aa84d3944ea547ff'
        self.client_secret = '06cbce79bb0f474aae909c57e6001f45'
        self.keyword = request_key

    def run(self):
        # 1. 플레이리스트 데이터 생성
        playlist_maker = PlaylistDataframeMaker(self.client_id, self.client_secret)
        playlist_maker.search_and_save_playlists(self.keyword)

        # 2. 데이터 전처리
        init_folder_path = 'Real_last/init_processing'
        processed_folder_path = 'processed_data'
        specific_file_path = 'Real_last/Data_and_Model/processing_data.csv'
        model_path = 'Real_last/Data_and_Model/song_cluster_pipeline2.pkl'
        clustering_output_path = 'Real_last/Data_and_Model/processing_data_processed.csv'

        dataframe_processor = DataframeProcessing(init_folder_path, processed_folder_path, specific_file_path, model_path, clustering_output_path)
        dataframe_processor.process_all()

        # 3. 추천 시스템 실행
        user_name = 'Song1212@gmail.com'
        user_name = user_name.split('@')[0]
        finish_directory_path = 'Real_last/finish_processing'
        spotify_data_path = clustering_output_path
        recommend_system = RecommendSystem(user_name, init_folder_path, finish_directory_path, spotify_data_path, model_path, self.client_id, self.client_secret)
        dictionary_data = recommend_system.process_recommendations()
        print(dictionary_data)
        return dictionary_data

def main():
    request_key = '슬픔'  # 리스트 형태로 진행하는 것도 가능함. (스포티파이 오류가 생기지 않도록)
    music_rec_system = MusicRecommendationSystem(request_key)
    music_rec_system.run()

if __name__ == "__main__":
    main()
