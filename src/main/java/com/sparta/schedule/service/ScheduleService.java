package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // 생성자
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 일정 작성
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);
        // DB 저장
        Schedule saveSchedule = scheduleRepository.save(schedule);
        // Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(saveSchedule);

        return scheduleResponseDto;
    }

    // 일정 선택 조회
    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule getScheduleById = scheduleRepository.findById(id);
        return new ScheduleResponseDto(getScheduleById);
    }

    // 일정 전체 조회
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    // 일정 수정
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) throws IllegalAccessException {
        Schedule schedule = scheduleRepository.findById(id);

        if (!Objects.equals(scheduleRequestDto.getPassword(), schedule.getPassword())) {
            throw new IllegalAccessException("등록하신 비밀번호와 일치하지 않습니다.");
        }
        scheduleRepository.update(id, scheduleRequestDto);
        return new ScheduleResponseDto(scheduleRepository.findById(id));
    }

    // 일정 삭제
    public Long deleteSchedule(Long id, String password) throws IllegalAccessException {
        Schedule schedule = scheduleRepository.findById(id);

        if(!Objects.equals(password, schedule.getPassword())) {
            throw new IllegalAccessException("등록하신 비밀번호와 일치하지 않습니다.");
        }
        scheduleRepository.delete(id);
        return id;
    }
}
