import os
file_path = r"C:\Users\freet\Desktop\capstone\Team_AI\Min-Ju\yolo\yolo_object_detection\four_object_detection\two_750_datasets\750datset\dataset_all_750\human"
file_name = os.listdir(file_path)

i = 1  # 파일에 들어가는 숫자
cnt = 1  # 돌아가는 횟수
for name in file_name:
    src = os.path.join(file_path, name)
    path, ext = os.path.splitext(name)

    if cnt % 2 == 0:
        i -= 1
    dst = 'human' + str(i) + ext
    print(dst)
    dst = os.path.join(file_path, dst)
    os.rename(src, dst)
    cnt += 1
    i += 1
