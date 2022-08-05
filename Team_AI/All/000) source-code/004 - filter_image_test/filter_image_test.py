import cv2
import numpy as np
from glob import glob
import matplotlib.pyplot as plt

# Yolo 로드
net = cv2.dnn.readNet("yolov4-custom_6000.weights", "yolov4-custom.cfg")  # yolo weight 파일과 cfg(설정) 파일 읽어오기
classes = []  # 탐지할 객체 이름
with open("obj.names", "r") as f:
    classes = [line.strip() for line in f.readlines()]  # coco.names에 들어있는 class 들을 classes에 저장
layer_names = net.getLayerNames()
output_layers = [layer_names[i - 1] for i in net.getUnconnectedOutLayers()]
colors = np.random.uniform(0, 255, size=(len(classes) - 1, 3))

cnt = ["30", "70"]
types = ["animal", "bird", "human"]
filters = ["rain", "snow", "night", "sundown"]

datas = [[[[], [], [], []], [[], [], [], []], [[], [], [], []]], [[[], [], [], []], [[], [], [], []], [[], [], [], []]]]
imgs = []

# 이미지 가져오기
for c in cnt:
    for t in types:
        for f in filters:
            imgs += glob(f'img/{c}/{t}/{f}/*.jpg')
print(imgs)

name = ""
for img in set(imgs):
    img_name = img
    img_name = img_name.replace("\\", "/")
    name = img_name.split("/")
    names = ""
    img = cv2.imread(img)

    # img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    img = cv2.resize(img, None, fx=0.9, fy=0.9)  # None = 절대크기 , fx, fy = 상대크기
    height, width, channels = img.shape  # 이미지 높이, 너비, 채널을 각각 저장 channels에 경우 흑백은 나오지 않는다!

    # 객체 탐지
    blob = cv2.dnn.blobFromImage(img, 0.00392, (416, 416), (0, 0, 0), True,
                                 crop=False)  # blob을 만든다, blob = 멀티 데이터 저장시 사용
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
            if confidence > 0.7:  # 객체탐지시 0.7 = 70% 이상의 정확도를 가진 애들만 탐지

                # Object detected
                center_x = int(detection[0] * width)
                center_y = int(detection[1] * height)

                w = int(detection[2] * width)
                h = int(detection[3] * height)
                # 좌표
                x = int(center_x - w / 2)
                y = int(center_y - h / 2)

                boxes.append([x, y, w, h])
                confidences.append(float(confidence))
                class_ids.append(class_id)

    indexes = cv2.dnn.NMSBoxes(boxes, confidences, 0.5, 0.4)  # 탐지된 객체를 반환
    font = cv2.FONT_HERSHEY_SIMPLEX
    confis = []
    for i in range(len(boxes)):
        if i in indexes:
            x, y, w, h = boxes[i]
            label = str(classes[class_ids[i]])
            color = colors[1]
            confi = str(round(confidences[i], 2))
            cv2.rectangle(img, (x, y), (x + w, y + h), color, 1)
            cv2.putText(img, label, (x, y), font, 1, color, 2)
            cv2.putText(img, confi, (x, y + 30), font, 1, color, 2)
            confis.append(round(confidences[i], 2))

    cv2.imwrite(f"result/{name[1]}/{name[2]}/{name[3]}/{name[4]}", img)
    try:
        datas[cnt.index(name[1])][types.index(name[2])][filters.index(name[3])].append(
            round(sum(confis) / len(confis), 2))
    except ZeroDivisionError as e:
        datas[cnt.index(name[1])][types.index(name[2])][filters.index(name[3])].append(0)

for c in range(len(cnt)):
    for t in range(len(types)):
        dataa = [i for i in datas[c][t]]
        plt.plot(["night", "rain", "snow", "sundown"], [round(sum(i)/len(i), 2) for i in dataa])
        plt.title(f"{cnt[c]}_{types[t]}")
        plt.show()
cv2.waitKey(0)
cv2.destroyAllWindows()
