package leets.attendance.src.domain;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public enum Week {

    WEEK1(LocalDate.of(2024, 3, 28),
            LocalDate.of(2024, 4, 3)),
    WEEK2(LocalDate.of(2024, 4, 4),
            LocalDate.of(2024, 4, 10)),
    WEEK3(LocalDate.of(2024, 4, 11),
            LocalDate.of(2024, 4, 17)),
    WEEK4(LocalDate.of(2024, 4, 18),
            LocalDate.of(2024, 4, 24)),
    WEEK5(LocalDate.of(2024, 4, 25),
            LocalDate.of(2024, 5, 1)),
    WEEK6(LocalDate.of(2024, 5, 2),
            LocalDate.of(2024, 5, 8)),
    WEEK7(LocalDate.of(2024, 5, 9),
            LocalDate.of(2024, 5, 15)),
    WEEK8(LocalDate.of(2024, 5, 16),
            LocalDate.of(2024, 5, 22));

    private final LocalDate startDate;
    private final LocalDate endDate;

    Week(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
