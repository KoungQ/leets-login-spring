package leets.attendance.src.repository;

import leets.attendance.src.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsById(String id);
}
