from PIL import Image
import os
file_path = r"C:\Users\freet\Desktop\capstone\final"
file_name = os.listdir(file_path)

for name in file_name:
    img = Image.open(file_path + "/"+ name)
    imgGray = img.convert('L')
    imgGray.save(file_path + "/"+ name)
