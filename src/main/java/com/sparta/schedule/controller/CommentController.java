package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommentRequestDto;
import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/schedule/{scheduleId}/comments")
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable Long scheduleId, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto responseDto = commentService.addComment(scheduleId, commentRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

}
