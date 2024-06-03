package com.sparta.schedule.service;

import com.sparta.schedule.dto.SchedulePasswdDto;
import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    // 일정 작성
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto, User user) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto, user);
        // DB 저장
        Schedule saveSchedule = scheduleRepository.save(schedule);
        // Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(saveSchedule);

        return scheduleResponseDto;
    }

    // 일정 선택 조회
    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = findScheduleById(id);
        return new ScheduleResponseDto(schedule);
    }

    // 일정 전체 조회
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAllByOrderByCreatedAtDesc().stream().map(ScheduleResponseDto::new).toList();
    }

    // 일정 수정
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto, User user) {
        // 해당 ID 가 존재하는지 확인
        // 일정을 작성한 유저와 해당 기능을 요청한 유저가 동일한지 확인
        // Password 가 일치하는지 확인
        Schedule schedule = findScheduleById(id);

        Long loginUserId = user.getId();
        Long scheduleUserId = schedule.getUser().getId();
        reqUserCheck(loginUserId, scheduleUserId);

        checkPasswd(schedule.getPassword(), scheduleRequestDto.getPassword());

        schedule.update(scheduleRequestDto);
        return new ScheduleResponseDto(findScheduleById(id));
    }

    // 일정 삭제
    public Long deleteSchedule(Long id, SchedulePasswdDto passwdDto, User user) {
        // 해당 ID 가 존재하는지 확인
        // 일정을 작성한 유저와 해당 기능을 요청한 유저가 동일한지 확인
        // Password 가 일치하는지 확인

        Schedule schedule = findScheduleById(id);

        Long loginUserId = user.getId();
        Long scheduleUserId = schedule.getUser().getId();
        reqUserCheck(loginUserId, scheduleUserId);

        checkPasswd(schedule.getPassword(), passwdDto.getPassword());

        scheduleRepository.delete(schedule);
        return id;
    }


    // 해당 ID 가 존재하는지 확인
    public Schedule findScheduleById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 일정은 존재하지 않습니다."));
    }

    // 일정을 작성한 유저와 해당 기능을 요청한 유저가 동일한지 확인
    private void reqUserCheck(Long loginUserId, Long scheduleUserId) {
        if (!loginUserId.equals(scheduleUserId)) {
            throw new IllegalArgumentException("해당 일정을 작성한 사용자가 아닙니다");
        }
    }

    // Password 가 일치하는지 확인
    private void checkPasswd(String password, String passwordDto) {
        if (!Objects.equals(password, passwordDto)) {
            throw new IllegalArgumentException("등록하신 비밀번호와 일치하지 않습니다.");
        }
    }
}
