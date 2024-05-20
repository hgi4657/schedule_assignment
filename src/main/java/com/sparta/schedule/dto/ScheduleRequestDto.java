package com.sparta.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private Long id;  // ID
    private String title;  // 할일 제목
    private String content;  // 할일 내용
    private String manager;  // 담당자
    private String password;  // 비밀번호
}