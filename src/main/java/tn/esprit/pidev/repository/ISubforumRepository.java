package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Subforum;


import java.util.Optional;
@Repository
public interface ISubforumRepository extends JpaRepository<Subforum, Long> {
    Optional<Subforum> findByName(String subforumName);
}
