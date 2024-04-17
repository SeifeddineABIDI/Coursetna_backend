package com.picloud.picloud.repository.ressources;

import com.picloud.picloud.entities.ressources.VersionRessource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVersionRessRepository extends JpaRepository<VersionRessource,Long> {
}
