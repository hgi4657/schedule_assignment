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
        // 입력받은 일정 아이디가 DB에 존재하는지 확인
        // 댓글 내용이 공백 or " "이 아닌지 확인
        Schedule schedule = findScheduleById(scheduleId);

        String content = commentRequestDto.getContent();
        nullCheckByContent(content);

        Comment comment = new Comment(commentRequestDto, schedule);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    // 댓글 수정
    public CommentResponseDto updateComment(Long scheduleId, Long commentId, CommentRequestDto commentRequestDto) {
        // 입력받은 일정 아이디가 DB에 존재하는지 확인
        // 입력받은 댓글 아이디가 DB에 존재하는지 확인
        // 입력받은 일정 아이디와 댓글 아이디가 제대로 맵핑된 것인지 확인
        // 댓글 내용이 공백 or " "이 아닌지 확인
        Schedule schedule = findScheduleById(scheduleId);
        Comment comment = findCommentById(commentId);

        checkScheduleIdAndCommentId(schedule, comment);

        String content = commentRequestDto.getContent();
        nullCheckByContent(content);

        comment.update(content);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    public Long deleteComment(Long scheduleId, Long commentId) {
        // 입력받은 일정 아이디가 DB에 존재하는지 확인
        // 입력받은 댓글 아이디가 DB에 존재하는지 확인
        // 입력받은 일정 아이디와 댓글 아이디가 제대로 맵핑된 것인지 확인
        Schedule schedule = findScheduleById(scheduleId);
        Comment comment = findCommentById(commentId);

        checkScheduleIdAndCommentId(schedule, comment);

        commentRepository.delete(comment);
        return commentId;
    }


    // 댓글 내용이 공백 혹은 " " 인지 확인
    private void nullCheckByContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용은 필수입니다");
        }
    }

    // 입력받은 일정 아이디가 DB에 존재하는지 확인
    private Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new NullPointerException("해당 일정은 존재하지 않습니다."));
    }

    // 입력받은 댓글 아이디가 DB에 존재하는지 확인
    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()->
                new  NullPointerException("해당 댓글은 존재하지 않습니다."));
    }

    // 입력받은 일정 아이디와 댓글 아이디가 제대로 맵핑된 것인지 확인
    private void checkScheduleIdAndCommentId(Schedule schedule, Comment comment) {
        if (!schedule.getId().equals(comment.getSchedule().getId())) {
            throw new IllegalArgumentException("해당 댓글은 선택하신 일정에 존재하지 않습니다.");
        }
    }
}
