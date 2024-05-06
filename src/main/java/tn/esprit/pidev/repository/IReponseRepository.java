package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.entities.Reponse;

public interface IReponseRepository extends JpaRepository<Reponse,Long> {
}
