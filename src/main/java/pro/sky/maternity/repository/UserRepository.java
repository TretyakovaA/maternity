package pro.sky.maternity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.maternity.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
