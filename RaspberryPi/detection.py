import cv2
import numpy as np
import timeit


def detect(net, obj_link, cap, accuracy=0.7, mode=0):  # 입력받은 영상에서 객체를 탐지하는 함수
    # net : 가중치(.weights) 파일
    # obj_link : obj.names의 파일 경로
    # cap : 카메라 입력 cv2.Capture(카메라넘버)
    # accuracy : 객체 탐지 정확도 기준
    # mode : 0은 객체 탐지를 할때, 1번은 객체 탐지가 된 이후 퇴치 여부 확인을 할때
    start_t = timeit.default_timer()
    classes = []
    with open(obj_link, "r") as f:
        classes = [line.strip() for line in f.readlines()]  # coco.names에 들어있는 class 들을 classes에 저장
    layer_names = net.getLayerNames()
    output_layers = [layer_names[i - 1] for i in net.getUnconnectedOutLayers()]
    colors = np.random.uniform(0, 255, size=(len(classes) - 1, 3))
    while True:
        ret, img = cap.read()
        # img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        # img = cv2.resize(img, None, fx=0.9, fy=0.9)  # None = 절대크기 , fx, fy = 상대크기
        try:
            height, width, _ = img.shape  # 이미지 높이, 너비, 채널을 각각 저장 channels에 경우 흑백은 나오지 않는다!
        except Exception as e:
            height, width = img.shape
        # 객체 탐지
        blob = cv2.dnn.blobFromImage(img, 1/255, (416, 416), (0, 0, 0), True,
                                     crop=False)  # blob을 만든다, blob = 멀티 데이터 저장시 사용
        print(blob.shape)
        net.setInput(blob)
        outs = net.forward(output_layers)  # 추론 진행

        # 정보를 화면에 표시
        class_ids = []
        confidences = []  # 정확도 데이터
        boxes = []  # 박스1
        for out in outs:
            for detection in out:
                scores = detection[5:]
                class_id = np.argmax(scores)
                confidence = scores[class_id]
                if confidence > accuracy:  # 객체탐지시 0.7 = 70% 이상의 정확도를 가진 애들만 탐지

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
        find = set()
        for i in range(len(boxes)):
            if i in indexes:
                x, y, w, h = boxes[i]
                label = str(classes[class_ids[i]])
                find.add(label)
                color = colors[1]
                confi = str(round(confidences[i], 2))
                cv2.rectangle(img, (x, y), (x + w, y + h), color, 1)
                cv2.putText(img, label, (x, y), font, 1, color, 2)
                cv2.putText(img, confi, (x, y + 30), font, 1, color, 2)
        cv2.imshow("aaa.jpg", img)  # 탐지된 순간의 이미지를 show 한다
        if cv2.waitKey(10) & 0xFF == ord('x'):
            cap.release()
            cv2.destroyAllWindows()
            break
        if "animal" in find or "bird" in find:  # find 변수 안에 animal 또는 bird가 들어있을 경우 객체 탐지
            cv2.imwrite("return_img.jpg", img)  # 탐지된 순간의 이미지를 현재 경로에 "return_img.jpg" 파일을 쓴다
            print("탐지됨")
            return True
        if mode == 1:
            ttt = timeit.default_timer() - start_t
            print(f"{round(ttt, 1)} 초 탐지중")
            if ttt > 10:
                print("탐지 안됨")
                return False
