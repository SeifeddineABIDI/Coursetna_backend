package tn.esprit.pidev.controller.googledrive;

import tn.esprit.pidev.dtos.GoogleDriveFileDTO;
import tn.esprit.pidev.entities.ressources.Categorie;
import tn.esprit.pidev.entities.ressources.Options;
import tn.esprit.pidev.entities.ressources.Ressource;
import tn.esprit.pidev.services.googledrive.GoogleDriveFileService;
import tn.esprit.pidev.services.ressources.IGestionRessource;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@Tag(name = "Files", description = "Files API")
@CrossOrigin
public class GoogleDriveFileController {

    private final GoogleDriveFileService googleDriveFileService;
    @Autowired
    IGestionRessource ressourceService;
    @GetMapping
    public ResponseEntity<List<GoogleDriveFileDTO>> findAll() {
        return ResponseEntity.ok(googleDriveFileService.findAll());
    }

    @GetMapping("/{folderId}")
    public ResponseEntity<List<GoogleDriveFileDTO>> findAllInFolder(@PathVariable String folderId) {
        return ResponseEntity.ok(googleDriveFileService.findAllInFolder(folderId));
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<Map<String, String>> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("path") String path,
            @RequestParam("description") String description,
            @RequestParam("categorie") String categorie,
            @RequestParam("userId") Long userId,
            @RequestParam("topicName") String topicName,
            @RequestParam("options") String options) throws IOException {

        path = "".equals(path) ? "Root" : path;
        Ressource ressource = new Ressource();
        ressource.setTitre(file.getOriginalFilename());
        ressource.setDescription(description);
        ressource.setCategorie(Categorie.valueOf(categorie));
        ressource.setOptions(Options.valueOf(options));
        ressourceService.addRessource(file, ressource, userId, topicName);

        Map<String, String> response = new HashMap<>();
        response.put("message", "File uploaded successfully");
        response.put("googleDriveResponse", googleDriveFileService.upload(file, path, false));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{fileId}/copy/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public void copy(@PathVariable String fileId, @PathVariable String folderId) {
        googleDriveFileService.copyToFolder(fileId, folderId);
    }

    @GetMapping("/{fileId}/move/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public void move(@PathVariable String fileId, @PathVariable String folderId) {
        googleDriveFileService.moveToFolder(fileId, folderId);
    }

    @PostMapping("/{fileId}/permission/{gmail}")
    @ResponseStatus(HttpStatus.OK)
    public void permission(@PathVariable String fileId, @PathVariable String gmail) {
        googleDriveFileService.shareFile(fileId, gmail);
    }
}
