# 참고 링크 2 : https://jsikim1.tistory.com/27

# ----------------------------------------------------------------------------------------

# google.cloud가 설치되어 있지 않으면 cmd 창에 pip install google.cloud.storage 타이핑하고 설치할 것

# ----------------------------------------------------------------------------------------

import os

### 환경 변수 설정

# 사전에 json 파일을 다운로드 받아야 한다.
# 관련 참고 링크 : https://no-ah0410.tistory.com/103

# 역슬래쉬를 슬래쉬로 다 변경할 것
KEY_PATH = "[json 파일 절대 경로]"

os.environ["GOOGLE_APPLICATION_CREDENTIALS"]= KEY_PATH

# ----------------------------------------------------------------------------------------

### 인증 확인
from google.cloud import storage

storage_client = storage.Client()
buckets = list(storage_client.list_buckets())

print(buckets) # 결과 => [<Bucket: 버킷 이름>]

# ----------------------------------------------------------------------------------------

### GCP에 파일 올리기

# 관련 참고 링크 : https://soundprovider.tistory.com/entry/GCP-Python%EC%97%90%EC%84%9C-GCP-Cloud-Storage-%EC%97%B0%EB%8F%99%ED%95%98%EA%B8%B0


bucket_name = '버킷이름'    # 서비스 계정 생성한 bucket 이름 입력
source_file_name = '이미지 절대경로'  # GCP에 업로드할 파일 절대경로, 경로 사이 역슬래쉬는 슬래쉬로 변환할 것
destination_blob_name = 'GCP에 이미지 저장 시 저장 이름'    # 업로드할 파일을 GCP에 저장할 때의 이름. 새로운 이미지를 넣을 때마다 바꾸어줘야 함


storage_client = storage.Client()
bucket = storage_client.bucket(bucket_name)
blob = bucket.blob(destination_blob_name)

blob.upload_from_filename(source_file_name)

print("done!") # 삽입 완료 시 출력 문구

# ----------------------------------------------------------------------------------------

### GCP에 업로드된 사진 순서

################################################################################################
# 공식적 사진 URL = https://storage.googleapis.com/farmserity/ + destination_blob_name 와 동일
# 즉, destination_blob_name 이 test3 이면 이 업로드된 사진의 공식 URL은
# https://storage.googleapis.com/farmserity/test3 임
# 따라서, 이것을 차례대로 사진이 발생할 때마다 이 값을 프백팀에 보내주면 됨