package com.sparta.schedule.controller;

import com.sparta.schedule.dto.SchedulePasswdDto;
import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 일정 작성 POST
    @PostMapping("/schedule")
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@RequestBody ScheduleRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ScheduleResponseDto responseDto = scheduleService.saveSchedule(requestDto, userDetails.getUser());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 일정 선택 조회 GET
    @GetMapping("/schedule/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {
        ScheduleResponseDto responseDto = scheduleService.getScheduleById(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 일정 전체 조회 GET
    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules() {
        List<ScheduleResponseDto> responseDtos = scheduleService.getAllSchedules();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    // 일정 수정 PUT
    @PutMapping("/schedule/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id,
                                                              @RequestBody ScheduleRequestDto requestDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ScheduleResponseDto responseDto = scheduleService.updateSchedule(id, requestDto, userDetails.getUser());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 일정 삭제 DELETE
    @DeleteMapping("/schedule/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long id,
                                                 @RequestBody SchedulePasswdDto passwdDto,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long deletedId = scheduleService.deleteSchedule(id, passwdDto, userDetails.getUser());
        String message = "[ ID: " + deletedId + " ] 일정이 삭제되었습니다.";
        return ResponseEntity.ok(message);
    }
}
