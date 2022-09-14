import cv2
import time
import pygame
import numpy as np
import scrollphathd as sphd
from detection import detect
from insert_to_server import insert

net = cv2.dnn.readNet("yolov4-custom_6000.weights", "yolov4-custom.cfg")  # yolo weight 파일과 cfg(설정) 파일 읽어오기
pygame.mixer.init()
hz = pygame.mixer.Sound("4000HZ-10s.wav")
sound = pygame.mixer.Sound("sound.wav")


def play_sound(type, sec=30):  # type(hz, mp3) 를 sec(초) 동안 재생하는 함수
    if type == "hz":
        hz.play()
    else:
        sound.play()


def led_On(sec=30):  # 라즈베리파이의 led를 sec(초) 동안 켜는 함수
    print("켬")
    for x in range(17):
        for y in range(7):
            sphd.set_pixel(x, y, 1.0)  # LED모듈의 x, y 좌표에 해당하는 led를 1.0의 밝기로 켠다
    sphd.show()  # 설정된 좌표의 밝기로 LED모듈에 출력한다.
    time.sleep(sec)
    sphd.clear()
    sphd.show()


step = 0  # 퇴치단계
mode = 0  # 0 = 탐지되기 전, 1 = 탐지된 후
cap = cv2.VideoCapture(0)
cap.set(cv2.CAP_PROP_FRAME_WIDTH, 416)  # 입력받은 영상의 너비를 416으로
cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 416)  # 입력받은 영상의 높이를 416으로

if not cap.isOpened():
    print('영상을 불러오는데 실패함')
    exit()

# 배경 영상 등록(정적(static) 배경)
ret, back = cap.read()
if not ret:
    print('배경 등록 실패')
    exit()

back = cv2.cvtColor(back, cv2.COLOR_BGR2GRAY)
back = cv2.GaussianBlur(back, (0, 0), 1.)
# *cv2.accumulateWeighted(gray, fback, 0.01) 연산을 위함*
# print(back)
fback = back.astype(np.float32)
while True:
    ret, frame = cap.read()
    if not ret:
        print("ret error")
        break
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    gray = cv2.GaussianBlur(gray, (0, 0), 1.)

    # fback: float32, back: uint8 정적배경
    cv2.accumulateWeighted(gray, fback, 0.01)

    # *absdiff 연산을 위한 변환*
    back = fback.astype(np.uint8)

    diff = cv2.absdiff(gray, back)
    _, diff = cv2.threshold(diff, 30, 255, cv2.THRESH_BINARY)

    cnt, _, stats, _ = cv2.connectedComponentsWithStats(diff)
    for i in range(1, cnt):
        x, y, w, h, area = stats[i]
        if area < 1000:
            continue
        print("탐지됨, 객체 판별시작")
        if not detect(net, "obj.names", cap, 0.7, mode):
            # insert("퇴치완료", "return_img.jpg")  # DB와 GCP에 데이터 전송 및 업로드
            mode = 0
            step = 0
            print("퇴치완료")
            cv2.destroyAllWindows()
            break
        step += 1  # 탐지 완료 후 step을 1 증가
        # insert(step, "return_img.jpg")  # DB와 GCP에 데이터 전송 및 업로드
        print(f"{step}:단계")
        if step == 1:
            led_On(3)  # LED를 3초동안 켬
        elif step == 2:
            play_sound("mp3", 5)  # mp3를 5초동안 재생
        elif step == 3:
            led_On(3)  # LED를 3초동안 켬
            play_sound("hz", 5)  # hz를 5초동안 재생
        elif step == 4:
            led_On(3)  # LED를 3초동안 켬
            play_sound("hz", 5)  # hz를 5초동안 재생
            step = 0  # 보류
        mode = 1  # 탐지 되었으니 mode를 1로
        cv2.imshow("return_img", cv2.imread("return_img.jpg"))
        cv2.waitKey(0)
        cv2.destroyAllWindows()
        cap.release()
        cap = cv2.VideoCapture(0)
        cap.set(cv2.CAP_PROP_FRAME_WIDTH, 416)  # 입력받은 영상의 너비를 416으로
        cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 416)  # 입력받은 영상의 높이를 416으로

        if not cap.isOpened():
            print('영상을 불러오는데 실패함')
            exit()

        # 배경 영상 등록(정적(static) 배경)
        ret, back = cap.read()
        if not ret:
            print('배경 등록 실패')
            exit()

        back = cv2.cvtColor(back, cv2.COLOR_BGR2GRAY)
        back = cv2.GaussianBlur(back, (0, 0), 1.)
        # *cv2.accumulateWeighted(gray, fback, 0.01) 연산을 위함*
        # print(back)
        fback = back.astype(np.float32)
        break
cap.release()
cv2.destroyAllWindows()
