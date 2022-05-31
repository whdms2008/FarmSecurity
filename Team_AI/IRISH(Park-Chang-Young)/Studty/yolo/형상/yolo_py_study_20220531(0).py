# yolo.py - 0차 형상
# 전체적 코드 설명 블로그 링크 : [https://greencloud.tistory.com/82]

import cv2
import numpy as np
# from google.colab.patches import cv2_imshow # colab으로 이미지 출력 시 해당 라인 실행

# 1) Yolo 로드
'''
# 네트워크 불러오기 - cv2.dnn.readNet
[참고 링크 > https://deep-learning-study.tistory.com/299] >>> 더 상세하게 나와있는 것이니까 링크 확인해볼 것

* cv2.dnn.readNet(model, config=None, framework=None) -> retval
    • model: 훈련된 가중치를 저장하고 있는 이진 파일 이름
    • config: 네트워크 구성을 저장하고 있는 텍스트 파일 이름, config가 없는 경우도 많습니다.
    • framework: 명시적인 딥러닝 프레임워크 이름
    • retval: cv2.dnn_Net 클래스 객체
'''

# 밑의 net은 '다크넷' 딥러닝 프레임워크임
# @ yolov3.weights > yolov3의 훈련된 가중치를 저장하고 있는 이진 파일
# @ yolov3.cfg > yolov3의 네트워크 구성을 저장하고 있는 텍스트 파일
net = cv2.dnn.readNet("yolov3.weights", "yolov3.cfg")  # yolo weight 파일과 cfg(설정) 파일 읽어오기

classes = []  # 탐지할 객체 이름, class 배열 만들기

# 2) coco.names에 들어있는 class 들을 classes에 저장
with open("coco.names", "r") as f:
    
    # 읽어온 coco 파일을 whitespace(공백라인)를 제거하여 classes 배열 안에 삽입.
    # @ srtip() > whitespace(띄어쓰기, 탭, 엔터)를 없앰. 중간에 끼어있는 것은 없어지지 않음
    classes = [line.strip() for line in f.readlines()]  

# 네트워크의 모든 레이어 이름을 가져와서 layer_names에 대입.
# @ getLyaerNames() > 네트워크의 모든 레이어 이름을 가져옴
# @ YOLOv3에는 3개의 출력 레이어(82,94,106)가 있음 
layer_names = net.getLayerNames() 

# 레이어 중 출력 레이어의 인덱스를 가져와서 output_layers에 대입
# @ getUnconnectedOutLayers() > 출력 레이어를 가져옴
output_layers = [layer_names[i[0] - 1] for i in net.getUnconnectedOutLayers()]

# 클래스의 갯수만큼 랜덤으로 BGR 배열 생성. 한 사물 당 하나의 color만 사용할 수 있도록 구분해야 함
# @ np.random.uniform(a, b) > random 모듈 안에 정의되어 있는 두 수 사이의 랜덤 한 소수를 리턴 시켜주는 함수
colors = np.random.uniform(0, 255, size=(len(classes), 3))

# cap = cv2.VideoCapture("sample.jpg") # 이미지 파일일 경우
cap = cv2.VideoCapture("d.mp4") # 영상 파일일 경우


while cap.isOpened():
    # 3) 이미지 가져오기
    '''
    - ret, img = cap.read()
    1. cap.read()는 재생되는 비디오의 한 프레임씩 읽어온다. 비디오 프레임을 제대로 읽었다면 ret 값이 True가 되며, 실패하면 False가 된다. 필요한 경우, ret     값을 체크하여 비디오 프레임을 제대로 읽었는지 확인할 수 있다. 읽은 프레임은 img이다.
    2. 가끔 cap이 제대로 초기화되지 않을 수도 있다. 이럴 경우 에러코드를 리턴한다. 이때 cap.isOpened() 함수를 이용해 cap이 초기화가 제대로 되었는지     확인할 수 있다. 만약 cap.isOpened()가 False이면 cap.open() 함수를 이용해 오픈하면 된다.
    3.참고 링크 : https://m.blog.naver.com/samsjang/220500854338
    '''
    ret, img = cap.read() # opencv를 통해 이미지를 가져옴

    if ret:
        # 이미지 재설정
        # @ None = 절대크기 , fx, fy = 상대크기
        img = cv2.resize(img, None, fx=0.6, fy=0.6)  
        
        # 이미지 속성 삽입
        # @ 이미지 높이, 너비, 채널을 각각 저장 channels에 경우 흑백은 나오지 않는다!
        height, width, channels = img.shape  

        # 4) 물체 감지

        # 이미지를 blob 객체로 처리
        # @ blob을 만든다, blob = 멀티 데이터 저장시 사용, 네트워크에 넣기 위한 전처리
        blob = cv2.dnn.blobFromImage(img, 0.00392, (416, 416), (0, 0, 0), True, crop=False)

        '''
        * cv2.dnn.blobFromImage(image, scalefactor=None, size=None, mean=None, swapRB=None, crop=None, ddepth=None) -> retval에 대한 설명
            • image: 입력 영상
            • scalefactor: 입력 영상 픽셀 값에 곱할 값. 기본값은 1.
            • size: 출력 영상의 크기. 기본값은 (0, 0).
            • mean: 입력 영상 각 채널에서 뺄 평균 값. 기본값은 (0, 0, 0, 0).
            • swapRB: R과 B 채널을 서로 바꿀 것인지를 결정하는 플래그. 기본값은 False.
            • crop: 크롭(crop) 수행 여부. 기본값은 False.
            • ddepth: 출력 블롭의 깊이. CV_32F 또는 CV_8U. 기본값은 CV_32F.
            • retval: 영상으로부터 구한 블롭 객체. numpy.ndarray. shape=(N,C,H,W). dtype=numpy.float32.
        '''

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
        # @ 같은 사물에 대해 박스가 여러 개인 것을 제거하는 NMS(Non Maximum Suppresion) 작업을 한다.
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
        # @ 소문자  'q' 키를 눌렀을 때 종료하도록 하였음
        # waitKey() : 0 -> 키 입력이 있을 때까지 무한 대기 / ms 단위로 입력하면 그 단위에 따라 대기
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

# 4) 시스템 종료
cap.release()
cv2.destroyAllWindows() # 열린 모든 창 닫기