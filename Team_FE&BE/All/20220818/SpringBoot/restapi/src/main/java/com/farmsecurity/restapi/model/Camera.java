package com.farmsecurity.restapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name ="camera")
public class Camera { // 카메라 테이블

    @Id
    private String cameraNum;
    private String cameraName;
    @JoinColumn(name="member_id")
    private String id;

    public Camera(String cameraNum, String cameraName, String id){
        this.cameraNum = cameraNum;
        this.cameraName = cameraName;
        this.id = id;
    }
}
