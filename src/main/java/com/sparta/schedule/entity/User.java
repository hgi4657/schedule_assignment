package com.sparta.schedule.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "사용자 이름 입력은 필수입니다.")
    @Size(min = 4, max = 10, message = "최소 4자 이상, 10자 이하로 입력하세요")
    @Pattern(regexp = "^[a-z0-9]+$", message = " 알파벳 소문자(a~z), 숫자(0~9)으로 구성되어야 합니다")
    private  String username;  // 사용자이름(유저 ID)

    @Column(nullable = false)
    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    @Size(min = 8, max = 15, message = "최소 8자 이상, 15자 이하로 입력하세요")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "알파벳 대소문자(a~z, A~Z), 숫자(0~9)으로 구성되어야 합니다")
    private String password;  // 비밀번호

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;  // 권한 (일반, 어드민)
}