import cv2
import numpy as np
from glob import glob
import os


def createDirectory(directory):
    try:
        if not os.path.exists(directory):
            os.makedirs(directory)
            print(f"{directory} : 디렉토리 생성")
    except OSError:
        print("Error: Failed to create the directory.")


def sa(image, num):
    pic = image.copy()  # 입력받은 image 데이터를 복사하여 pic에 저장
    pic = pic.astype('int16')  # pic의 모든 열의 데이터 타입을 int16으로 변경한다.
    pic = np.clip(pic + (pic - 128) * num, 0, 255)  # np.clip(배열, 최솟값, 최댓값)
    # pic 의 값에 pic + (pic - 128 ) * num 을 하여 array(pic)의 값을 변경한 후,
    # 최솟값 보다 작은 값을 최솟값으로 변경하고, 최댓값 또한 같다.
    pic = pic.astype('uint8')  # pic의 데이터 타입을 uint8로 변경한
    return pic  # 변경된 pic(image) 를 반환 한다.


OUTPUT_PATH = "result"  # 이미지 합성 후 결과를 출력할 폴더
INPUT_PATHS = glob('dataset\\*.jpg')  # 합성에 사용할 이미지 원본 폴더 에서 .jpg 파일 들의 경로를 list 형태로 저장
FILTER_PATHS = glob('img_filter\\*.jpg')  # 합성에 사용할 이미지 필터 폴더 에서 .jpg 파일 들의 경로를 list 형태로 저장
FILTER_NAME = ["snow", "rain", "sundown", "night"]  # 합성에 사용한 이미지 필터의 이름
CNT = [0, 0, 0]  # animal, bird, human 순서 대로 이미지 개수를 카운팅
NAMES = ["animal", "bird", "human"]  # 사진의 클래스 종류

createDirectory(OUTPUT_PATH)
for i in NAMES:
    createDirectory(f"{OUTPUT_PATH}\\{i}")
    for j in FILTER_NAME:
        createDirectory(f"{OUTPUT_PATH}\\{i}\\{i}_{j}")

for img in INPUT_PATHS:  # 원본 이미지 경로 리스트 에서
    text, check = img.split("\\")  # 읽어온 이미지 경로를 \\ 기준으로 나눈 후 문자열 슬라이싱으로 .jpg 확장자 문자를 제거한다.
    check = check[0:-4]
    text = f"{text}\\{check}.txt"
    with open(text, "r") as file:
        strings = file.readlines()
    type_num = [i for i in range(len(NAMES)) if NAMES[i] in check][0]  # check의 문자열이 names의 몇번째 인덱스인지 반환
    # print(text, check, type_num)
    CNT[type_num] += 1  # animal은 0번, bird는 1번, human은 2번으로 cnt[0] += 1 이런식으로 이미지의 개수가 추가됨
    img_data = cv2.imread(img, 1)  # img(경로)에 있는 이미지를 1(COLOR)로 img_data 변수 읽어 저장한다.
    h, w, c = img_data.shape  # img_data 이미지의 상태를 출력 받는다 (h : height, w: width, c:Channel )
    for i in range(len(FILTER_PATHS)):  # FILTER_PATH의 크기만큼 0부터 점진적으로 반복한다
        if i == 3:  # i == 3일 경우
            result = sa(img_data, 2)  # result 변수에 sa 함수의 반환 값을 저장
        else:  # 아닐경우
            filter_img = cv2.resize(cv2.imread(FILTER_PATHS[i]), (w, h))  # 필터의 이미지 크기를 적용할 이미지의 크기에 맞게 resize 한다.
            result = cv2.addWeighted(img_data, 1, filter_img, 1,
                                     0)  # result값에 img_data(원본 이미지) 와 filter_img (필터 이미지)를 합친다
        cv2.imwrite(
            f"{OUTPUT_PATH}\\{NAMES[type_num]}\\{NAMES[type_num]}_{FILTER_NAME[i]}\\{NAMES[type_num]}_{FILTER_NAME[i]}_{CNT[type_num]}.jpg",
            result)  # 각 클래스 타입의 맞는 필터 폴더에 맞게 저장시킨다.
        with open(f"{OUTPUT_PATH}\\{NAMES[type_num]}\\{NAMES[type_num]}_{FILTER_NAME[i]}\\{NAMES[type_num]}_{FILTER_NAME[i]}_{CNT[type_num]}.txt","w") as file:
            file.writelines(strings)
        # 예 : dataset_20220721\\animal\\animal_rain\\animal_rain_1.jpg
