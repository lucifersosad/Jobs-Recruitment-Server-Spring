package spring.api.uteating.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.api.uteating.entity.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

}