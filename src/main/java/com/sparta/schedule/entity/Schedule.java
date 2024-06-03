package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ID
    @Column(name = "title", nullable = false)
    private String title;  // 할일 제목
    @Column(name = "content", nullable = false, length = 200)
    private String content;  // 할일 내용
    @Column(name = "manager", nullable = false)
    private String manager;  // 담당자
    @Column(name = "password", nullable = false)
    private String password;  // 비밀번호


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Schedule(ScheduleRequestDto scheduleRequestDto, User user) {
        this.id = scheduleRequestDto.getId();
        this.title = scheduleRequestDto.getTitle();
        this.content = scheduleRequestDto.getContent();
        this.manager = scheduleRequestDto.getManager();
        this.password = scheduleRequestDto.getPassword();
        this.user = user;
    }

    public void update(ScheduleRequestDto scheduleRequestDto) {
        this.title = scheduleRequestDto.getTitle();
        this.content = scheduleRequestDto.getContent();
        this.manager = scheduleRequestDto.getManager();
        this.password = scheduleRequestDto.getPassword();
    }
}
