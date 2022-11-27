# :blue_book: 프로젝트 소개

## :bell: 프로젝트 주제
:apple: AI를 이용한 농작물 피해 완화 시스템

## :beginner: 개발 배경
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/179216377-5c0d525e-64dd-4a1e-8bd0-22841a7d0f2d.JPG" alt="img1"/>
  
  :file_folder: [표 1] : 동물별 농작물 피해실태(환경부 생물다양성과/연도별 유해생물에 의한 피해현황(2014~2018)/2019)
</div>

:heavy_check_mark: [표 1]에서 확인할 수 있듯이 '18년 기준 피해 규모는 '14년 대비 8.1% 증가 <br>
:heavy_check_mark: 즉, 해마다 피해 규모가 전반적 증가 추세임을 알 수 있음 <br>
:heavy_check_mark: 동물 부분에서는 멧돼지, 고라니, 청설모 순으로 피해 규모가 큼 <br>
:heavy_check_mark: 새 부분에서는 꿩, 까치, 오리류 순으로 피해 규모가 큼 <br>
> :bangbang: 새도 과학적으로 동물에 포함되나, 해당 프로젝트에서는 동물은 새가 아닌 동물로 지정하여, 동물과 새로 구분하였음

## :fire: 개발 목적
:heavy_check_mark: 개발 배경을 통해 확인할 수 있는 피해 규모 완화 위해 사람이 직접 유해야생동물 포획 시 인건비, 안전 등 고려요소가 많음<br>
:heavy_check_mark: 이에 따라, 사람 이외의 피해 규모 완화 위한 무인 시스템 필요성을 느껴 해당 프로젝트 개발<br>

## :sweat_drops: 개발 설계
:one: __객체 탐지를 위해 AI 활용__<br>
:heavy_check_mark: 객체는 [사람/동물/새/사물]로 구분<br>
:heavy_check_mark: 객체 중 새 및 동물은 퇴치 대상에 해당 <br>

:two: __차영상 및 YOLO를 활용한 객체 판별 & 객체 탐지__<br>
:heavy_check_mark: 탐지 측면에서 효율성을 높이기 위해 차영상을 활용<br>
:heavy_check_mark: 차영상을 통해 기존 배경과 차이 발생 시 YOLO를 실행하여 객체 판별 및 탐지 과정 진행<br>

:three: __퇴치 동작 다양화__<br>
:heavy_check_mark: 퇴치 객체 탐지 시 : 1-4단계 순서대로 퇴치 진행<br>
> :rotating_light: __1단계 : 고강도 조명 출력<br> :sound: 2단계 : 랜덤 퇴치 신호 출력<br> :zap: 3단계 : 고주파수 출력<br> :smiling_imp: 4단계 : 1~3단계 종합 출력__<br>

:four: __애플리케이션을 통한 퇴치 알림 서비스__<br>
:heavy_check_mark: 퇴치 대상 식별 및 퇴치 단계, 퇴치 여부를 농장 주인에게 알림<br> 

## 🐟 H/W & S/W 구성도<br>
__#1 단순 구성도__
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/186291096-df9253c7-dadf-42e8-898d-3786f0734994.JPG" width="800" height="500"/>
</div> <br><br>

__#2 상세 구성도__ <br>
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/188639417-deef579e-22c8-481c-afca-3c71f208b1e8.JPG" width="700" height="350"/> 
</div><br><br>

__#3 구성도 설명__ <br>
✔️ 라즈베리(카메라)는 실시간으로 영상 촬영 및 AI 모듈에 영상 제공 <br>
✔️ AI 모듈은 전달받은 영상에서 먼저 차영상을 구함. 탐지된 차영상 있을 시 YOLO로 객체 판별 진행 <br>
✔️ 판별 객체가 퇴치 객체(동물 또는 새)일 경우, AI 모듈은 퇴치 단계에 따라 라즈베리(빛) 또는 라즈베리(스피커) 제어 <br>
✔️ 라즈베리(빛) 또는 라즈베리(스피커)는 AI 모듈의 제어 신호에 따라 작동됨 <br>
✔️ AI 모듈은 동물 또는 새 탐지 시 [카메라 일련번호 / 탐지 객체 캡처 링크 / 퇴치 단계 / 탐지 시간] 정보를 서버(=Spring Boot)에 송신 <br>
✔️ 서버는 실시간으로 AI 모듈에서 보내는 정보를 감지. 감지된 정보가 있을 경우 해당 정보를 DB에 삽입. 또한 이 정보를 사용자에게 파이어베이스 알림을 통해 전송 {-> 탐지 객체 있을 경우 사용자가 알아야 하므로} <br>
✔️ 사용자가 과거 기록 확인 요청할 경우 해당 요청 정보 확인 가능 <br>

## 👬 ERD <br>
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/188640237-c1407dee-c22d-4075-9293-b1dc26ec1a10.JPG" alt="ERD" width="600" height="450"/>
</div> <br>

## :chart_with_upwards_trend: 순서도
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/179218577-c5aa5d2d-a47b-4818-831c-6e1da456e6f2.JPG" alt="img2"/>
</div>

## :dart: 사용 Tool / 사이트 / 프레임워크

| 구분 | 사용 Tool / 사이트 / 프레임워크 |     
| :------: | :-----------------------------------------------:|
| Data collection | AI Hub / Kaggle |
| Date train & test | Goolge Colab Pro & Yolov4 |
| Front End | Android Studio |
| Back End | Spring Boot |
| DB | Maria DB |
| Notification | Firebase |
| Hardware | Raspberry Pi |
| Devops | Github |
| Etc | GCP, GCM, FCM |

## 🚥 Yolo 설정
### 🎏 __Yolo v4 vs v5 선택__<br>
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/179221837-d6928d6d-3a25-4477-b081-6f0d94b96021.JPG" alt="img3"/>
</div><br>

> :smiley: 참고 링크 : https://yong0810.tistory.com/30 <br>
:heavy_check_mark: v5로 선택할 시 좀 더 정확한 인식률 등을 기대할 수 있으나, 이것은 모델 성능 향상에 따른 인식률 등의 개선이지, 연구자 등의 노력으로 인한 개선이 아님<br>
:heavy_check_mark: 이러한 점과 연구 목적에 맞는 v4가 좀 더 합당하다고 판단하여 v4로 최종적으로 선택하였음<br>

### 🎫 __yolov4의 Darknet을 활용한 객체 탐지__  <br>
:heavy_check_mark: [객체 탐지 과정 설명 블로그](https://velog.io/@irish/Yolov4%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%9C-%EA%B0%9D%EC%B2%B4-%ED%83%90%EC%A7%80-%EA%B5%AC%ED%98%84) <br>
:heavy_check_mark: [정리 ipynb 파일 보기](https://github.com/whdms2008/FarmSecurity/blob/main/Team_AI/All/FarmSecurity_ipynb/220718/farmSecurity.ipynb) <br>

# :factory: YOLO를 활용한 객체 인식 과정(train&test 포함)
## :one: __학습 Dataset 개수 차이에 따른 비교__ <br>
:heavy_check_mark: 학습을 진행할 때마다 weights 파일 생성. Iteration을 중점으로 1000단위마다 파일 생성<br>
- 예) yolov4-1000.weights / yolov4-3000.weights 등<br>

:heavy_check_mark: 이 weights 파일을 통해 Avg Loss, mAP, IoU 등을 알 수 있음<br>
:heavy_check_mark: 보통 6000 Itertaion으로 많이 채택하여 사용<br>

__1) 이미지 수 기준(150장 vs 750장)__ <br>
:heavy_check_mark: 새 / 동물 / 사람 별 각 50장 씩 추출한 총 150장과, 각 150장 씩 추출한 총 750장 간 학습 및 실험에서 Iteration에 따른 Avg Loss 및 IoU 측정 <br><br>

__#1 Avg Loss, IoU 측면__ <br>

__1. 150장__
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/180736911-2fbc9124-587a-479b-b452-22a9e43c27c0.png" alt="50_dataset_6000_train_chart" width="500" height="500"/>
</div>

:stars: 위 표에서 파란색 선은 Avg Loss, 빨간색 선은 IoU를 지칭<br>
:stars: 150장에서 Avg Loss는 Iteration이 커질수록 감소, IoU는 Itration이 커질수록 증가 <br>

__2. 750장__
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/180737017-ea6835b5-eedc-4803-8f47-a45de30a0895.png" alt="250_dataset_6000_train_chart" width="500" height="500"/>
</div>

:stars: 각 선이 의미하는 바는 150장의 표와 동일<br>
:stars: 150장과 동일하게 750장에서도 Avg Loss는 Iteration이 커질수록 감소, IoU는 Itration이 커질수록 증가<br><br>

__3. 2250장__
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/192546039-d4f4b0e0-d09b-4910-8cb7-52236db6e0de.png" alt="750_dataset_6000_train_chart" width="500" height="500"/> </div> <br>
  
:stars: 각 선이 의미하는 바는 150장 및 750장 표와 동일<br>
:stars: 150장 및 750장과 동일하게 2250장에서도 Avg Loss는 Iteration이 커질수록 감소, IoU는 Itration이 커질수록 증가<br><br>

__4. 공통점__ <br>
:stars: 이미지 수와 상관없이 Iteration이 증가할수록, 대부분 Avg Loss는 감소 / IoU는 증가한다는 점을 알 수 있음<br><br>

__#2 mAP 측면__

__1. 이미지 수 기준(총 150장 vs 총 750장)__ <br>
:heavy_check_mark: 150장, 750장 훈련에서 발생한 각 6000.weights 파일 기준으로 test하여 mAP 측정 진행<br>

<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/180740427-84b54fec-7707-448b-a892-021542efb56e.JPG" alt="250vs750_mAP_chart" width="800" height="350"/>
</div>

:stars: 150장의 mAP는 58.87%인 반면, 750장은 mAP가 70.97%로 약 12% 정도 차이를 보임<br>
:stars: 750장이 확실히 mAP가 높은 것을 알 수 있음<br>
:stars: 이를 통해 사진 수가 많을수록 mAP가 높다는 것을 알 수 있음<br><br>

__2) Iteration 기준(6000번 vs 9000번)__ <br>
:heavy_check_mark: 동일한 이미지 수에서 Iteration이 차이날 경우 Avg Loss, IoU의 수치는 상호 간 약간의 차이가 있을 수 있으나 6000번과 9000번의 그래프 형상은 비슷한 모습이므로, Avg Loss와 IoU는 생략하였음<br>

<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/180742261-78b76660-9b3c-4112-9ddb-2e81632df34b.JPG" alt="6000Iterationvs6000Iteration_mAP_chart" width="800" height="350"/>
</div>

:stars: 이미지 수가 차이났을 때의 mAP는 확연한 차이가 있었으나, 이미지 수가 동일한 상황 속에서 Iteration이 3000번 정도나 차이가 남에도 불구하고 두 mAP의 차이는 1.2정도밖에 나지 않음<br>
:stars: 즉, Iteration의 차이는 mAP에 큰 영향을 미치지 않는다는 것을 알 수 있음<br>

__3) 2250장 mAP__ <br>
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/192548486-5b21349c-d4c4-4816-9341-0fc7e922ccc8.png" alt="2250_mAP" width="300" height="300"/>
</div>

:stars: 위 사실을 토대로 2250장을 학습했을 경우 위 이미지와 같이 mAP가 88.12%를 기록<br>
:stars: 즉, Dataset이 많은 상태로 학습 할 경우 학습 효과가 높아진다는 것을 알 수 있음<br>

## :two: 기법을 적용한 정확도 개선 확인 <br>
✔️ 1️⃣에서 확인할 수 있듯이 이미지 수를 늘릴수록, mAP 등 정확도가 높아진다는 것을 알 수 있음 <br>
✔️ 이미지 수만 늘려서 정확도를 높이기보다, 특정 기법을 적용해서 정확도를 높이기 위한 방식을 찾아보았음 <br>
✔️ sharpning 기법과 sobel 기법을 적용하여 test를 진행하였음 <br>

### 🔪 sharpning 기법 <br>
✔️ ["sharpning"이란](https://marisara.tistory.com/entry/%ED%8C%8C%EC%9D%B4%EC%8D%AC-openCV-13-%EB%B8%94%EB%9F%AC%EB%A7%81blurring%EC%8A%A4%EB%AC%B4%EB%94%A9smoothing%EC%83%A4%ED%94%84%EB%8B%9Dsharpening)  <br>
✔️ sharpning 기법을 적용한 결과는 아래와 같음 <br>

<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/185849277-7f990217-69c1-4b6e-a371-376ca7889cd7.JPG" width="300" height="300"/>
</div> <br>

🌠 750장 test시 사용했던 동일한 Dataset에 sharpning 기법을 적용 후 train하였고, 이 중 6000.weights 파일 기준으로 test하여 mAP 측정 진행한 것임<br>
🌠 아무 기법 적용하지 않은 750장 mAP 결과보다, sharpning 기법을 적용한 mAP가 약 10% 정도 낮은 것을 확인할 수 있음<br>

### 🔮 sobel 기법 <br>
✔️ ["sobel"이란](https://deep-learning-study.tistory.com/205)  <br>
✔️ sobel 기법을 적용한 결과는 아래와 같음 <br>

<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/185861525-a0d738c5-ba70-4a03-83f5-ec7c2712da0d.png" width="300" height="300"/>
</div> <br>

🌠 750장 test시 사용했던 동일한 Dataset에 sobel 기법을 적용 후 train하였고, 이 중 6000.weights 파일 기준으로 test하여 mAP 측정 진행한 것임<br>
🌠 아무 기법 적용하지 않은 750장 mAP 결과보다, sobel 기법을 적용한 mAP가 약 13~14% 정도 낮은 것을 확인할 수 있음<br>

## 3️⃣ 명확한 전처리를 통한 정확도 개선  <br>
✔️ 2️⃣ 에서 확인할 수 있듯이 sharpning, 차영상, sobel 기법을 통해 정확도가 개선되지 않음을 알 수 있음<br>
✔️ 이에 특정 기법을 적용해서 정확도 개선을 기대하기 보다는, 더 명확한 전처리를 통해 개선을 기대하기로 함<br>
✔️ 맨 처음 150장, 750장을 학습시키기 위한 전처리는 labeling과 각 이미지 속에 있는 객체 좌표값만 있었음<br>
✔️ 이에 따라, 아래와 같은 방식으로 전처리를 진행하였음<br>

### 🍑 이전 전처리 방식과 달라진 점<br>
🌠 __416*416으로 모든 데이터 크기 맞추기 및 좌표 재설정__ <br>
- 이전에는 각 이미지의 크기가 달랐고, cfg 파일에서 416*416 사이즈로 입력받도록 하였음<br>
- 하지만, 객체 좌표값은 기존 이미지 크기에 입각한 좌표값인데, 416*416 사이즈로 변경될 경우 이 좌표값과 matching이 안되는 경우가 발생<br>
- 이에 따라 cfg에서 resize를 진행하기 보다, 애초에 모든 이미지 size를 416*416으로 조정<br>
- 이후 이 사이즈 규격을 기반으로 객체 좌표값 산출<br>

🌠 __해상도 높이기(bilateralFilter)__ <br>
- [이미지 속 객체 해상도 높이기 참고 링크](https://deep-learning-study.tistory.com/164) <br>
- 해상도를 높이면 학습 효과가 더 올라가는 경우가 존재함에 따라 해상도를 높임<br>

🌠 __cfg 파일도 재설정__ <br>
- cfg 파일 점검 재진행<br>

🌠 __animal, bird, human 클래스 데이터별로 train, test, valid 크기 맞추기__ <br>
- 예전 학습 때에는 한 Dataset 파일에 animal, bird, human 클래스 데이터를 넣고 train, test, valid를 임의로 6:2:2 비율로 나누었음<br>
- 이럴 경우, 각 클래스 데이터별로 6:2:2로 나누어지는 것이 아니기 때문에, 특정 클래스 데이터가 더 많은 학습 데이터에 사용될 수도 안될수도 있음<br>
- 이에 따라, 클래스 데이터별로 폴더로 나누어 train, test, valid를 6:2:2 비율로 정확히 나누었음<br>
- 이로 인해 모든 클래스 데이터가 공평한 이미지 수로 학습에 참여할 수 있게 되었음<br>

🌠 __yolov4 정확도 높이기(cfg 관련) 적용__ <br>

### 🎸 달라진 결과 확인(mAP)<br>

<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/186552231-3e3637c5-888a-4ba1-9bf3-f59944a1a5ba.png" width="350" height="350"/>
</div> <br>

- 학습 때 사용한 Dataset은 이전 전처리 방식의 750장 Dataset과 동일함<br>
- 이전 전처리 방식에서의 mAP는 약 71%였던 것에 비해, 이번 전처리 방식에서의 mAP는 약 77%로 6% 가량 오른 것을 확인할 수 있음<br>
- 이를 통해, 명확한 전처리 방식이 학습 효과 향상에 기여한다는 것을 확인할 수 있음<br>

### 🌸 달라진 결과 확인(동영상)<br>
✔️ README.md에 동영상(GIF)를 올리려 했으나, 동영상 용량 문제로 인해, 각 영상 별 대표 이미지와 각 영상 설명란에 유튜브 링크를 첨부하였습니다.<br>
✔️ 확실한 차이는 각 영상 별 이미지보다 유튜브에 더 잘 보이기 때문에, 유튜브 링크를 통해 차이를 꼭 비교하시길 바랍니다.<br>
✔️ 각 유튜브 영상에서 가운데를 기준으로 __왼쪽이 old version, 오른쪽이 new version에 해당합니다.__ <br>
✔️ __old version은 명확한 전처리 이전의 학습 결과물로 동영상을 test한 것입니다.__ <br>
✔️ __new version은 명확한 전처리 이후의 학습 결과물로 동영상을 test한 것입니다.__ <br>
✔️ 탐지 개체마다 Bounding Box가 존재하는데, __Red Box는 animal, Blue Box는 bird, Green Box는 human으로 Yolo가 탐지했을 때 생깁니다.__ <br><br>

__#1 영상 1__<br>
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/188833756-ed099fb8-1c5b-4fd6-9f69-a389487a64b7.JPG" width="350" height="250"/>
</div> <br>

🌠 [영상 1 시청하러 가기](https://www.youtube.com/watch?v=m7LAfAoSNYU&list=PLXqZ70DL_8OpOzJjbJVKdIw7nivN18d2O&index=1) <br>
🌠 old version에서는 __한 개체에 여러개의 Bounding Box가 생성__ <br>
🌠 new version에서는 __한 개체에 하나의 Bounding Box가 생성__ <br><br>

__#2 영상 2__<br>
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/188833763-6b2e7a76-7532-4752-9ecc-1e8953b234cd.JPG" width="350" height="250"/>
</div> <br>

🌠 [영상 2 시청하러 가기](https://www.youtube.com/watch?v=VWNyDYlmNVE&list=PLXqZ70DL_8OpOzJjbJVKdIw7nivN18d2O&index=2) <br>
🌠 old version에서는 다람쥐가 나무 위에 있어서 __다람쥐를 animal이 아닌 bird로 인식__ <br>
🌠 new version에서는 __다람쥐를 animal로도 인식함. 다만, 다람쥐를 animal, bird로 동시에 인식.__ 해당 문제는 지속적인 학습을 통해 해결해야 할 필요성이 있음 <br><br>

__#3 영상 3__<br>
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/188833772-9bbde1e3-dabd-4e0a-ae66-aee12a7916a0.JPG" width="350" height="250"/>
</div> <br>

🌠 [영상 3 시청하러 가기](https://www.youtube.com/watch?v=1QCsn8tHLl4&list=PLXqZ70DL_8OpOzJjbJVKdIw7nivN18d2O&index=3) <br>
🌠 old version에서는 __새때 중 대부분 또는 일부 새를 인식하지 못하였음__ <br>
🌠 new version에서는 __새때 중 대부분의 새를 인식__ <br><br>

__#4 영상 4__<br>
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/188833789-06ec5ee2-5e6f-414e-9131-5af6666e238c.JPG" width="350" height="250"/>
</div> <br>

🌠 [영상 4 시청하러 가기](https://www.youtube.com/watch?v=EjBetpMZBPo&list=PLXqZ70DL_8OpOzJjbJVKdIw7nivN18d2O&index=4) <br>
🌠 old version에서는 __사람을 사람이 아닌 새로 인__식 <br>
🌠 new version에서는 __사람을 대부분 사람으로 인식.__ 일부는 아직 새로 인식하는데, 해당 문제는 지속적인 학습을 통해 해결해야 할 필요성이 있음 <br><br>


### 🎰 Number of objects per class (클래스별 탐지 객체 수)

<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/186587636-5cbdfe8e-6471-45ae-b3c5-a16e4e423438.png" width="450" height="350"/>
</div> <br>

- True Positive(이하 TP) : 예측을 잘함<br>
- False Positive(이하 FP) : false 인데 positive로 판단<br>
- bird의 FP/TP+FP는 약 0.18을 기록<br>
- animal의 FP/TP+FP는 약 0.11을 기록<br>
- human의 FP/TP+FP는 약 0.19를 기록<br>
- 즉, bird와 human의 오탐율은 비교적 높고, animal의 오탐율은 비교적 낮음을 확인할 수 있음<br>

## 4️⃣ 날씨 및 환경에 따른 학습 및 실험 영향 확인 <br>
✔️ 이전까지는 날씨 및 환경이 좋은 사진들로만 학습 및 실험을 진행하였음 <br>
✔️ 하지만 __날씨 및 환경이 상대적으로 좋지 않을 경우(예 : 눈/비/일출/일몰), 이러한 것이 객체 인식에 영향을 줄 수 있음__ <br>
✔️ 이에 따라, 본 연구팀은 __날씨 및 환경이 좋았던 상황에서 77% mAP를 기록하기 위해 750장의 dataset이 필요한 것처럼, 날씨 및 환경이 좋지 않을 경우 약 77%를 기록하기 위해, 얼마나 많은 dataset이 필요한지 확인해보기로 했음__ <br>
✔️ 이에 따라, 각 종류별 사진들을 눈/비/일출&일몰/밤 필터 총 4개와 합성하여 총 750장의 이미지를 생성 <br>
✔️ 각 필터 적용된 종류별 구성은 다음과 같음 > 일출&일몰(60장)+눈(60장)+비(65장)+밤(65장) > 한 종류당 250장 <br>

__1. 이미지 생성__  <br>
🌠 각 필터와, 동일한 동물 이미지에 대한 결과의 예시는 아래와 같음  <br>
🌠 결과 이미지는 흑백 처리까지 한 결과임  <br>

__#1 이미지 필터__  <br>

<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/180904419-008b90d7-005c-4c4c-b597-04960853f0f6.JPG" alt="img-filters" width="500" height="300"/></div>

__#2 필터 적용 이미지__  <br>

<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/180904530-f19a422f-12e5-4581-a4ab-17bd652ea076.JPG" alt="img-filters-results" width="500" height="300"/></div> <br>
  
__#3 학습 이후 test한 이미지__ <br>
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/188819244-b93ff5a4-b2a0-42bd-8b2d-29df0471270b.JPG" alt="img-filters-results" width="500" height="300"/></div> <br>
✔️ 해당 이미지는 비 필터가 적용된 동물(animal) 사진임 <br>
✔️ 필터가 적용돼도 잘 탐지하는 것을 확인할 수 있음 <br>
    
<br>

__#4 필터 적용 mAP__ <br>
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/188819737-7d46334a-f391-4063-9b63-b299daa88df0.JPG" alt="" width="500" height="300"/></div> <br>
<br>

__#5 결과__ <br>
✔️ 필터가 적용되지 않은 mAP와 적용된 mAP는 각각 __약 77%와 74%로로, 3% 정도의 차이를 기록__ <br>
✔️ 해당 과정을 실험하기 전에는 필터가 적용된 것이 안된 것보다 __mAP 측면에서 10~15% 정도 낮을 것으로 예상__ <br>
✔️ 그러나, 필터가 적용된 것의 mAP가 안된 것보다 약 3% 낮기는 하나, __필터가 적용된 것을 감안하였을 때 예상보다는 많이 좋은 mAP를 산출__ <br>
✔️ 실제 상황에서는 적용된 필터보다 더 다양한 날씨 및 환경이 존재하겠으나, __특별한 경우가 아닐 경우 대부분 상황에서도 객체 인식을 정상적으로 수행할 것으로 기대 가능__ <br>

__#6 참고 자료__ <br>
1. [전·후처리를 이용한 딥러닝 기반의 주차여부인식](https://www.kci.go.kr/kciportal/ci/sereArticleSearch/ciSereArtiView.kci?sereArticleSearchBean.artiId=ART002519323) <br>
2. [파이썬 코드를 이용한 파이카메라 제어](https://neosarchizo.gitbooks.io/raspberrypiforsejonguniv/content/chapter4.html) <br>

<br><br> 

## 5️⃣ 차영상 이후 YOLO 실행 <br>
✔️ 실제 촬영되는 영상을 입력받을 경우, 계속 YOLO로 탐지를 진행하고 있으면 프로그램에 과부하가 높을 것으로 예상 <br> 
✔️ 이에 따라, 먼저 입력된 영상 속에서 차영상이 나타날 경우에 YOLO를 가동시켜 해당 객체가 동물/새/사람인지 구분 <br>
✔️ 이를 통해 프로그램 상 주어질 수 있는 과부화 중 일부를 완화할 수 있을 것으로 기대 <br>
✔️ 더 나아가, 여러 프로젝트에서 대부분 객체 인식을 실시간 YOLO로만 구동시키는데, 본 연구팀은 차영상 이후 YOLO를 실행함으로써 실행 방식에서의 차이를 기대 <br><br>

### 차영상 적용 전후 비교 <br>
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/190208416-f6b16c64-db28-4cfe-b1fd-d8a7a1e19dc7.JPG" alt="차영상-적용-전후-비교" width="500" height="300"/></div> <br><br>
🌠 차영상 적용 전이나 후나 먼저 영상에서 객체 탐지 진행 후 판별을 진행 <br>
🌠 판별 부분에서는 동일한 성능을 보여줌 <br>
🌠 탐지 부분에서는 차영상 적용 전과 후가 약 45초 정도 차이나는 것을 확인할 수 있음 <br>
🌠 차영상 이후 YOLO를 가용하는 것이 시스템 측면에서 유리한 것을 알 수 있음 <br>
🌠 본 시스템처럼, 특정 객체를 퇴치해야 하는 경우 더 효율적인 방안임을 알 수 있음 <br><br>

# 🎭 객체 인식 정보 서버에 송신하기
- Yolo에서 동물 또는 새를 인식하면, 이에 대한 정보(예 : 객체 탐지 시간 / 탐지 객체 캡처 사진) 등을 최종적으로 클라이언트에 보여주기 위해 이 값들을 서버에 보내주어야 한다. 객체 탐지 시간 등에 대한 정보는 단순 text이기 때문에 이를 처리하는 과정은 단순하다. 하지만, 캡처된 이미지 자체를 보내주는 것은 어렵다. 이를 해결하기 위해서는 캡처 이미지를 공개 URL 등을 통해 다른 곳에서도 쉽게 접근해야 할 것이다. 이에 대한 과정을 ~~~  __!!!!!추가 작성 필요!!!!!__ <br><br>


# ⚠️ 사용자에게 객체 인식 이미지 보여주기 <br>
 __!!!!!작성 필요!!!!!__ <br><br>

# ⏰ 알림을 위한 Firebase 채택 이유 <br>
✔️ 서버에서 앱으로 푸쉬 알림을 전송하는 기능을 구현하기 위해서는 파이어베이스와 브로드캐스트리시버 2종류의 선택지가 존재했음 <br>
✔️ 해당 프로젝트는 앱이 실행 중이지 않을 때(즉, 백그라운드에서 실행되고 있을 경우)에도 알림이 필요할 경우, 클라이언트에게 알림을 전송해야 함 <br>
✔️ 이에 따라, 앱이 실행 중이거나 특수한 이벤트 발생 시에 작동되는 브로드캐스트리시버는 부적절하다고 판단 <br>
✔️ 따라서, 필요 조건을 충족할 수 있는 파이어베이스를 선택 <br>

# ✉️ GCM(Google Cloud Messaging) 구성 <br>
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/188641475-ea68109a-baf2-4b81-b0eb-464eb2aed978.JPG" alt="GCM" width="550" height="400"/>
</div> <br>

# 📮 Firebase 클라우드 메시징(이하, FCM)의 동작 원리 <br>
<div align="center">
  <img src="https://user-images.githubusercontent.com/80700537/188641863-e223bc87-7b27-45ca-80fa-c1e56da04d02.JPG" alt="Firebase" width="550" height="400"/>
</div> <br>

1. 클라이언트 앱에서 Sender ID를 이용해 등록을 요청 <br>
2. FCM은 클라이언트 앱에서 전달받은 SenderId를 토대로 Registration Token을 클라이언트 앱에서 발급 <br>
3. 클라이언트 앱은 FCM에서 전달받은 Registration Token을 앱 서버에 전달하고, 이를 전달받은 앱 서버는 Registration Token을 저장 <br>
4. 앱 서버는 Registration Token, API Key, 전송할 메시지를 이용하여 GCM에 메시지를 전송 <br>
5. FCM은 앱 서버로부터 전달받은 메시지를 해당 클라이언트 앱에 메시지를 전송 <br>

# 🎹 __기타__ <br>
 __!!!!!작성 필요!!!!!__ <br><br>

# :rocket: 성과 및 기대 효과
:one: __농작물 재배 편의성 측면__ <br>
:heavy_check_mark: 농경지 면적이 많은 전남, 경북 등의 농업 종사자에게 무인 시스템을 제공함으로써 농작물 피해 규모 완화<br><br>

:two: __지역 경제 활성화 측면__ <br> 
:heavy_check_mark: 질 좋은 농작물 재배 및 수출을 통해 지역 경제 활성화<br>
:heavy_check_mark: 양질의 농작물 재배를 통한 해외 농작물 수입 억제<br>

# :checkered_flag: 참여 대회
:one: __2022 KOAT 아이디어 경진대회__ <br>
> :running: [제출 파일 다운로드하러 가기](https://github.com/irishNoah/FarmSecurity_irish/blob/main/contest_exhibit/2022_KOAT/%EB%B0%95%EC%B0%BD%EC%98%81_2022_KOAT%ED%98%81%EC%8B%A0%EC%95%84%EC%9D%B4%EB%94%94%EC%96%B4%EA%B3%B5%EB%AA%A8%EC%A0%84_AI%EB%A5%BC%EC%9D%B4%EC%9A%A9%ED%95%9C%EB%86%8D%EC%9E%91%EB%AC%BC%ED%94%BC%ED%95%B4%EC%99%84%ED%99%94%EC%8B%9C%EC%8A%A4%ED%85%9C.hwp)
<br>

:two: __2022 청소년/청년 아이디어 경진대회__ <br>
> :running: [제출 파일 다운로드하러 가기](https://github.com/irishNoah/FarmSecurity_irish/blob/main/contest_exhibit/2022_%EC%B2%AD%EC%86%8C%EB%85%84%26%EC%B2%AD%EB%85%84/FarmSecurity(%EB%B0%95%EC%B0%BD%EC%98%81)_2022_%EC%B2%AD%EC%86%8C%EB%85%84%EC%B2%AD%EB%85%84_%EC%95%84%EC%9D%B4%EB%94%94%EC%96%B4_%EA%B2%BD%EC%A7%84%EB%8C%80%ED%9A%8C.hwp)
<br>
