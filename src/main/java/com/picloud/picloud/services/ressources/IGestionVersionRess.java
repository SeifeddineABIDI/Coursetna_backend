package com.picloud.picloud.services.ressources;

import com.picloud.picloud.entities.ressources.VersionRessource;

import java.util.List;

public interface IGestionVersionRess {
    VersionRessource creerNouvelleVersion(VersionRessource version);

    VersionRessource recupererVersion(Long id);

    List<VersionRessource> recupererToutesLesVersions();

    void supprimerVersion(Long id);
}
