import glob
import os
path = "C:\\Users\\freet\\Desktop\\캡스톤\\최종 이미지\\bird_test"
files = glob.glob(path + "\\*")

for i, f in enumerate(files):  
    fname, fext = os.path.splitext(f)
    new_f = f.replace('0', '')
    os.rename(f, new_f)
    print(new_f)
