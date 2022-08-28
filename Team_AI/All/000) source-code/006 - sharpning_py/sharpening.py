import numpy as np
import cv2
import os

file_path = r'../third_object_detection/250_dataset/test'
file_names = os.listdir(file_path)
total_origin_image_num = len(file_names)

for i in range(1, 251):
    image = cv2.imread(r'../third_object_detection/250_dataset/test/human'+str(i)+'.jpg')
    
    mask = np.array([[0, -1, 0], [-1, 5, -1], [0, -1, 0]])
    out = cv2.filter2D(image, -1, mask)
    
    cv2.imwrite(file_path+'/human'+str(i)+'.jpg', out)
    
##animal
##bird
##human
