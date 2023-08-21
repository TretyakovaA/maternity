package pro.sky.maternity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.maternity.model.User;
import pro.sky.maternity.model.UserWithExtraSupport;

public interface UserWithExtraSupportRepository extends JpaRepository<UserWithExtraSupport, Long> {
}
