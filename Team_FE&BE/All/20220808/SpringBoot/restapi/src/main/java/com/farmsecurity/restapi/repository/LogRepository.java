package com.farmsecurity.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.farmsecurity.restapi.model.Log;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {

    List<Log> findByNum(String num); // 로그 찾기
}
