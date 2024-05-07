package tn.esprit.pidev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.entities.VersionRessource;
import tn.esprit.pidev.entities.VersionRessource;
import tn.esprit.pidev.repository.IVersionRessRepository;
import tn.esprit.pidev.services.IGestionVersionRess;
import tn.esprit.pidev.services.IGestionVersionRess;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/version")
@CrossOrigin
public class ControllerVersionRess {
    @Autowired
    private IGestionVersionRess versionservice;
    @Autowired
    private IVersionRessRepository versionRessRepository;

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

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> telechargerFichier(@PathVariable Long id) throws IOException {
        Optional<VersionRessource> optionalRessource = versionRessRepository.findById(id);
        if (!optionalRessource.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        VersionRessource versionRessource = optionalRessource.get();
        String filePath = versionRessource.getCheminFichier();
        Path path = Paths.get(filePath);
        Resource fileResource = new FileSystemResource(path.toFile());

        if (fileResource.exists() && fileResource.isReadable()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"");
            return new ResponseEntity<>(fileResource, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{versionId}")
    public ResponseEntity<?> deleteVersion(@PathVariable Long versionId) {
        versionservice.deleteVersion(versionId);
        return ResponseEntity.ok().build();
    }
}
