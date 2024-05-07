package leets.attendance.src.controller;

import leets.attendance.common.ResponseApiMessage;
import leets.attendance.exception.BaseException;
import leets.attendance.src.dto.UserRequestDto;
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
    public ResponseEntity<ResponseApiMessage> registerUser(@RequestBody UserRequestDto requestDto) {
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
    public ResponseEntity<ResponseApiMessage> checkDuplicateId(@RequestBody UserRequestDto requestDto) {
        try {
            return sendResponseHttpByJson(SUCCESS, userService.check(requestDto));
        } catch (BaseException e) {
            return sendResponseHttpByJson(e.getStatus());
        }
    }
}
