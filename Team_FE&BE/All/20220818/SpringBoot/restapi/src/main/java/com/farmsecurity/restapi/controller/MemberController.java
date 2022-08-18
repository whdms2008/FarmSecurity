package com.farmsecurity.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.farmsecurity.restapi.model.Member;
import com.farmsecurity.restapi.repository.MemberRepository;

import java.util.*;

@RequestMapping("/member")
@RestController
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/insert") // CREATE
    public Member insert(@RequestBody Map<String, String> map){
        return memberRepository.save(
                new Member(map.get("id"), map.get("pw"),  map.get("name"), map.get("address"), map.get("phone"), map.get("token"))
        );
    }

    @GetMapping("/select") // READ
    public List<Member> selectAll(){
        return memberRepository.findAll();
    }

    @GetMapping("/select/{id}") // READ
    public Member selectOne(@PathVariable("id") String id){return memberRepository.findById(id).orElse(null);}

//    @GetMapping("/select/{name}/{phone}")
//    public String findId(@PathVariable("name") String name, @PathVariable("phone") String phone){
//        List<Member> members = memberRepository.findByNameAndPhone(name, phone);
//        if(members.size() == 1){
//            return members.get(0).getId();
//        } else{
//            throw new IllegalStateException("아이디가 존재하지 않습니다.");
//        }
//    }

    @GetMapping("/select/{name}/{phone}")
    public Member findId(@PathVariable("name") String name, @PathVariable("phone") String phone){
        List<Member> members = memberRepository.findByNameAndPhone(name, phone);
        if(members.size() == 1){
            return members.get(0);
        } else{
            throw new IllegalStateException("아이디가 존재하지 않습니다.");
        }
    }

    @GetMapping("/select2/{id}/{phone}")
    public Member findPw(@PathVariable("id") String id, @PathVariable("phone") String phone){
        List<Member> members = memberRepository.findByIdAndPhone(id, phone);
        if(members.size() == 1){
            return members.get(0);
        } else{
            throw new IllegalStateException("아이디가 존재하지 않습니다.");
        }
    }

    @PostMapping("/update/{id}") // UPDATE
    public Member updateOne(@PathVariable("id") String id, @RequestBody Map<String, String> map){
        Member member = memberRepository.findById(id).orElse(null);
        member.setPw(map.get("pw"));
        return memberRepository.save(member);
    }

    @PostMapping("/update2/{id}") // UPDATE
    public Member updateAddress(@PathVariable("id") String id, @RequestBody Map<String, String> map){
        Member member = memberRepository.findById(id).orElse(null);
        member.setAddress(map.get("address"));
        return memberRepository.save(member);
    }

    @DeleteMapping("/delete/{id}") // DELETE
    public String deleteOne(@PathVariable("id") String id){
        memberRepository.deleteById(id);
        return "삭제 완료";
    }
}
