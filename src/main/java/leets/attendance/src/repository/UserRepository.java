package leets.attendance.src.repository;

import leets.attendance.src.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByUsername(String username);

    Optional<User> findUserByUsername(String username);
}
