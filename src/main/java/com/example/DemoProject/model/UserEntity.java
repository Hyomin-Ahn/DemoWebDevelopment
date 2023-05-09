package com.example.DemoProject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class UserEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id; //유저에게 고유하게 부여되는 id

    @Column(nullable = false)
    private String username;

    //데이터베이스에서 null 입력 가능, 컨트롤러에서는 반드시 입력하게
    private String password;

    private String role; //사용자의 롤 : 어드민, 일반 사용자 등

    private String authProvider; //OAuth에서 사용할 유저 정고 제공자 : github


}
