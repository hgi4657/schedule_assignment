package com.sparta.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private boolean isAdmin;
}