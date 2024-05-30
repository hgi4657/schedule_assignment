package com.sparta.schedule.service;

import com.sparta.schedule.dto.CommentRequestDto;
import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.entity.Comment;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.entity.User;
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
    public CommentResponseDto addComment(Long scheduleId, CommentRequestDto commentRequestDto, User user) {
        // 입력받은 일정 아이디가 DB에 존재하는지 확인
        // 댓글 내용이 공백 or " "이 아닌지 확인
        Schedule schedule = findScheduleById(scheduleId);

        Comment comment = new Comment(commentRequestDto, schedule, user);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    // 댓글 수정
    public CommentResponseDto updateComment(Long scheduleId, Long commentId, CommentRequestDto commentRequestDto, User user) throws IllegalAccessException {
        // 입력받은 일정 아이디가 DB에 존재하는지 확인
        // 입력받은 댓글 아이디가 DB에 존재하는지 확인
        // 입력받은 일정 아이디와 댓글 아이디가 제대로 맵핑된 것인지 확인
        // 댓글을 입력한 유저와 로그인한 유저가 동일한지 확인
        Schedule schedule = findScheduleById(scheduleId);
        Comment comment = findCommentById(commentId);

        checkScheduleIdAndCommentId(schedule, comment);

        Long loginUserId = user.getId();
        Long commentUserId = comment.getUser().getId();
        reqUserCheck(loginUserId, commentUserId);

        comment.update(commentRequestDto.getContent());
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    public Long deleteComment(Long scheduleId, Long commentId, User user) throws IllegalAccessException {
        // 입력받은 일정 아이디가 DB에 존재하는지 확인
        // 입력받은 댓글 아이디가 DB에 존재하는지 확인
        // 입력받은 일정 아이디와 댓글 아이디가 제대로 맵핑된 것인지 확인
        // 댓글을 입력한 유저와 로그인한 유저가 동일한지 확인
        Schedule schedule = findScheduleById(scheduleId);
        Comment comment = findCommentById(commentId);

        checkScheduleIdAndCommentId(schedule, comment);

        Long loginUserId = user.getId();
        Long commentUserId = comment.getUser().getId();
        reqUserCheck(loginUserId, commentUserId);

        commentRepository.delete(comment);
        return commentId;
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

    // 댓글을 입력한 유저와 로그인한 유저가 동일한지 확인
    private void reqUserCheck(Long loginUserId, Long commentUserId) throws IllegalAccessException {
        if (!loginUserId.equals(commentUserId)) {
            throw new IllegalAccessException("해당 일정을 작성한 사용자가 아닙니다");
        }
    }
}
