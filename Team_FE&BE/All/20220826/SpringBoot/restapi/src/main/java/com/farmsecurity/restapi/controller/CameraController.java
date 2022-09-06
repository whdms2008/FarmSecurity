package com.farmsecurity.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.farmsecurity.restapi.model.Camera;
import com.farmsecurity.restapi.repository.CameraRepository;

import java.util.*;

@RequestMapping("/camera") // 카메라 테이블
@RestController
public class CameraController {

    @Autowired
    private CameraRepository cameraRepository;

    // 카메라 삽입
    @PostMapping("/insert")
    public Camera insert(@RequestBody Map<String, String> map){
        return cameraRepository.save(
                new Camera(map.get("cameraNum"), map.get("cameraName"), map.get("memId"))
        );
    }

    // 카메라 전체 검색
    @GetMapping("/select")
    public List<Camera> selectAll(){
        return cameraRepository.findAll();
    }

    @GetMapping("/select2/{memId}")
    public Camera selectCamera2(@PathVariable("memId") String memId){
        List<Camera> camera = cameraRepository.findByMemId(memId);
        if(camera.size() == 1){
            return camera.get(0);
        } else{
            // 회원 테이블에 데이터가 존재하지 않을 때 예외 처리
            throw new IllegalStateException("카메라가 존재하지 않습니다.");
        }
    }

    // 카메라 검색
    @GetMapping("/select/{cameraNum}") 
    public Camera selectCamera(@PathVariable("cameraNum") String cameraNum){return cameraRepository.findById(cameraNum).orElse(null);}

    // 카메라 삭제
    @DeleteMapping("/delete/{memId}") // DELETE
    public String deleteCamera(@PathVariable("memId") String memId){
        cameraRepository.deleteByMemId(memId);
        return "삭제 완료";
    }
}
