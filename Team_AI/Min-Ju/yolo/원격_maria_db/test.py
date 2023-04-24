# 참고 자료 : https://reddb.tistory.com/139
import pymysql

# 전역변수 선언부
conn = None
cur = None

sql = "" # sql 변수

# 메인 코드
conn = pymysql.connect(host='15.165.86.204', user='admin', password='12345678', db='log', charset='utf8') # 접속정보
cur = conn.cursor() # 커서생성

sql = "INSERT INTO test(camera, link, level, testdb) VALUE ('hello3','world3','PCY3', 'JMJ3');" # sql 변수에 Insert SQL문 입력
cur.execute(sql) # 커서로 sql 실행

conn.commit() # 최종 저장 
conn.close() # 접속 종료