import cv2
import time
import scrollphathd as sphd
import winsound
from detection import detect
from insert_to_server import insert

net = cv2.dnn.readNet("yolov4-custom_6000.weights", "yolov4-custom.cfg")  # yolo weight 파일과 cfg(설정) 파일 읽어오기


def playsound(type, sec=30):  # type(hz, mp3) 를 sec(초) 동안 재생하는 함수
    if type == "hz":
        winsound.Beep(4000, sec * 1000)  # 4000Hz
    else:
        winsound.PlaySound("mp3.mp3", winsound.MB_OK)


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

while True:
    cap = cv2.VideoCapture(0)
    if not detect(net, "obj.names", cap, 0.7, mode):
        insert("퇴치완료", "return_img.jpg")  # DB와 GCP에 데이터 전송 및 업로드
        mode = 0
        step = 0
        print("퇴치완료")
        cap.release()
        continue
    step += 1  # 탐지 완료 후 step을 1 증가
    insert(step, "return_img.jpg")   # DB와 GCP에 데이터 전송 및 업로드
    print(f"{step}:단계")
    if step == 1:
        led_On(3)  # LED를 3초동안 켬
    elif step == 2:
        playsound("mp3", 5)  # mp3를 5초동안 재생
    elif step == 3:
        led_On(3)  # LED를 3초동안 켬
        playsound("hz", 5)  # hz를 5초동안 재생
    elif step == 4:
        led_On(3)  # LED를 3초동안 켬
        playsound("hz", 5)  # hz를 5초동안 재생
        step = 0  # 보류

    time.sleep(2)
    mode = 1  # 탐지 되었으니 mode를 1로
    cv2.imshow("return_img", cv2.imread("return_img.jpg"))
    cv2.waitKey(0)
    cap.release()
    cv2.destroyAllWindows()
cap.release()
cv2.destroyAllWindows()
