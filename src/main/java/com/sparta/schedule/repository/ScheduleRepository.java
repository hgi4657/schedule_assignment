package com.sparta.schedule.repository;

import com.sparta.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // ID 와 Password 검사
    Optional<Schedule> findByIdAndPassword(Long id, String password);

    // createAt 내림차순 정렬
    List<Schedule> findAllByOrderByCreatedAtDesc();
}