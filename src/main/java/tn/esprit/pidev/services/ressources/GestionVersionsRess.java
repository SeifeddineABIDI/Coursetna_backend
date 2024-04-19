package tn.esprit.pidev.services.ressources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.ressources.VersionRessource;
import tn.esprit.pidev.repository.ressources.IVersionRessRepository;

import java.util.List;

@Service
public class GestionVersionsRess implements IGestionVersionRess {

    @Autowired
    private IVersionRessRepository versionRessourceRepository;

    @Override
    public VersionRessource creerNouvelleVersion(VersionRessource version) {
        return versionRessourceRepository.save(version);
    }

    @Override
    public VersionRessource recupererVersion(Long id) {
        return versionRessourceRepository.findById(id).orElse(null);
    }

    @Override
    public List<VersionRessource> recupererToutesLesVersions() {
        return versionRessourceRepository.findAll();
    }

    @Override
    public void supprimerVersion(Long id) {
        versionRessourceRepository.deleteById(id);
    }


}

