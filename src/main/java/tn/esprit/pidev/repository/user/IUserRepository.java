package tn.esprit.pidev.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.entities.user.User;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

}
