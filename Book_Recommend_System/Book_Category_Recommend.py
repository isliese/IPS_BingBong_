import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

class BookCategoryRecommender:
    def __init__(self, csv_file):
        # CSV 파일을 읽어서 DataFrame 생성
        self.filtered_df = pd.read_csv(csv_file)
        # TF-IDF 벡터라이저 생성
        self.vectorizer = TfidfVectorizer()
        # 카테고리 설명을 벡터화
        self.X = self.vectorizer.fit_transform(self.filtered_df['Category_Description'])

    def find_similar_category(self, input_word):
        input_vec = self.vectorizer.transform([input_word])
        similarities = cosine_similarity(input_vec, self.X)
        most_similar_index = similarities.argmax()
        return self.filtered_df.iloc[most_similar_index]['CID'], self.filtered_df.iloc[most_similar_index]['Category_Description']

