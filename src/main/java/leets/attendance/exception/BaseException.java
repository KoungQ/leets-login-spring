package leets.attendance.exception;

import leets.attendance.common.HttpResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends Exception {
    private HttpResponseStatus status;
}
