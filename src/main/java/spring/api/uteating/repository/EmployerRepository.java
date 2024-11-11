package spring.api.uteating.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.api.uteating.entity.Employer;
import spring.api.uteating.entity.User;

import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Optional<Employer> findByEmail(String email);
    @Query("select u from Employer u where u.email = :email")
    Employer getEmployerByEmail(@Param("email") String email);
}