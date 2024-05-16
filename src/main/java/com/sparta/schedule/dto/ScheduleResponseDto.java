package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long id;  // ID
    private String title;  // 할일 제목
    private String content;  // 할일 내용
    private String manager;  // 담당자
    private String password;  // 비밀번호
    private String data; // 작성일자

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.manager = schedule.getManager();
        this.password = schedule.getPassword();
        this.data = schedule.getData();
    }
}