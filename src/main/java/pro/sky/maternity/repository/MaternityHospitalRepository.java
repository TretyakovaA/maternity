package pro.sky.maternity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.maternity.model.MaternityHospital;

public interface MaternityHospitalRepository extends JpaRepository<MaternityHospital, Long> {
}
