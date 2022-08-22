import cv2
from detection import detect
import time
from insert_to_server import insert

net = cv2.dnn.readNet("yolov4-custom_6000.weights", "yolov4-custom.cfg")  # yolo weight 파일과 cfg(설정) 파일 읽어오기

step = 0
mode = 0
steps = {1: "1단계 : 조명!발싸", 2: "2단계 : 스피커!출력!", 3: "3단계 : 스피커!! 조명!", 4: "4단계 : 퇴치.완료! 코랩.전송"}

while True:
    cap = cv2.VideoCapture(0)
    if not detect(net, "obj.names", cap, 0.7, mode):
        insert("퇴치완료", "return_img.jpg")
        mode = 0
        step = 0
        print("퇴치완료")
        cap.release()
        continue
    step += 1
    insert(step, "return_img.jpg")
    print(steps[step])
    if step == 4:
        step = 0
    time.sleep(2)
    mode = 1
    cv2.imshow("return_img", cv2.imread("return_img.jpg"))
    cv2.waitKey(0)
    cap.release()
    cv2.destroyAllWindows()
cap.release()
cv2.destroyAllWindows()
