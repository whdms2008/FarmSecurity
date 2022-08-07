package com.farmsecurity.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {

    @Id // 기본키로 지정
    private String id;
    private String pw;
    private String name;
    private String address;
    private String phone;
    @CreationTimestamp
    private Date createdAt;

    public Member(String id, String pw, String name, String address, String phone){
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
}