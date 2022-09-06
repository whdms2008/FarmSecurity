package com.farmsecurity.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.farmsecurity.restapi.model.Member;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> { // 회원 레포지토리

    List<Member> findByNameAndPhone(String name, String phone); // 아이디 찾기
    List<Member> findByIdAndPhone(String id, String phone); // 비밀번호 찾기

}
