package pro.sky.maternity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.maternity.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
}
