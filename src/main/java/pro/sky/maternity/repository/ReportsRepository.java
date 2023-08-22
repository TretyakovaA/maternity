package pro.sky.maternity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.maternity.model.Reports;
import pro.sky.maternity.model.User;

import java.util.List;

public interface ReportsRepository extends JpaRepository<Reports, Long> {
    List<Reports> findByUser (User user);
}
