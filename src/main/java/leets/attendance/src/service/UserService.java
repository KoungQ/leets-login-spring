package leets.attendance.src.service;

import jakarta.transaction.Transactional;
import leets.attendance.exception.BaseException;
import leets.attendance.jwt.JwtToken;
import leets.attendance.jwt.JwtTokenService;
import leets.attendance.src.domain.Attendance;
import leets.attendance.src.domain.RefreshToken;
import leets.attendance.src.domain.User;
import leets.attendance.src.domain.Week;
import leets.attendance.src.dto.LoginRequestDto;
import leets.attendance.src.dto.RegisterUserRequestDto;
import leets.attendance.src.repository.AttendanceRepository;
import leets.attendance.src.repository.RefreshTokenRepository;
import leets.attendance.src.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static leets.attendance.common.HttpResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AttendanceRepository attendanceRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public User getUser(String username) throws BaseException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new BaseException(INVALID_ACCESS));
    }

    @Transactional
    public User register(RegisterUserRequestDto requestDto) throws BaseException {
        try {
            // 1. 비밀번호 암호화
            String hash = passwordEncoder.encode(requestDto.getPwd());
            requestDto.setPwd(hash);

            List<Attendance> attendances = Arrays.stream(Week.values())
                    .map(Attendance::new)
                    .peek(attendanceRepository::save)
                    .toList();

            // 2. 회원 정보 저장
            return userRepository.save(new User(requestDto, attendances));
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean check(String username) throws BaseException {
        try {
            return !userRepository.existsUserByUsername(username);  // 존재한다면 false, 존재하지 않는다면 true 리턴
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public JwtToken login(User user, LoginRequestDto requestDto) throws BaseException {
        try {
            // DB에서 가져온 객체와 DTO 비밀번호 대조, 다르면 throw
            if(!passwordEncoder.matches(requestDto.getPassword(), user.getPwd()))
                throw new BaseException(LOGIN_ERROR);

            // 토큰 생성
            JwtToken token = jwtTokenService.generateToken(new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));
            // refreshToken DB 저장
            refreshTokenRepository.save(new RefreshToken(token.getRefreshToken()));
            return token;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
