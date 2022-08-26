import cv2
import time
# import scrollphathd as sphd
import winsound
from detection import detect
from insert_to_server import insert

net = cv2.dnn.readNet("yolov4-custom_6000.weights", "yolov4-custom.cfg")  # yolo weight 파일과 cfg(설정) 파일 읽어오기


def playsound(sec=30):
    winsound.Beep(4000, sec * 1000)


def led_On(sec=30):
    print("빤짝")
    return 0
    # for x in range(17):
    #     for y in range(7):
    #         # sphd.set_pixel(x, y, 1.0)
    # sphd.show()
    # time.sleep(sec)


step = 0
mode = 0

while True:
    cap = cv2.VideoCapture(0)
    if not detect(net, "obj.names", cap, 0.7, mode):
        # insert("퇴치완료", "return_img.jpg")
        mode = 0
        step = 0
        print("퇴치완료")
        cap.release()
        continue
    step += 1
    # insert(step, "return_img.jpg")
    if step == 1:
        led_On()
    elif step == 2:
        playsound(5)
    elif step == 3:
        led_On()
        playsound(5)
    elif step == 4:
        step = 0
    time.sleep(2)
    mode = 1
    cv2.imshow("return_img", cv2.imread("return_img.jpg"))
    cv2.waitKey(0)
    cap.release()
    cv2.destroyAllWindows()
cap.release()
cv2.destroyAllWindows()
