package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long id;  // ID
    private String title;  // 할일 제목
    private String content;  // 할일 내용
    private String manager;  // 담당자
    private String data; // 작성일자

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.manager = schedule.getManager();
        this.data = schedule.getData();
    }

    public ScheduleResponseDto(Long id, String title, String content, String manager, String data) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.manager = manager;
        this.data = data;
    }
}