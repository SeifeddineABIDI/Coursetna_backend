package tn.esprit.pidev.services.ressources;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.entities.ressources.Ressource;
import tn.esprit.pidev.entities.ressources.VersionRessource;
import tn.esprit.pidev.repository.ressources.IRessourceRepository;
import tn.esprit.pidev.repository.ressources.IVersionRessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class GestionVersionsRess implements IGestionVersionRess {

    @Autowired
    private IRessourceRepository ressourceRepository;
    @Autowired
    private IVersionRessRepository versionRessRepository;

    @Value("${upload.directory}")
    private String uploadDirectory;
    @Override
    public VersionRessource createNewVersion(Long ressourceId, String versionName, MultipartFile file) throws Exception {
        Optional<Ressource> ressourceOptional = ressourceRepository.findById(ressourceId);
        if (ressourceOptional.isPresent()) {
            Ressource ressource = ressourceOptional.get();
            VersionRessource nouvelleVersion = new VersionRessource();
            nouvelleVersion.setVersionName(versionName);
            nouvelleVersion.setCheminFichier(uploadDirectory + "/" + file.getOriginalFilename());
            nouvelleVersion.setRessource(ressource);
            versionRessRepository.save(nouvelleVersion);
            saveUploadedFile(file);

            return nouvelleVersion;
        } else {
            throw new Exception("Ressource not found with id: " + ressourceId);
        }
    }

    public void saveUploadedFile(MultipartFile file) {
        Path filePath = Paths.get(uploadDirectory, file.getOriginalFilename());
        try {
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            // Gérer l'erreur
        }
    }


    @Override
    public List<VersionRessource> getAllVersionsByRessourceId(Long ressourceId) {
        return versionRessRepository.findByRessourceId(ressourceId);
    }

    @Override
    public void deleteVersion(Long versionId) {
        versionRessRepository.deleteById(versionId);
    }


}

