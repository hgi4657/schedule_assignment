package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @NotBlank(message = "닉네임 입력은 필수입니다.")
    private String nickname;  // 별명

    @NotBlank(message = "사용자 이름 입력은 필수입니다.")
    @Size(min = 4, max = 10, message = "최소 4자 이상, 10자 이하로 입력하세요")
    @Pattern(regexp = "^[a-z0-9]+$", message = " 알파벳 소문자(a~z), 숫자(0~9)으로 구성되어야 합니다")
    private String username;

    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    @Size(min = 8, max = 15, message = "최소 8자 이상, 15자 이하로 입력하세요")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "알파벳 대소문자(a~z, A~Z), 숫자(0~9)으로 구성되어야 합니다")
    private String password;

    private boolean admin = false;
    private String adminToken = "";
}
