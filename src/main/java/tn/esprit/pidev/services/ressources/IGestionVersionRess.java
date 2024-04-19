package tn.esprit.pidev.services.ressources;

import tn.esprit.pidev.entities.ressources.VersionRessource;

import java.util.List;

public interface IGestionVersionRess {
    VersionRessource creerNouvelleVersion(VersionRessource version);

    VersionRessource recupererVersion(Long id);

    List<VersionRessource> recupererToutesLesVersions();

    void supprimerVersion(Long id);
}
