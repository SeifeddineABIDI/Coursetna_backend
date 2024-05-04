package tn.esprit.pidev.controller.ressources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.entities.ressources.VersionRessource;
import tn.esprit.pidev.services.ressources.IGestionVersionRess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/version")
public class ControllerVersionRess {
  @Autowired
    private IGestionVersionRess versionservice;

    @PostMapping("/create")
    public ResponseEntity<VersionRessource> createNewVersion(@RequestParam Long ressourceId,
                                                             @RequestParam String versionName,
                                                             @RequestParam("file") MultipartFile cheminFichier) throws Exception {
        VersionRessource nouvelleVersion = versionservice.createNewVersion(ressourceId, versionName, cheminFichier);
        return ResponseEntity.ok(nouvelleVersion);
    }

    @GetMapping("/all")
    public ResponseEntity<List<VersionRessource>> getAllVersionsByRessourceId(@RequestParam Long ressourceId) {
        List<VersionRessource> versions = versionservice.getAllVersionsByRessourceId(ressourceId);
        return ResponseEntity.ok(versions);
    }

    @DeleteMapping("/delete/{versionId}")
    public ResponseEntity<?> deleteVersion(@PathVariable Long versionId) {
        versionservice.deleteVersion(versionId);
        return ResponseEntity.ok().build();
    }
}
