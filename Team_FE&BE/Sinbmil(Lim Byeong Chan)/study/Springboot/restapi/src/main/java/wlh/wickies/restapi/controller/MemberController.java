package wlh.wickies.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wlh.wickies.restapi.model.Member;
import wlh.wickies.restapi.repository.MemberRepository;

import java.util.*;

@RequestMapping("/member")
@RestController
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/insert") // CREATE
    public Member insert(@RequestBody Map<String, String> map){
        return memberRepository.save(
                new Member(map.get("name"), map.get("id"),  map.get("pw"), map.get("address"))
        );
    }

    @GetMapping("/select") // READ
    public List<Member> selectAll(){
        return memberRepository.findAll();
    }

    @GetMapping("/select/{id}") // READ
    public Member selectOne(@PathVariable("id") String id){
        return memberRepository.findById(id).orElse(null);
    }

    @PostMapping("/update/{member_num}") // UPDATE
    public Member updateOne(@PathVariable("member_num") String id, @RequestBody Map<String, String> map){
        System.out.println(id);
        System.out.println(map);
        Member member = memberRepository.findById(id).orElse(null);
        member.setName(map.get("name"));
        member.setId(map.get("id"));
        member.setPw(map.get("pw"));
        member.setAddress(map.get("address"));
        return memberRepository.save(member);
    }

    @PostMapping("/delete/{member_num}") // DELETE
    public String deleteOne(@PathVariable("member_num") String id){
        memberRepository.deleteById(id);
        return "삭제 완료";
    }

}
