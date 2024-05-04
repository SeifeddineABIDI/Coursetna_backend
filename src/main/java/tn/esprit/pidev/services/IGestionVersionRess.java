package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.VersionRessource;

import java.util.List;

public interface IGestionVersionRess {
    VersionRessource creerNouvelleVersion(VersionRessource version);

    VersionRessource recupererVersion(Long id);

    List<VersionRessource> recupererToutesLesVersions();

    void supprimerVersion(Long id);
}
