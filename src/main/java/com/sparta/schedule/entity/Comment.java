package com.sparta.schedule.entity;

import com.sparta.schedule.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ID

    @Column(name = "content", nullable = false)
    private String content; // 댓글 내용


    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment(CommentRequestDto requestDto, Schedule schedule, User user) {
        this.content = requestDto.getContent();
        this.schedule = schedule;
        this.user = user;
    }

    public void update(String content) {
        this.content = content;
    }
}
