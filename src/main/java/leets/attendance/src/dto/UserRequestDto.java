package leets.attendance.src.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private String id;
    private String pwd;
    private String checkPwd;
    private String name;
    private String part;
    private Boolean isChecked;  // 아이디 중복 검사 여부
}
