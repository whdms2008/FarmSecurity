import requests  # 보내고자 하는 ip로 전송하기 위해 라이브러리
import json  # python data 형식을 json 형식으로 변환하기 위한 라이브러리
from datetime import datetime
from upload_to_gcp import upload_gcp


# URL
# 127.0.0.1은 localhost로 대체 가능
# 아래 IP는 AI팀이 프백팀으로 보내주기 위한 것
def insert(step, image):
    url = "http://15.165.86.204:8080/log/insert"
    gcp_url = "https://storage.googleapis.com/farmserity/"
    # headers
    headers = {
        "Content-Type": "application/json"
    }

    # data
    # temp 변수에 보내고자 하는 것을 key - value 형식으로 작성
    # 모두 소문자로 구성해줄 것

    cameraNum = "AAA"
    get_time = datetime.now().strftime('%Y-%m-%d-%H:%M:%S')
    temp = {
        "cameraNum": cameraNum,
        "link": f"{gcp_url + get_time}",
        "level": f"{str(step) + '단계'}",
        "time": f"{get_time}"
    }
    upload_gcp(image, get_time)  # (이미지, 이름)
    # 딕셔너리를 JSON으로 변환
    data = json.dumps(temp)

    # POST
    response = requests.post(url, headers=headers, data=data)

    # 상태 및 text 확인
    print("response: ", response)  # 200이 떠야 정상적으로 송신된 것임.
    print("response.text: ", response.text)
