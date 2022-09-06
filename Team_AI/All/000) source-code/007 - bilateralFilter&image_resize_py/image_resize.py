import os
import glob
from PIL import Image

files = glob.glob(
    r'C:\Users\freet\Desktop\캡스톤\Team_AI\Min-Ju\yolo\yolo_object_detection\four_object_detection\dataset\*.jpg')

for f in files:
    img = Image.open(f)
    img_resize = img.resize((416, 416))
    title, ext = os.path.splitext(f)
    img_resize.save(title + ext)
