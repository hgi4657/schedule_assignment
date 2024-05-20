package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ScheduleResponseDto getScheduleById(Long id) throws IllegalAccessException {
        Schedule schedule = findScheduleById(id);
        return new ScheduleResponseDto(schedule);
    }

    // 일정 전체 조회
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAllByOrderByCreatedAtDesc().stream().map(ScheduleResponseDto::new).toList();
    }

    // 일정 수정
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) throws IllegalAccessException {
        Schedule schedule = findScheduleById(id);

        findScheduleByIdAndPassword(id, scheduleRequestDto.getPassword());

        schedule.update(scheduleRequestDto);
        return new ScheduleResponseDto(findScheduleById(id));
    }

    // 일정 삭제
    public Long deleteSchedule(Long id, String password) throws IllegalAccessException {
        Schedule schedule = findScheduleById(id);

        findScheduleByIdAndPassword(id, password);

        scheduleRepository.delete(schedule);
        return id;
    }

    // 해당 ID 가 존재하는지 확인
    private Schedule findScheduleById(Long id) throws IllegalAccessException {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new IllegalAccessException("선택한 일정은 존재하지 않습니다."));
    }

    // 해당 ID의 Password 가 일치하는지 확인
    private Schedule findScheduleByIdAndPassword(Long id, String password) throws IllegalAccessException {
        return scheduleRepository.findByIdAndPassword(id, password).orElseThrow(() ->
                new IllegalAccessException("선택한 일정의 비밀번호가 일치하지 않습니다."));
    }
}
