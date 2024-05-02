package tn.esprit.pidev.repository.ressources;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.entities.ressources.VersionRessource;

public interface IVersionRessRepository extends JpaRepository<VersionRessource,Long> {
}
