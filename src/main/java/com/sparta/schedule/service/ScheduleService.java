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
    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    // 일정 전체 조회
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    // 일정 수정
    public Schedule updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) throws IllegalAccessException {
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule != null) {
            scheduleRepository.update(id, scheduleRequestDto);
            return scheduleRepository.findById(id);
        } else {
            throw new IllegalAccessException("선택한 메모는 존재하지 않습니다");
        }

//        // 안되는 코드
//        if (schedule != null) {
//            if (!Objects.equals(schedule.getPassword(), scheduleRequestDto.getPassword())) {
//                throw new IllegalArgumentException("입력하신 비밀번호가 일치하지 않습니다.");
//            }
//            scheduleRepository.update(id, scheduleRequestDto);
//            return scheduleRepository.findById(id);
//        } else {
//            throw new IllegalStateException("선택한 메모는 존재하지 않습니다");
//        }
    }

}
