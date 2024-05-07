package leets.attendance.src.service;

import leets.attendance.exception.BaseException;
import leets.attendance.src.domain.User;
import leets.attendance.src.dto.UserRequestDto;
import leets.attendance.src.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static leets.attendance.common.HttpResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User register(UserRequestDto requestDto) throws BaseException {
        try {
            return userRepository.save(new User(requestDto));
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean check(UserRequestDto requestDto) throws BaseException {
        try {
            return userRepository.existsById(requestDto.getId());
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
