import cv2
import numpy as np

# Yolo 로드
net = cv2.dnn.readNet("yolov3.weights", "yolov3.cfg")  # yolo weight 파일과 cfg(설정) 파일 읽어오기
classes = []  # 탐지할 객체 이름
with open("coco.names", "r") as f:
    classes = [line.strip() for line in f.readlines()]  # coco.names에 들어있는 class 들을 classes에 저장
layer_names = net.getLayerNames()
output_layers = [layer_names[i[0] - 1] for i in net.getUnconnectedOutLayers()]
colors = np.random.uniform(0, 255, size=(len(classes), 3))
cap = cv2.VideoCapture("sample.jpg")

# 이미지 가져오기
while cap.isOpened():
    ret, img = cap.read()
    if ret:
        img = cv2.resize(img, None, fx=0.6, fy=0.6)  # None = 절대크기 , fx, fy = 상대크기
        height, width, channels = img.shape  # 이미지 높이, 너비, 채널을 각각 저장 channels에 경우 흑백은 나오지 않는다!

        # 객체 탐지
        blob = cv2.dnn.blobFromImage(img, 0.00392, (416, 416), (0, 0, 0), True, crop=False)  # blob을 만든다, blob = 멀티 데이터 저장시 사용

        '''
        cv2.dnn.blobFromImage(image, scalefactor=None, size=None, mean=None, swapRB=None, crop=None, ddepth=None) -> retval에 대한 설명
            • image: 입력 영상
            • scalefactor: 입력 영상 픽셀 값에 곱할 값. 기본값은 1.
            • size: 출력 영상의 크기. 기본값은 (0, 0).
            • mean: 입력 영상 각 채널에서 뺄 평균 값. 기본값은 (0, 0, 0, 0).
            • swapRB: R과 B 채널을 서로 바꿀 것인지를 결정하는 플래그. 기본값은 False.
            • crop: 크롭(crop) 수행 여부. 기본값은 False.
            • ddepth: 출력 블롭의 깊이. CV_32F 또는 CV_8U. 기본값은 CV_32F.
            • retval: 영상으로부터 구한 블롭 객체. numpy.ndarray. shape=(N,C,H,W). dtype=numpy.float32.
        '''

        net.setInput(blob)
        outs = net.forward(output_layers)  # 추론 진행

        # 정보를 화면에 표시
        class_ids = []
        confidences = []  # 정확도 데이터
        boxes = []  # 박스
        for out in outs:
            for detection in out:
                scores = detection[5:]
                class_id = np.argmax(scores)
                confidence = scores[class_id]
                if confidence > 0.3:  # 객체탐지시 0.3 = 30% 이상의 정확도를 가진 애들만 탐지

                    # Object detected
                    center_x = int(detection[0] * width)
                    center_y = int(detection[1] * height)

                    w = int(detection[2] * width)
                    h = int(detection[3] * height)
                    # 좌표
                    x = int(center_x - w / 2)
                    y = int(center_y - h / 2)

                    boxes.append([x, y, w, h])
                    # print("탐지된 객체(정확도:", round(confidence, 2), ", class_id : ", classes[class_id], "위치 :", [x, y, w, h])
                    confidences.append(float(confidence))
                    class_ids.append(class_id)
        # print(boxes)
        indexes = cv2.dnn.NMSBoxes(boxes, confidences, 0.5, 0.4)  # 탐지된 객체를 반환

        font = cv2.FONT_HERSHEY_PLAIN
        for i in range(len(boxes)):
            if i in indexes:
                x, y, w, h = boxes[i]
                label = str(classes[class_ids[i]])
                color = colors[i]
                confi = str(round(confidences[i], 2))
                cv2.rectangle(img, (x, y), (x + w, y + h), color, 2)
                cv2.putText(img, label, (x, y + 30), font, 3, color, 3)
                cv2.putText(img, confi, (x, y + 90), font, 3, color, 3)
        cv2.imshow("Image", img)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

cap.release()
cv2.destroyAllWindows()