package com.farmsecurity.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.farmsecurity.restapi.model.Camera;
import com.farmsecurity.restapi.repository.CameraRepository;

import java.util.*;

@RequestMapping("/camera")
@RestController
public class CameraController {

    @Autowired
    private CameraRepository cameraRepository;

    @PostMapping("/insert") // CREATE
    public Camera insert(@RequestBody Map<String, String> map){
        return cameraRepository.save(
                new Camera(map.get("cameraNum"), map.get("cameraName"), map.get("id"))
        );
    }

    @GetMapping("/select") // READ
    public List<Camera> selectAll(){
        return cameraRepository.findAll();
    }

    @GetMapping("/select/{cameraNum}") // READ
    public Camera selectCamera(@PathVariable("cameraNum") String cameraNum){return cameraRepository.findById(cameraNum).orElse(null);}

    @DeleteMapping("/delete/{cameraNum}") // DELETE
    public String deleteCamera(@PathVariable("cameraNum") String cameraNum){
        cameraRepository.deleteById(cameraNum);
        return "삭제 완료";
    }
}
