package leets.attendance.src.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import leets.attendance.src.dto.RegisterUserRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 유저 인덱스

    @NotNull    // 수정: 추후 NotNull Validation으로 수정
    private String username;    // 로그인 시 필요한 아이디

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pwd;

    @NotNull
    private String name;

    @NotNull
    private String part;    // 파트가 정해져있다면 엔티티나 enum으로 빼고싶지만.. 입력받는거같아서 String으로 설정

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Attendance> attendances;

    public User(RegisterUserRequestDto requestDto, List<Attendance> attendances) {
        this.username = requestDto.getUsername();
        this.pwd = requestDto.getPwd();
        this.name = requestDto.getName();
        this.part = requestDto.getPart();
        this.attendances = attendances;
    }
}
