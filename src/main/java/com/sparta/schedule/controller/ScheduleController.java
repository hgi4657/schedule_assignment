package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 생성자
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 작성 POST
    @PostMapping("/schedule")
    public ScheduleResponseDto saveSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.saveSchedule(requestDto);
    }

    // 일정 선택 조회 GET
    @GetMapping("/schedule/{id}")
    public Schedule getScheduleById(@PathVariable Long id) throws IllegalAccessException {
        return scheduleService.getScheduleById(id);
    }

    // 일정 전체 조회 GET
    @GetMapping("/schedule")
    public List<ScheduleResponseDto> getSchedule() {
        return scheduleService.getAllSchedules();
    }

    // 일정 수정 PUT
    @PutMapping("/schedule/{id}")
    public Schedule updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) throws IllegalAccessException {
        return scheduleService.updateSchedule(id, requestDto);
    }

    // 일정 삭제 DELETE
    @DeleteMapping("/schedule/{id}")
    public Long deleteSchedule(@PathVariable Long id) throws IllegalAccessException {
        return scheduleService.deleteSchedule(id);
    }
}
