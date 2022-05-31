# yolo.py - 2차 형상
# 전체적 코드 설명 블로그 링크 : [https://greencloud.tistory.com/82]

import cv2
import numpy as np
# from google.colab.patches import cv2_imshow # colab으로 이미지 출력 시 해당 라인 실행

# 1) Yolo 로드
net = cv2.dnn.readNet("yolov3.weights", "yolov3.cfg")  # yolo weight 파일과 cfg(설정) 파일 읽어오기

classes = []  # 탐지할 객체 이름, class 배열 만들기

# 2) coco.names에 들어있는 class 들을 classes에 저장
with open("coco.names", "r") as f:
    # 읽어온 coco 파일을 whitespace(공백라인)를 제거하여 classes 배열 안에 삽입.
    classes = [line.strip() for line in f.readlines()]  

# 네트워크의 모든 레이어 이름을 가져와서 layer_names에 대입.
layer_names = net.getLayerNames() 

# 레이어 중 출력 레이어의 인덱스를 가져와서 output_layers에 대입
output_layers = [layer_names[i[0] - 1] for i in net.getUnconnectedOutLayers()]

# 클래스의 갯수만큼 랜덤으로 BGR 배열 생성. 한 사물 당 하나의 color만 사용할 수 있도록 구분해야 함
colors = np.random.uniform(0, 255, size=(len(classes), 3))

# cap = cv2.VideoCapture("sample.jpg") # 이미지 파일일 경우
cap = cv2.VideoCapture("d.mp4") # 영상 파일일 경우


while cap.isOpened():
    # 3) 이미지 가져오기
    ret, img = cap.read() # opencv를 통해 이미지를 가져옴

    if ret:
        # 이미지 재설정
        img = cv2.resize(img, None, fx=0.6, fy=0.6)  
        
        # 이미지 속성 삽입
        height, width, channels = img.shape  

        # 4) 물체 감지

        # 이미지를 blob 객체로 처리
        # @ blob을 만든다, blob = 멀티 데이터 저장시 사용, 네트워크에 넣기 위한 전처리
        blob = cv2.dnn.blobFromImage(img, 0.00392, (416, 416), (0, 0, 0), True, crop=False)

        # blob 객체에 setInput 함수를 적용
        net.setInput(blob)

        # output_layers를 네트워크 순방향으로 실행(추론)
        outs = net.forward(output_layers)  

        # 5) 물체 인식 정보를 화면에 표시

        class_ids = [] # 인식한 사물 클래스 ID를 넣는 배열
        confidences = []  # 정확도 데이터(0에서 1까지 사물 인식에 대한 신뢰도를 넣는 배열)
        boxes = []  # 사물을 인식해서 그릴 상자에 대한 배열

        for out in outs:
            for detection in out:
                scores = detection[5:]
                # score 중에서 최댓값을 색안히야 class_id에 대입
                class_id = np.argmax(scores)
                # scores 중에서 class_id에 해당하는 값을 confidence에 대입
                confidence = scores[class_id]

                # 객체 탐지시 confidence(정확도) > A 일 때 사물이 인식되었다고 판단.
                # 즉, confidence > 0.5이면 0.5가 넘을 경우에만 사물이 인식되었다고 판단
                if confidence > 0.3: 
                    # 객체 탐지(Object detected)
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

        # 6) 노이즈 제거
         
        # 노이즈 제거
        indexes = cv2.dnn.NMSBoxes(boxes, confidences, 0.5, 0.4)  # 탐지된 객체를 반환

        # 7) 결과 이미지 출력

        # Font 종류 중 하나인 FONT_HERSHEY_PLAIN(작은 크기 산세리프 폰트)를 적용
        font = cv2.FONT_HERSHEY_PLAIN

        for i in range(len(boxes)):
            if i in indexes:
                x, y, w, h = boxes[i]
                # 클래스 아이디 지정해둔 것을 label 변수에 저장
                label = str(classes[class_ids[i]])
                # 위에서 colors 배열에 색상을 넣어둔 것을 color에 저장
                color = colors[i]
                confi = str(round(confidences[i], 2))
                cv2.rectangle(img, (x, y), (x + w, y + h), color, 2) # 사각형 그리기
        
                # yolo에서 학습된 사물 명칭 출력
                cv2.putText(img, label, (x, y + 30), font, 3, color, 3)
                cv2.putText(img, confi, (x, y + 90), font, 3, color, 3)

        cv2.imshow("Image", img) # VS CODE, 주피터는 해당 라인 실행
        # cv2_imshow(img) # 구글 코랩에서는 해당 라인 실행

        # 이미지 또는 영상 출력 화면 종료 조건
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

# 7) 시스템 종료
cap.release()
cv2.destroyAllWindows() # 열린 모든 창 닫기