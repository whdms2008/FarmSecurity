##### *) 캡처된 이미지 GCP(Google Cloud Platform)에 업로드, 업로드된 링크 변수에 할당

# google.cloud가 설치되어 있지 않으면 cmd 창에 pip install google.cloud.storage 타이핑하고 설치할 것
import os
from google.cloud import storage

### 환경 변수 설정

# 사전에 json 파일을 다운로드 받아야 한다.

# 역슬래쉬를 슬래쉬로 다 변경할 것

KEY_PATH = "irish-358208-81bd58886a5f.json"

os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = KEY_PATH


### 인증 확인
def upload_gcp(file_url, file_name):
    storage_client = storage.Client()
    buckets = list(storage_client.list_buckets())

    print(buckets)  # 결과 => [<Bucket: farmserity>]

    ### GCP에 파일 올리기
    bucket_name = 'farmserity'  # 서비스 계정 생성한 bucket 이름 입력
    source_file_name = file_url  # GCP에 업로드할 파일 절대경로, 경로 사이 역슬래쉬는 슬래쉬로 변환할 것
    destination_blob_name = file_name  # 업로드할 파일을 GCP에 저장할 때의 이름. 새로운 이미지를 넣을 때마다 바꾸어줘야 함

    storage_client = storage.Client()
    bucket = storage_client.bucket(bucket_name)
    blob = bucket.blob(destination_blob_name)

    blob.upload_from_filename(source_file_name)

    print("done!")

    ### GCP에 업로드된 사진 순서

    ################################################################################################
    # 공식적 사진 URL = https://storage.googleapis.com/farmserity/ + destination_blob_name 와 동일
    # 즉, destination_blob_name 이 test3 이면 이 업로드된 사진의 공식 URL은
    # https://storage.googleapis.com/farmserity/test3 임
    # 따라서, 이것을 차례대로 사진이 발생할 때마다 이 값을 프백팀에 보내주면 됨
