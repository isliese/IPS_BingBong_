import os
import re
import pandas as pd
from joblib import load

class DataframeProcessing:
    def __init__(self, init_folder_path, processed_folder_path, specific_file_path, model_path, clustering_output_path):
        self.init_folder_path = init_folder_path
        self.processed_folder_path = processed_folder_path
        self.specific_file_path = specific_file_path
        self.model_path = model_path
        self.clustering_output_path = clustering_output_path
        self.number_cols = ['valence', 'year', 'acousticness', 'danceability', 'duration_ms', 'energy', 'explicit',
                            'instrumentalness', 'key', 'liveness', 'loudness', 'mode', 'popularity', 'speechiness', 'tempo']

    def process(self, str_):
        'preprocess titles to merge two tables'
        bullets = r'[-◇▲○■▶◆#※♢]'
        parentheses = r'[\[\]\(\)\<\>\{\}]'
        quotes = r'["\']'
        
        str_ = re.sub(quotes, '', str_)
        str_ = re.sub(bullets, '', str_)
        str_ = re.sub(parentheses, '', str_)

        parts = str_.rsplit(',', 1)
        if len(parts) > 1:
            str_ = re.sub(r',', '', parts[0]) + ',' + parts[1]
        else:
            str_ = parts[0]
        
        return str_

    def preprocess_csv_files(self):
        os.makedirs(self.processed_folder_path, exist_ok=True)

        for file_name in os.listdir(self.init_folder_path):
            if file_name.endswith('.csv'):
                file_path = os.path.join(self.init_folder_path, file_name)
                processing_data = pd.read_csv(file_path)
                processing_data['artists'] = processing_data['artists'].apply(self.process)
                processing_data['name'] = processing_data['name'].apply(self.process)
                
                processed_file_path = os.path.join(self.processed_folder_path, file_name)
                processing_data.to_csv(processed_file_path, index=False)
                
                print(f"Processed {file_name}: {processing_data.shape}")

    def preprocess_specific_file(self):
        processing_data = pd.read_csv(self.specific_file_path)
        processing_data['artists'] = processing_data['artists'].apply(self.process)
        processing_data['name'] = processing_data['name'].apply(self.process)
        processing_data.to_csv(self.specific_file_path, index=False)
        print(processing_data.shape)

    def align_data_types(self):
        for filename in os.listdir(self.init_folder_path):
            if filename.endswith(".csv"):
                file_path = os.path.join(self.init_folder_path, filename)
                df = pd.read_csv(file_path)

                if 'explicit' in df.columns:
                    if df['explicit'].dtype != bool:
                        df['explicit'] = df['explicit'].astype(int)
                        print(f"{filename} 파일의 'explicit' 열을 bool에서 int로 변환했습니다.")

                    if df['explicit'].dtype != 'int64':
                        df['explicit'] = df['explicit'].astype(int)
                        print(f"{filename} 파일의 'explicit' 열을 int로 변환했습니다.")

                if 'cluster_label' in df.columns and df['cluster_label'].dtype == 'int32':
                    df['cluster_label'] = df['cluster_label'].astype('int64')
                    print(f"{filename} 파일의 'cluster_label' 열을 int32에서 int64로 변환했습니다.")

                df.to_csv(file_path, index=False)

        print("모든 CSV 파일에 대한 전처리가 완료되었습니다.")

    def apply_clustering(self):
        song_cluster_pipeline2 = load(self.model_path)

        for filename in os.listdir(self.init_folder_path):
            if filename.endswith(".csv"):
                file_path = os.path.join(self.init_folder_path, filename)
                df = pd.read_csv(file_path)
                
                if 'cluster_label' in df.columns:
                    df.drop(columns=['cluster_label'], inplace=True)
                    print(f"'cluster_label' 열을 제거하고 {filename} 파일을 업데이트했습니다.")
                
                playlist_features = df[self.number_cols]
                df_cluster_labels = song_cluster_pipeline2.predict(playlist_features)
                df['cluster_label'] = df_cluster_labels
                
                df.to_csv(file_path, index=False)
                print(f"'cluster_label' 열을 {filename} 파일에 넣어서 업데이트 했습니다")

                df_modi = df.loc[:, (df.columns != 'keyword')]

                if 'keyword' in df.columns:
                    df_modi.to_csv(self.clustering_output_path, mode='a', header=False, index=False)
                    print(f"processing_data 파일의 행에 학습한 {filename} 파일을 붙여넣었습니다.")
                    print("--------------------------------------------------------------------------")
                else:
                    df.to_csv(self.clustering_output_path, mode='a', header=False, index=False)
                    print(f"processing_data 파일의 행에 학습한 {filename} 파일을 붙여넣었습니다.")
                    print("--------------------------------------------------------------------------")

    def remove_duplicates(self):
        df = pd.read_csv(self.clustering_output_path)
        df.drop_duplicates(subset=['id'], inplace=True)
        df.to_csv(self.clustering_output_path, index=False)

        print("중복된 행을 제거하여 CSV 파일을 업데이트했습니다.")
        print(df.info())
        print(df.shape)

    def process_all(self):
        self.preprocess_csv_files()
        self.preprocess_specific_file()
        self.align_data_types()
        self.apply_clustering()
        self.remove_duplicates()

        spotify_data = pd.read_csv(self.clustering_output_path)
        print("최종 업데이트된 CSV 파일을 다시 불러왔습니다:")
        print(spotify_data.head())

# 테스트 코드
if __name__ == "__main__":
    init_folder_path = 'Real_last/init_processing'
    processed_folder_path = 'processed_data'
    specific_file_path = 'Real_last/Data_and_Model/processing_data.csv'
    model_path = 'Real_last/Data_and_Model/song_cluster_pipeline2.pkl'
    clustering_output_path = 'Real_last/Data_and_Model/processing_data_processed.csv'

    dataframe_processor = DataframeProcessing(init_folder_path, processed_folder_path, specific_file_path, model_path, clustering_output_path)
    dataframe_processor.process_all()
