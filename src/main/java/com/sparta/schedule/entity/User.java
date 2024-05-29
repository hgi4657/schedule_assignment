package com.sparta.schedule.entity;

import com.sparta.schedule.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ID

    @Column(nullable = false)
    private String nickname;  // 별명

    @Column(nullable = false, unique = true)  // 중복 X
    private String username;  // 사용자이름(유저 ID)

    @Column(nullable = false)
   private String password;  // 비밀번호

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;  // 권한 (일반, 어드민)

    public User(SignupRequestDto signupRequestDto, UserRoleEnum role) {
        this.nickname = signupRequestDto.getNickname();
        this.username = signupRequestDto.getUsername();
        this.password = signupRequestDto.getPassword();
        this.role = role;
    }

}