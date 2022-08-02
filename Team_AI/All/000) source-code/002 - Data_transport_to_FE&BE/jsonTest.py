# 참고 1 : https://rfriend.tistory.com/474
# 참고 2 : https://tw0226.tistory.com/60
# 참고 3 : https://www.daleseo.com/python-json/
# 참고 4 : https://kejdev.github.io/posts/python-rest-api/

# -----------
import requests # 보내고자 하는 ip로 전송하기 위해 라이브러리
import json # python data 형식을 json 형식으로 변환하기 위한 라이브러리

# URL
# 127.0.0.1은 localhost로 대체 가능
# 아래 IP는 AI팀이 프백팀으로 보내주기 위한 것 
url = "http://15.165.86.204:8080/log/insert/"

# headers
headers = {
    "Content-Type": "application/json"
}

# data
# temp 변수에 보내고자 하는 것을 key - value 형식으로 작성
# 모두 소문자로 구성해줄 것
temp = {
    "camera_num":'abc3',
    "link": "hello-world",
    "level": "level-3",
    "time": "2022-0802-1330"
}

# 딕셔너리를 JSON으로 변환
data = json.dumps(temp)

# psot
response = requests.post(url, headers=headers, data=data)

# 상태 및 text 확인
print("response: ", response)
print("response.text: ", response.text)