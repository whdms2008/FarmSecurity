package com.farmsecurity.restapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "log")
public class Log { // 로그 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="num")
    private long num;

    @JoinColumn(name="member_id")
    private String memberId;
    @JoinColumn(name="camera_cameraNum")
    private String cameraNum;
    private String cameraName;
    private String link;
    private String level;
    private String time;

    public Log(String memberId, String cameraNum, String cameraName, String link, String level, String time){
        this.memberId = memberId;
        this.cameraNum =cameraNum;
        this.cameraName = cameraName;
        this.link = link;
        this.level = level;
        this.time = time;
    }
}
