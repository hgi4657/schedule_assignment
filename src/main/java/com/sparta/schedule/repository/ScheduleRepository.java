package com.sparta.schedule.repository;

import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    // 생성자
    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 일정 DB 저장
    public Schedule save(Schedule schedule) {
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO schedule (title, content, manager, password, data) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, schedule.getTitle());
                    preparedStatement.setString(2, schedule.getContent());
                    preparedStatement.setString(3, schedule.getManager());
                    preparedStatement.setString(4, schedule.getPassword());
                    preparedStatement.setString(5, schedule.getData());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);

        return schedule;
    }


    // 해당 ID 가 있는지 조회
    public Schedule findById(Long id) {
        // DB 조회
        String sql = "SELECT id, title, content, manager, data FROM schedule WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getLong("id"));
                schedule.setTitle(resultSet.getString("title"));
                schedule.setContent(resultSet.getString("content"));
                schedule.setManager(resultSet.getString("manager"));
                schedule.setData(resultSet.getString("data"));
                return schedule;
            } else {
                return null;
            }
        }, id);
    }


    // 전체 조회
    public List<ScheduleResponseDto> findAll() {
        String sql = "SELECT * FROM schedule";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String manager = rs.getString("manager");
                String data = rs.getString("data");
                return new ScheduleResponseDto(id, title, content, manager, data);
            }
        });
    }


}
