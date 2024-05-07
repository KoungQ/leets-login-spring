package leets.attendance.src.controller;

import leets.attendance.common.HttpResponseStatus;
import leets.attendance.common.ResponseApiMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.nio.charset.StandardCharsets;

@Controller
class BaseController {

    /**
     * 요청에 성공 시, JSON 형식으로 HTTP 응답을 생성하여 반환하는 메소드
     *
     * @param httpResponseStatus   HTTP 상태 코드
     * @param responseData 응답 데이터
     * @return ResponseEntity 객체, JSON 응답, HTTP 상태 코드
     */

    public ResponseEntity<ResponseApiMessage> sendResponseHttpByJson(HttpResponseStatus httpResponseStatus, Object responseData) {
        ResponseApiMessage responseApiMessage = ResponseApiMessage.builder()
                .httpResponseStatus(httpResponseStatus)
                .message(httpResponseStatus.getMessage())
                .responseData(responseData)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(responseApiMessage, headers, 200);
    }

    /**
     * 요청에 실패 시, JSON 형식으로 HTTP 응답을 생성하여 반환하는 메소드
     *
     * @param httpResponseStatus   HTTP 상태 코드
     * @return ResponseEntity 객체, JSON 응답, HTTP 상태 코드
     */

    public ResponseEntity<ResponseApiMessage> sendResponseHttpByJson(HttpResponseStatus httpResponseStatus) {
        ResponseApiMessage responseApiMessage = ResponseApiMessage.builder()
                .httpResponseStatus(httpResponseStatus)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(responseApiMessage, headers, httpResponseStatus.getCode());
    }
}
