package leets.attendance.common;

import lombok.Getter;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum HttpResponseStatus {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, OK.value(), "요청에 성공하였습니다."),


    /**
     * 400 : Request 오류, Response 오류
     */
    MISMATCH_PASSWORD(false, BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다."),
    UNCHECKED_ID(false, BAD_REQUEST.value(), "아이디 중복 확인을 해주세요."),
    ALREADY_DONE(false, BAD_REQUEST.value(), "이미 처리된 작업입니다."),
    INVALID_ACCESS(false, NOT_FOUND.value(), "잘못된 접근입니다."),
    LOGIN_ERROR(false, NOT_FOUND.value(), "아이디 혹은 비밀번호를 잘못 입력하였습니다."),

    /**
     * 500 : Database, Server 오류
     */
    DATABASE_ERROR(false, INTERNAL_SERVER_ERROR.value(), "오류가 발생했습니다. 잠시 후에 다시 시도해주세요.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    HttpResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}

