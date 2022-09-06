package com.farmsecurity.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.farmsecurity.restapi.model.Member;
import com.farmsecurity.restapi.repository.MemberRepository;

import java.util.*;

@RequestMapping("/member") // 회원 테이블
@RestController
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    // 회원 생성
    @PostMapping("/insert")
    public Member insert(@RequestBody Map<String, String> map){
        return memberRepository.save(
                new Member(map.get("id"), map.get("pw"),  map.get("name"), map.get("address"), map.get("phone"), map.get("token"))
        );
    }

    // 회원 전체 검색
    @GetMapping("/select") 
    public List<Member> selectAll(){
        return memberRepository.findAll();
    }

    // 회원 검색
    @GetMapping("/select/{id}") // READ
    public Member selectOne(@PathVariable("id") String id){return memberRepository.findById(id).orElse(null);}

    // 아이디 찾기
    @GetMapping("/select/{name}/{phone}")
    public Member findId(@PathVariable("name") String name, @PathVariable("phone") String phone){
        List<Member> members = memberRepository.findByNameAndPhone(name, phone);
        if(members.size() == 1){
            return members.get(0);
        } else{
            // 회원 테이블에 데이터가 존재하지 않을 때 예외 처리
            throw new IllegalStateException("아이디가 존재하지 않습니다.");
        }
    }

    // 비밀번호 찾기
    @GetMapping("/select2/{id}/{phone}")
    public Member findPw(@PathVariable("id") String id, @PathVariable("phone") String phone){
        List<Member> members = memberRepository.findByIdAndPhone(id, phone);
        if(members.size() == 1){
            return members.get(0);
        } else{
            // 회원 테이블에 데이터가 존재하지 않을 때 예외 처리
            throw new IllegalStateException("아이디가 존재하지 않습니다.");
        }
    }

    // 비밀번호 번겅
    @PostMapping("/update/{id}") // UPDATE
    public Member updateOne(@PathVariable("id") String id, @RequestBody Map<String, String> map){
        Member member = memberRepository.findById(id).orElse(null);
        member.setPw(map.get("pw"));
        return memberRepository.save(member);
    }

    // 주소 변경
    @PostMapping("/update2/{id}") // UPDATE
    public Member updateAddress(@PathVariable("id") String id, @RequestBody Map<String, String> map){
        Member member = memberRepository.findById(id).orElse(null);
        member.setAddress(map.get("address"));
        return memberRepository.save(member);
    }

    // 회원 삭제
    @DeleteMapping("/delete/{id}") // DELETE
    public String deleteOne(@PathVariable("id") String id){
        memberRepository.deleteById(id);
        return "삭제 완료";
    }
}
