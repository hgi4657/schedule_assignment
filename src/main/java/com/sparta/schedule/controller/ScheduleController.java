package com.sparta.schedule.controller;

import com.sparta.schedule.dto.SchedulePasswdDto;
import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 일정 작성 POST
    @PostMapping("/schedule")
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@RequestBody ScheduleRequestDto requestDto) {
        ScheduleResponseDto responseDto = scheduleService.saveSchedule(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 일정 선택 조회 GET
    @GetMapping("/schedule/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) throws IllegalAccessException {
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
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) throws IllegalAccessException {
        ScheduleResponseDto responseDto = scheduleService.updateSchedule(id, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 일정 삭제 DELETE
    @DeleteMapping("/schedule/{id}")
    public ResponseEntity<Long> deleteSchedule(@PathVariable Long id, @RequestBody SchedulePasswdDto passwdDto) throws IllegalAccessException {
        Long deletedId = scheduleService.deleteSchedule(id, passwdDto);
        return new ResponseEntity<>(deletedId, HttpStatus.OK);
    }
}
