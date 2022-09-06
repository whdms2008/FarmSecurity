package com.farmsecurity.restapi;

import com.farmsecurity.restapi.model.Log;
import com.farmsecurity.restapi.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootApplication
@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class AlarmScheduler {

    @Autowired
    private LogRepository logRepository;

    /**
     * 매일 날짜 체크
     * 매일 0시 1분에 날짜를 체크하도록 작동합니다.
     * 해당 시간에 되면 스프링 Scheduled 에 등록되어 있는 DailyDateCompare 가 작동합니다.
     */

    //매일 0시 1분에 날짜 체크
    @Scheduled(cron = "0 1 0 * * * ")
    public void DailyCheck(){
        int idx = 0;
        // 로그 테이블 로그 전체 검색
        List<Log> logs = logRepository.findAll();
        LocalDateTime today = LocalDateTime.now();
        for (Log log : logs) {
            // 로그 테이블의 시간 데이터와 오늘의 시간 비교
            String time = logs.get(idx).getTime();
            CompareDate(time, today);
            idx ++;
        }
    }

    // 날짜 비교
    public void CompareDate (String t, LocalDateTime today){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH:mm:ss");
        LocalDateTime time = LocalDateTime.parse(t,formatter);
        // 로그 테이블에 있는 시간 데이터를 정해진 타입에 맞게 변경
        LocalDate timeDate = LocalDate.from(time);

        // 기존 시간 데이터에 한 달 더해주기
        LocalDate timeDate2 = timeDate.plusMonths(1);
        LocalDate todayDate = LocalDate.from(today);

        if(timeDate2.isEqual(todayDate)){
            // 삽입된 데이터는 한 달이 지나면 삭제
            logRepository.deleteByTime(t);
            System.out.println("삭제완료");
        }
    }
}
