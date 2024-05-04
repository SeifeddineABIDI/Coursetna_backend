package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.entities.VersionRessource;

import java.util.List;

public interface IVersionRessRepository extends JpaRepository<VersionRessource,Long> {
    List<VersionRessource> findByRessourceId(Long ressourceId);
}
