package com.farmsecurity.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.farmsecurity.restapi.model.Camera;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CameraRepository extends JpaRepository<Camera, String> { // 카메라 레포지토리

    List<Camera> findByCameraNum(String cameraNum); // 카메라 이름 찾기
    List<Camera> findByMemId(String memId);
    @Transactional
    List<Camera> deleteByMemId(String memId); // 카메라 삭제
}
