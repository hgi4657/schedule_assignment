package com.sparta.schedule.service;

import com.sparta.schedule.dto.SignupRequestDto;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.entity.UserRoleEnum;
import com.sparta.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 회원가입
    public void signup(SignupRequestDto requestDto) {
        // 해당 회원이 DB 에 존재하는지 확인 (중복)
        // 해당 회원의 권한 확인 ?
        UserRoleEnum role = UserRoleEnum.USER;

        Optional<User> checkDuplicateUsers = userRepository.findByUsername(requestDto.getUsername());
        if (checkDuplicateUsers.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 이름입니다");
        }

        User user = new User(requestDto, role);
        userRepository.save(user);
    }
}
