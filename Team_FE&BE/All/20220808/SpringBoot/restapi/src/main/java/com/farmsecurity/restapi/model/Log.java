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
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="num")
    private long num;
    @JoinColumn(name="camera_cameraNum")
    private String cameraNum;
    private String cameraName;
    private String link;
    private String level;
    private String time;

    @CreationTimestamp
    private Date createdAt;

    public Log(String cameraNum, String cameraName, String link, String level, String time){
        this.cameraNum =cameraNum;
        this.cameraName = cameraName;
        this.link = link;
        this.level = level;
        this.time = time;
    }
}
