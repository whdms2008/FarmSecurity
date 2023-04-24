##from urllib.request import urlretrieve
##from urllib.parse import quote_plus    
##from bs4 import BeautifulSoup as BS    
##from selenium import webdriver        
##
##
##keyword = input("Image Name : ")
##i_URL = f'https://www.google.com/search?q={quote_plus(keyword)}&sxsrf=ALeKk00OQamJ34t56QSInnMzwcC5gC344w:1594968011157&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjXs-7t1tPqAhVF7GEKHfM4DqsQ_AUoAXoECBoQAw&biw=1536&bih=754'
##
##driver=webdriver.Chrome('C://chromedriver.exe') #크롬 드라이버
##options = webdriver.ChromeOptions()
##options.add_experimental_option('excludeSwitches', ['enable-logging'])
##driver.get(i_URL)
##
##html = driver.page_source
##soup = BS(html,features="html.parser")
##
##img = soup.select('img')
##
##i_list = []
##count = 1
##
##print("Searching...")
##for i in img:
##   try:
##      i_list.append(i.attrs["src"])
##   except KeyError:
##      i_list.append(i.attrs["data-src"])
##
##print("Downloading...")
##for i in i_list:
##   urlretrieve(i,"animal_dataset/"+keyword+str(count)+".jpg")
##   count+=1
##
##driver.close()
##print("FINISH")


#원본에 가깝게 가져오는 방법사용
from selenium import webdriver
from selenium.webdriver.common.by import By
import time
from selenium.webdriver.common.keys import Keys
import urllib.request
import time
import os
from selenium.webdriver.common.action_chains import ActionChains

driver = webdriver.Chrome('C://chromedriver.exe')#chromedriver를 사용하기위한 webdriver함수 사용

keyword = str(input("insert keyword for searching : "))#서치할 키워드 입력하기
driver.get("https://www.google.co.kr/imghp?hl=ko&authuser=0&ogbl")##open google image search page #이미지 구글 사이트 열기
#driver.get("https://www.google.com/search?q=%EC%96%B4%EC%B9%98+%EB%96%BC&tbm=isch&ved=2ahUKEwi3m6OW5-74AhW6S_UHHdSLC8wQ2-cCegQIABAA&oq=%EC%96%B4%EC%B9%98+%EB%96%BC&gs_lcp=CgNpbWcQAzIECCMQJzoGCAAQChAYOgUIABCABDoECAAQGDoGCAAQHhAFUO0DWMYVYNwWaARwAHgAgAF1iAHbB5IBAzguMpgBAKABAaoBC2d3cy13aXotaW1nwAEB&sclient=img&ei=8gbLYrfaOrqX1e8P1Jeu4Aw&bih=692&biw=1294")
driver.maximize_window()##웹브라우저 창 화면 최대화
time.sleep(2)

#키워드로 입력해서 서치할 때 사용
driver.find_element_by_css_selector("input.gLFyf").send_keys(keyword) #send keyword
driver.find_element_by_css_selector("input.gLFyf").send_keys(Keys.RETURN)##send Keys.RETURN


last_height = driver.execute_script("return document.body.scrollHeight") #initialize standard of height first
while True: #break가 일어날 때 까지 계속 반복
    driver.execute_script("window.scrollTo(0, document.body.scrollHeight);") #페이지 스크롤 시키기

    time.sleep(1)

    new_height = driver.execute_script("return document.body.scrollHeight") ## update new_height
    if new_height == last_height:#이전 스크롤 길이와 현재의 스크롤 길이를 비교
        try:
            driver.find_element_by_css_selector(".mye4qd").click() ## click more button 더보기 버튼이 있을 경우 클릭
        except:
            break # 더보기 버튼이 없을 경우는 더 이상 나올 정보가 없다는 의미이므로 반복문을 break
    last_height = new_height ##last_height update

i=0

list = driver.find_elements_by_css_selector("img.rg_i.Q4LuWd")##thumnails list
print(len(list)) #print number of thumnails

#화질 좋게 받아오기
address = "C:/Users/freet/Desktop/캡스톤/Team_AI/Min-Ju/yolo/yolo_object_detection/second_object_detection/만드는 중/참새떼/" #저장할 폴더 지정
for img in list:
    ActionChains(driver).click(img).perform()
    time.sleep(1)
    imgurl = driver.find_element_by_xpath('//*[@id="Sva75c"]/div/div/div[3]/div[2]/c-wiz/div/div[1]/div[1]/div[3]/div/a/img').get_attribute("src")
    try:
        urllib.request.urlretrieve(imgurl,address+"참새"+str(i)+".jpg") #저장되는 이름 변경할 떄 바꿔야함
        i+=1
    except:
        pass

driver.close()
