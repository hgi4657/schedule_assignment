package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommentRequestDto;
import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule/{scheduleId}/comments")
public class CommentController {
    private final CommentService commentService;

    // 댓글 등록
    @PostMapping
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable Long scheduleId,
                                                         @RequestBody @Valid CommentRequestDto commentRequestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.addComment(scheduleId, commentRequestDto, userDetails.getUser());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 댓글 수정
    @Transactional
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long scheduleId,
                                                            @PathVariable Long commentId,
                                                            @RequestBody @Valid CommentRequestDto commentRequestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.updateComment(scheduleId, commentId, commentRequestDto, userDetails.getUser());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long scheduleId,
                                                @PathVariable Long commentId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long deletedId = commentService.deleteComment(scheduleId, commentId, userDetails.getUser());
        String message = "[ ID: " + deletedId + " ] 댓글이 삭제되었습니다.";
        return ResponseEntity.ok(message);
    }
}
