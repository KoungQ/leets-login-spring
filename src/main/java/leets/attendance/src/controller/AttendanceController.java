package leets.attendance.src.controller;

import leets.attendance.common.ResponseApiMessage;
import leets.attendance.exception.BaseException;
import leets.attendance.src.domain.User;
import leets.attendance.src.service.AttendanceService;
import leets.attendance.src.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static leets.attendance.common.HttpResponseStatus.SUCCESS;

@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
public class AttendanceController extends BaseController {

    private final AttendanceService attendanceService;
    private final UserService userService;

    @PatchMapping()
    public ResponseEntity<ResponseApiMessage> attend(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            // 인증 객체가 아닌 진짜 유저 객체 가져오기 (인증 객체는 다른 테이블에 대한 정보를 담고 있지 않음)
            User user = userService.getUser(userDetails.getUsername());
            // 출석 처리
            return sendResponseHttpByJson(SUCCESS, attendanceService.attend(user));
        } catch (BaseException e) {
            return sendResponseHttpByJson(e.getStatus());
        }
    }

    @GetMapping()
    public ResponseEntity<ResponseApiMessage> getAttendances(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            // 인증 객체로 유저 정보 조회
            User user = userService.getUser(userDetails.getUsername());
            // 객체에 매핑된 출석 객체 반환
            return sendResponseHttpByJson(SUCCESS, user.getAttendances());
        } catch (BaseException e) {
            return sendResponseHttpByJson(e.getStatus());
        }
    }

    @GetMapping("/rates")
    public ResponseEntity<ResponseApiMessage> getAttendanceRate(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            // 인증 객체로 유저 정보 조회
            User user = userService.getUser(userDetails.getUsername());
            // 출석률 구하기
            return sendResponseHttpByJson(SUCCESS, attendanceService.getAttendanceRate(user));
        } catch (BaseException e) {
            return sendResponseHttpByJson(e.getStatus());
        }
    }
}
