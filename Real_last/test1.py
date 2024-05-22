# import os
# from flask import Flask, jsonify
# from flask_restx import Api, Resource
# from urllib.parse import unquote
# from main import MusicRecommendationSystem

# # Flask 애플리케이션과 Flask-RESTX API 초기화
# app = Flask(__name__)
# api = Api(app)

# # JSON 파일을 저장할 디렉토리 설정
# json_directory = "json_files"
# os.makedirs(json_directory, exist_ok=True)  # 디렉토리가 없으면 생성

# @api.route('/my_endpoint/<string:keyword>')  # URL 경로에 keyword를 포함시켜 동적으로 처리
# class MyEndpoint(Resource):
#     # GET 요청을 처리하는 메서드 정의
#     def get(self, keyword):
#         # URL 디코딩하여 한글 키워드를 복원
#         decoded_keyword = unquote(keyword)
#         # MusicRecommendationSystem 클래스의 인스턴스 생성 및 키워드 전달
#         my_instance = MusicRecommendationSystem(decoded_keyword)
#         # run 메서드를 호출하여 추천 결과를 얻음
#         result = my_instance.run()
#         # 콘솔에 결과 출력 (디버깅용)
#         print(result)
#         # 결과를 JSON 형식으로 반환
#         return jsonify(result)

# if __name__ == '__main__':
#     # Flask 애플리케이션 실행
#     app.run(debug=True, host='0.0.0.0', port=8090)

import os
from flask import Flask, request, jsonify
from flask_restx import Api, Resource
from urllib.parse import unquote
from main import MusicRecommendationSystem

# Flask 애플리케이션과 Flask-RESTX API 초기화
app = Flask(__name__)
api = Api(app)

# JSON 파일을 저장할 디렉토리 설정
json_directory = "json_files"
os.makedirs(json_directory, exist_ok=True)  # 디렉토리가 없으면 생성

@api.route('/my_endpoint/<string:keyword>')  # URL 경로에 keyword를 포함시켜 동적으로 처리
class MyEndpoint(Resource):
    # POST 요청을 처리하는 메서드 정의
    def post(self, keyword):
        # URL 디코딩하여 한글 키워드를 복원
        decoded_keyword = unquote(keyword)
        # POST 요청으로부터 데이터 추출
        data = request.get_json()
        # MusicRecommendationSystem 클래스의 인스턴스 생성 및 키워드와 데이터 전달
        my_instance = MusicRecommendationSystem(decoded_keyword, data)
        # run 메서드를 호출하여 추천 결과를 얻음
        result = my_instance.run()
        # 콘솔에 결과 출력 (디버깅용)
        print(result)
        # 결과를 JSON 형식으로 반환
        return jsonify(result)

if __name__ == '__main__':
    # Flask 애플리케이션 실행
    app.run(debug=True, host='0.0.0.0', port=8090)
