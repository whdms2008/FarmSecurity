# https://deep-learning-study.tistory.com/164
import cv2
import sys
import os
import glob

files = glob.glob(
    r"C:\Users\freet\Desktop\capstone\Team_AI\Min-Ju\yolo\yolo_object_detection\four_object_detection\dataset\*.jpg")

for f in files:
    print(f)
    img = cv2.imread(f)
    dst_img = cv2.bilateralFilter(img, -1, 10, 5)

    cv2.imwrite(f, dst_img)
