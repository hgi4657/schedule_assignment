package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommentRequestDto;
import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class CommentController {
    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/{scheduleId}/comments")
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable Long scheduleId, @RequestBody @Valid CommentRequestDto commentRequestDto) {
        CommentResponseDto responseDto = commentService.addComment(scheduleId, commentRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 댓글 수정
    @Transactional
    @PutMapping("/{scheduleId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long scheduleId, @PathVariable Long commentId, @RequestBody @Valid CommentRequestDto commentRequestDto) {
        CommentResponseDto responseDto = commentService.updateComment(scheduleId, commentId, commentRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/{scheduleId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long scheduleId, @PathVariable Long commentId) {
        Long deletedId = commentService.deleteComment(scheduleId, commentId);
        String message = "[ ID: " + deletedId + " ] 댓글이 삭제되었습니다.";
        return ResponseEntity.ok(message);
    }
}
