package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.entities.Reclamtion;

public interface IReclamationRepository extends JpaRepository<Reclamtion,Long> {
}
