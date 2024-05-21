from Book_Category_Recommend import BookCategoryRecommender
from Search_Book import BookSearcher

def main():
    # 사용자 입력 받기
    user_name = input("Enter your user name: ")
    user_name = user_name.split('@')[0]
    keyword = input("Enter a keyword: ")

    # BookCategoryRecommender 인스턴스 생성
    category_recommender = BookCategoryRecommender("aladin_Category_CID_20210927_filtered.csv")

    # 입력 단어에 대한 카테고리 ID와 설명을 가져옴
    cid, category_description = category_recommender.find_similar_category(keyword)

    # BookSearcher 인스턴스 생성
    book_searcher = BookSearcher(api_key='ttb77irene770053001')

    # 추천 도서 목록 받기
    recommended_books = book_searcher.recommend_books_by_keyword(keyword, cid)

    # 추천 도서가 있을 경우 CSV 파일 업데이트
    if recommended_books:
        book_searcher.update_csv_file(user_name, keyword, cid, recommended_books)

if __name__ == "__main__":
    main()
