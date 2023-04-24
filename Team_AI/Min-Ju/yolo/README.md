# yolo minju
진행하면서 코드랑 이미지들이 계속해서 변경되었는데, 과정에 따른 이미지를 올리려니까 양이 많았다.

진행하면서의 결과 파일과 최종 코드만 올렸다.

## 1. final_image
최종 weight 파일로 이미지 테스트

## 2. first_object_detection
초반기에 크롤링으로 이미지 수집 후, 라벨링한 파일인데 별 필요 없..는 것 같다.

## 3. second_object_detection
이미지 확대 실험, 논문을 검토했고, 자세한 사항은 .hwp에 정리되어 있다.

### case1)
각각 50장, 150장

animal: 멧돼지:17장, 고라니: 17장, 청설모: 16장

bird: 50장(무작위 선정)

human: 50장(무작위 선정)

### case2)

각각 250장, 총 750장

animal: 멧돼지: 85장, 고라니: 85장, 청설모: 80장

bird: 까마귀, 까치, 직박구리, 어치, 참새: 각각 50장

human: 250장

-> 해당하는 case들을 비교했다.

## 4. third_object_detection
샤프닝 필터를 적용해서 original과 샤프닝 필터를 적용시킨 사진과 샤프닝 필터만 있는 사진을 분석했다.

그리고

### case1) original 사진 750장 6000번 돌린 map

### case2) original 사진 750장 9000번 돌린 map

### case3) 샤프닝 한 사진 750장 6000번 돌린 map

를 비교했고 .hwp 정리되어 있다.

## 5. fourth_object_detection
### 1. first_bilateral Filter
• 416x416 모든 데이터 크기 resize

• resize 한 것에 대한 이미지 파일 좌표 수정

• 해상도 높이기(bilateralFilter)

• cfg 파일 수정

• animal, bird, human 데이터 클래스 별로 train,vaild,test 셋 나누기

• yolov4 에 대한 정확도 올리기 적용(cfg 수정)

하는 과정을 거쳤고, map가 71%에서 76%로 상승하게 되었다.

### 2. two_750_datasets
원래는 각각 250장으로 총 750장이었는데, 각각 750장으로 늘리기 위해 팀원들에게 라벨링을 부탁했고,  map는 76%에서 88%까지 올랐다.

### 3. three_750_labIlmg(again)
bird 라벨링에 문제가 있어서 다시 라벨링을 다시 한 결과로 map를 88.12%까지 올렸다.

## 6. 원격_maria_db
프론트, 백엔드 팀과 연결했었던 것이다.

## 7. farmSecurity_minju(최종코드).ipynb
colab에서 돌린 최종 코드이다. 코드에 대한 설명은 코드에 자세히 적어놨다.
