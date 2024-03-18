package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.entities.User;

public interface IUserRepository extends JpaRepository<User, Long> {
}
