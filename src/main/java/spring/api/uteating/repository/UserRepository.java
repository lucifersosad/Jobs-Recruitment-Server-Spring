package spring.api.uteating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.api.uteating.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email = :email")
    User getUserByEmail(@Param("email") String email);


}
