package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.entities.Reaction;

public interface IReactionRepository extends JpaRepository<Reaction, Long> {
}
