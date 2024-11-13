package spring.api.uteating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.api.uteating.entity.Cv;

public interface CvRepository extends JpaRepository<Cv,Long> {
}
