package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long id;  // ID
    private String title;  // 할일 제목
    private String content;  // 할일 내용
    private String manager;  // 담당자
    private String password;  // 비밀번호
    private String data; // 작성일자

    public Schedule(ScheduleRequestDto scheduleRequestDto) {
        this.id = scheduleRequestDto.getId();
        this.title = scheduleRequestDto.getTitle();
        this.content = scheduleRequestDto.getContent();
        this.manager = scheduleRequestDto.getManager();
        this.password = scheduleRequestDto.getPassword();
        this.data = scheduleRequestDto.getData();
    }
}
