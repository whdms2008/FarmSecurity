import random
import os
from PIL import Image

# 각 클래스 당 파일을 따로 두는 것을 추천

file_path = 'C:/Users/freet/Desktop/capstone/Team_AI/Min-Ju/yolo/yolo_object_detection/four_object_detection/two_750_datasets/dataset/human'  # 늘릴 해당 파일 경로
file_names = os.listdir(file_path)  # 파일에 저장된 이름 저장
total_origin_image_num = len(file_names)  # 파일 수
augment_cnt = 1  # 이름 뒤에 붙일 cnt

for file_name in file_names:  # 파일들 하나씩 불러오기
    if file_name.endswith('.jpg'):  # 확장자가 jpg일때만

        origin_image_path = file_path + '/' + file_name  # 원래 경로에 이미지 이름 합친 경로
        save_image_path = 'C:/Users/freet/Desktop/capstone/Team_AI/Min-Ju/yolo/yolo_object_detection/four_object_detection/two_750_datasets/augmentation_dataset/human/'  # 저장할 이미지

        image = Image.open(origin_image_path)  # 이미지 불러오기
        # 이미지 기울이기
        rotated_image = image.rotate(
            random.randrange(-30, 0))  # 각도 -30 ~ 0에서 난수 추출
        rotated_image.save(save_image_path + 'human' +
                           str(augment_cnt) + '_rotated1.jpg')  # 이미지 저장

        rotated_image2 = image.rotate(
            random.randrange(0, 30))  # 각도 0 ~ -30에서 난수 추출
        rotated_image2.save(save_image_path + 'human' +
                            str(augment_cnt) + '_rotated2.jpg')  # 이미지 저장
        augment_cnt += 1  # 이름 뒤에 붙일 cnt 갯수 늘리기


# imgaug 라이브러리를 사용해서 이미지를 늘리기도 해서 나중에 해보기
