package leets.attendance.src.controller;

import leets.attendance.common.ResponseApiMessage;
import leets.attendance.exception.BaseException;
import leets.attendance.src.domain.User;
import leets.attendance.src.dto.CheckDuplicateIdRequestDto;
import leets.attendance.src.dto.LoginRequestDto;
import leets.attendance.src.dto.RegisterUserRequestDto;
import leets.attendance.src.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static leets.attendance.common.HttpResponseStatus.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseApiMessage> registerUser(@RequestBody RegisterUserRequestDto requestDto) {
        try {
            // 1. 아이디 중복 검사 여부
            if(!requestDto.getIsChecked())
                throw new BaseException(UNCHECKED_ID);

            // 2. 비밀번호 일치 검사
            if(!requestDto.getPwd().equals(requestDto.getCheckPwd()))
                throw new BaseException(MISMATCH_PASSWORD);

            // 3. 회원 저장
            return sendResponseHttpByJson(SUCCESS, userService.register(requestDto));
        } catch (BaseException e) {
            return sendResponseHttpByJson(e.getStatus());
        }
    }

    @GetMapping("/check-duplicate-id")
    public ResponseEntity<ResponseApiMessage> checkDuplicateId(@RequestBody CheckDuplicateIdRequestDto requestDto) {
        try {
            return sendResponseHttpByJson(SUCCESS, userService.check(requestDto.getUsername()));
        } catch (BaseException e) {
            return sendResponseHttpByJson(e.getStatus());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseApiMessage> login(@RequestBody LoginRequestDto requestDto) {
        try {
            // 1. 아이디로 유저 객체 조회
            User user = userService.getUser(requestDto.getUsername());

            // 2. 로그인 (비밀번호 대조 후 토큰 생성)
            return sendResponseHttpByJson(SUCCESS, userService.login(user, requestDto));
        } catch (BaseException e) {
            return sendResponseHttpByJson(e.getStatus());
        }
    }
}
