##### 3) 객체 탐지 시 해당 이미지 캡처 > version2로 하면 될 것 같긴 함

# version1) box가 되어있지 않은 탐지된 이미지 저장하기
def image_url_download(url, count):  # 다운받을 data URIs, count
    from urllib import request

    with request.urlopen(url) as response:
        data = response.read()

    file_name = "image" + str(count) + ".jpg"  # 저장할 이미지 이름

    with open(file_name, "wb") as f:
        f.write(data)


# version2) box가 포함된 탐지한 이미지 저장하기
def image_url_download2(img_url, bbox_url, count):  # 다운받을 data URIs, count
    from urllib import request
    from PIL import Image

    # 인식된 이미지 저장
    with request.urlopen(img_url) as response:
        data = response.read()

    image_name = "image" + str(count) + ".jpg"  # 저장할 이미지 이름

    with open(image_name, "wb") as f:
        f.write(data)

    # bbox 이미지 저장
    with request.urlopen(bbox_url) as response:
        data = response.read()

    boxImage_name = "box" + str(count) + ".jpg"  # 저장할 이미지 이름

    with open(boxImage_name, "wb") as f:
        f.write(data)

    # 인식된 이미지, bbox 이미지 합성
    finImage_name = "fin" + str(count) + ".jpg"
    fg_img = Image.open(boxImage_name).convert("RGBA")
    bg_img = Image.open(image_name).convert("RGBA")
    fg_img_trans = Image.new("RGBA", bg_img.size)
    fg_img_trans.paste(fg_img, (0, 0), mask=fg_img)
    new_img = Image.alpha_composite(bg_img, fg_img_trans).save(finImage_name, "png")