package leets.attendance.src.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@Table(name = "attendance")
@RequiredArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isAttend;

    public Attendance attend() {
        this.isAttend = true;
        return this;
    }

    public Attendance(Week week) {
        this.startDate = week.getStartDate();
        this.endDate = week.getEndDate();
    }

    @PrePersist
    public void initIsAttend() {
        this.isAttend = false;
    }
}
