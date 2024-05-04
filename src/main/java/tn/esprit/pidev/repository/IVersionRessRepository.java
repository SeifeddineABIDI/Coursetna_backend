package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.entities.VersionRessource;

public interface IVersionRessRepository extends JpaRepository<VersionRessource,Long> {
}
