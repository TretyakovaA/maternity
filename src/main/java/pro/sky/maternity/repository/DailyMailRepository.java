package pro.sky.maternity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.maternity.model.DailyMail;
import pro.sky.maternity.model.Reports;

public interface  DailyMailRepository extends JpaRepository<DailyMail, Long>  {
}
