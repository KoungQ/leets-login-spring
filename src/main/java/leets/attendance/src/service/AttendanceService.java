package leets.attendance.src.service;

import jakarta.transaction.Transactional;
import leets.attendance.exception.BaseException;
import leets.attendance.src.domain.Attendance;
import leets.attendance.src.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static leets.attendance.common.HttpResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    @Transactional
    public Attendance attend(User user) throws BaseException {
        try {
            // 1. 오늘 날짜로 해당 주차 출석 여부 조회
            //      == 파라미터 유저의 attendance들 중 오늘 날짜가 몇주차인지 판별 후 가져오기
            LocalDate now = LocalDate.now();
            Attendance thisWeek = user.getAttendances()
                    .parallelStream()   // 아니면 stream() ?
                    .filter(attendance -> attendance.getStartDate().isBefore(now.plusDays(1)))  // 데드라인에 겹치는 날짜도 포함하기 위해 ±1
                    .filter(attendance -> attendance.getEndDate().isAfter(now.minusDays(1)))
                    .findFirst()
                    .orElseThrow(() -> new BaseException(DATABASE_ERROR));  // User 객체는 있지만 적절한 Attendance 객체가 없다면 데이터베이스 오류

            // 2. 출석 처리
            return thisWeek.attend();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public Integer getAttendanceRate(User user) {
        long total = user.getAttendances().stream()
                .filter(attendance -> attendance.getStartDate().isBefore(LocalDate.now()))
                .count();

        long attend = user.getAttendances().stream()
                .filter(Attendance::getIsAttend)
                .count();

        if(total == 0)
            return 0;

        return (int) (((double) attend / total) * 100);
    }
}
