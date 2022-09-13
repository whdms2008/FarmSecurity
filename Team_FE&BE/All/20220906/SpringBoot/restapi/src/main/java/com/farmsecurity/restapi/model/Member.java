package com.farmsecurity.restapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member { // 회원 테이블

    @Id // 기본키로 지정
    private String id;
    private String pw;
    private String name;
    private String address;
    private String phone;
    private String token;

    public Member(String id, String pw, String name, String address, String phone, String token){
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.token = token;
    }
}