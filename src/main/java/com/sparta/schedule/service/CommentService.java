package com.sparta.schedule.service;

import com.sparta.schedule.dto.CommentRequestDto;
import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.entity.Comment;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.CommentRepository;
import com.sparta.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    // 댓글 등록
    public CommentResponseDto addComment(Long scheduleId, CommentRequestDto commentRequestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new NullPointerException("해당 일정은 존재하지 않습니다.")
        );

        String content = commentRequestDto.getContent();
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용은 필수입니다");
        }

        Comment comment = new Comment(commentRequestDto, schedule);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    // 댓글 수정
    public CommentResponseDto updateComment(Long scheduleId, Long commentId, CommentRequestDto commentRequestDto) {
        // 입력받은 일정 아이디가 DB에 존재하는지 확인
        // 입력받은 댓글 아이디가 DB에 존재하는지 확인
        // 입력 받은 일정 아이디와 댓글 아이디가 제대로 맵핑된 것인지 확인
        // 수정한 댓글 내용이 공백 or 빈칸이 아닌지 확인
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new NullPointerException("해당 일정은 존재하지 않습니다.")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new  NullPointerException("해당 댓글은 존재하지 않습니다.")
        );

        if (!schedule.getId().equals(comment.getSchedule().getId())) {
            throw new IllegalArgumentException("해당 댓글은 선택하신 일정에 존재하지 않습니다.");
        }

        String content = commentRequestDto.getContent();
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용은 필수입니다");
        }

        comment.update(content);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    public Long deleteComment(Long scheduleId, Long commentId) {
        // 입력받은 일정 아이디가 DB에 존재하는지 확인
        // 입력받은 댓글 아이디가 DB에 존재하는지 확인
        // 입력 받은 일정 아이디와 댓글 아이디가 제대로 맵핑된 것인지 확인
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new NullPointerException("해당 일정은 존재하지 않습니다.")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new  NullPointerException("해당 댓글은 존재하지 않습니다.")
        );

        if (!schedule.getId().equals(comment.getSchedule().getId())) {
            throw new IllegalArgumentException("해당 댓글은 선택하신 일정에 존재하지 않습니다.");
        }

        commentRepository.delete(comment);
        return commentId;
    }
}
