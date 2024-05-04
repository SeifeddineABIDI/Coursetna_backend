package tn.esprit.pidev.repository.ressources;

import tn.esprit.pidev.entities.ressources.VersionRessource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IVersionRessRepository extends JpaRepository<VersionRessource,Long> {
    List<VersionRessource> findByRessourceId(Long ressourceId);
}
