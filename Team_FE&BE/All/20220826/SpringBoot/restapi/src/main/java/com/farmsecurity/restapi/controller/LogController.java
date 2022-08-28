package com.farmsecurity.restapi.controller;

import com.farmsecurity.restapi.firebase.FirebaseCloudMessageService;
import com.farmsecurity.restapi.model.Camera;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.farmsecurity.restapi.model.Log;
import com.farmsecurity.restapi.repository.LogRepository;
import com.farmsecurity.restapi.repository.CameraRepository;

import java.io.IOException;
import java.util.*;

@RequestMapping("/log") // 로그 테이블
@RestController
public class LogController {

    @Autowired
    private LogRepository logRepository;
    @Autowired
    private CameraRepository cameraRepository;
    @Autowired
    private FirebaseCloudMessageService fcm;

    // 로그 삽입
    @PostMapping("/insert")
    public Log insert(@RequestBody Map<String, String> map) throws FirebaseMessagingException, IOException {
        List<Camera> camera = cameraRepository.findByCameraNum(map.get("cameraNum"));

        if(camera.size() == 1){
            map.put("cameraName",camera.get(0).getCameraName()); // 카메라 이름 get
            map.put("memberId", camera.get(0).getMemId());    // 회원 id get
            fcm.sendMessageTo("token","알림","현재 농장의 상태를 확인해주세요");

            // 로그 정보 저장
             return logRepository.save(
                     new Log(map.get("memberId"), map.get("cameraNum"), map.get("cameraName"), map.get("link"), map.get("level"), map.get("time"))
             );
        } else{
            // 카메라 테이블에 해당하는 카메라가 없을 때 예외처리
            throw new IllegalStateException("카메라가 존재하지 않습니다.");
        }
    }

    // 로그 전체 검색
    @GetMapping("/select")
    public List<Log> selectAll(){return logRepository.findAll();}

    // 마지막 로그 검색
    @GetMapping("/select/{memberId}")
    public Log findByMemberId(@PathVariable("memberId") String memberId){
        List<Log> logs = logRepository.findByMemberId(memberId);
        return logs.get(logs.size()-1);
    }

    // 로그 검색
    @GetMapping("/select2/{memberId}") // READ
    public List<Log> selectLog(@PathVariable("memberId") String memberId){
        return logRepository.findByMemberId(memberId);
    }
}
