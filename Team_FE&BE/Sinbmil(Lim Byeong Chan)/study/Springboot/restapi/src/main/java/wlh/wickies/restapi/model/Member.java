package wlh.wickies.restapi.model;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_num")
    private long member_num;
    private String name;
    private String id;
    private String pw;
    private String address;
    @CreationTimestamp
    private Date createdAt;

    public Member(String name, String id, String pw, String address){
        this.name = name;
        this.id = id;
        this.pw = pw;
        this.address = address;
    }
}