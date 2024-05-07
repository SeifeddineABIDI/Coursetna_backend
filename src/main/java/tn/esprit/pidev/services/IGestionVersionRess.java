package tn.esprit.pidev.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.entities.VersionRessource;
import tn.esprit.pidev.entities.VersionRessource;

import java.util.List;

public interface IGestionVersionRess {

    VersionRessource createNewVersion(Long ressourceId, String versionName, MultipartFile file) throws Exception;

    List<VersionRessource> getAllVersionsByRessourceId(Long ressourceId);

    void deleteVersion(Long versionId);
}
