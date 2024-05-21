import requests
import csv
import os
from datetime import datetime

class BookSearcher:
    def __init__(self, api_key):
        self.api_key = api_key

    def search_books_by_keyword(self, keyword, category_id, max_results=10, page=1):
        base_url = "https://www.aladin.co.kr/ttb/api/ItemSearch.aspx"

        params = {
            'ttbkey': self.api_key,
            'Query': keyword,
            'QueryType': 'Keyword',
            'CID': category_id,
            'MaxResults': max_results,
            'start': page,
            'SearchTarget': 'Book',
            'output': 'js',
            'Version': '20131101'
        }

        response = requests.get(base_url, params=params)
        #print(response.url)  # 요청 URL 출력
        results = response.json()
        #print(results)  # 응답 데이터 출력

        return results.get('item', [])

    def sort_books_by_comprehensive_score(self, books):
        for book in books:
            sales_point = book.get('salesPoint', 0)
            review_rank = book.get('customerReviewRank', 0)
            # 종합 점수 계산 (예: 판매량과 고객 평점의 합)
            book['comprehensiveScore'] = sales_point + review_rank

        # 종합 점수 기준으로 정렬
        books.sort(key=lambda x: x['comprehensiveScore'], reverse=True)
        return books

    def recommend_books_by_keyword(self, keyword, category_id, max_results=10, page=1):
        books = self.search_books_by_keyword(keyword, category_id, max_results, page)
        if not books:
            #print(f"No books found for keyword: {keyword}")
            return []

        sorted_books = self.sort_books_by_comprehensive_score(books)

        #print("Recommended Books:")
        #for book in sorted_books[:3]:  # 상위 3개 도서만 출력
            #print(f"Title: {book['title']}, Sales: {book['salesPoint']}, Rating: {book['customerReviewRank']}")
            #print(f"Cover Image: {book['cover']}")
            #print(f"Link: {book['link']}\n")

        return sorted_books[:3]

    def update_csv_file(self, user_name, keyword, category_id, books):
        file_path = f"User_Book_Recommend/{user_name}_book.csv"

        # 기존 도서 목록 읽기
        existing_books = set()
        if os.path.exists(file_path):
            with open(file_path, mode='r', newline='', encoding='utf-8') as file:
                reader = csv.reader(file)
                next(reader, None)  # Skip the header
                for row in reader:
                    existing_books.add(row[3])  # Assuming the 'Book' column is the fourth column

        # CSV 파일이 존재하지 않으면 생성하고 헤더 추가
        if not os.path.exists(file_path):
            with open(file_path, mode='w', newline='', encoding='utf-8') as file:
                writer = csv.writer(file)
                writer.writerow(['Date', 'Category', 'Keyword/Tag', 'Book', 'Author', 'BookImage', 'BookLink'])

        # 새로운 도서 정보 추가
        with open(file_path, mode='a', newline='', encoding='utf-8') as file:
            writer = csv.writer(file)
            today = datetime.today().strftime('%Y-%m-%d')

            for book in books:
                if book['title'] not in existing_books:
                    writer.writerow([today, category_id, keyword, book['title'], book['author'], book['cover'], book['link']])
                    existing_books.add(book['title'])
                    break  # 한 번에 하나의 도서만 추가
