package tn.esprit.pidev.controller.ressources;

import com.fasterxml.jackson.core.JsonProcessingException;
import tn.esprit.pidev.entities.ressources.*;
import tn.esprit.pidev.repository.IUserRepository;
import tn.esprit.pidev.repository.ressources.IRessourceRepository;
import tn.esprit.pidev.repository.ressources.ItopicRepository;
import tn.esprit.pidev.services.ressources.IGestionRessource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ress")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ControllerRess {
    @Autowired
    IGestionRessource ressourceService;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    IRessourceRepository ressourceRepo;
    @Autowired
    ItopicRepository topicRepository;
    @Value("${upload.directory}")
    private String uploadDirectory;


    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> telechargerFichier(@PathVariable Long id) throws IOException {
        Optional<Ressource> optionalRessource = ressourceRepo.findById(id);
        if (!optionalRessource.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Ressource ressource = optionalRessource.get();
        String filePath = ressource.getFilePath();
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
    @GetMapping("/getall")
    public List<Ressource> getAllRessource() throws JsonProcessingException {
        return  ressourceService.getAll();
    }

    @PostMapping("/upload")
    public Ressource uploadFile(@RequestParam("file") MultipartFile file,
                                @RequestParam("titre") String titre,
                                @RequestParam("description") String description,
                                @RequestParam("categorie") String categorie,
                                @RequestParam("userId") Long userId,
                                @RequestParam("topicName") String topicName,
                                @RequestParam("options")String options) throws IOException {
        Ressource ressource = new Ressource();
        ressource.setTitre(titre);
        ressource.setDescription(description);
        ressource.setCategorie(Categorie.valueOf(categorie));
        ressource.setOptions(Options.valueOf(options));
        return ressourceService.addRessource(file, ressource, userId,topicName);
    }

    @GetMapping("/{option}")
    public ResponseEntity<List<Ressource>> getResourcesForOption(@PathVariable Options option) {
        List<Ressource> resources = ressourceService.getResourcesForOption(option);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/byCategorie/{categorie}/{topicId}")
    public ResponseEntity<List<Ressource>> getResourcesByCategorie(@PathVariable Categorie categorie,@PathVariable Long topicId) {
        List<Ressource> resources = ressourceService.getRessourceByCategory(categorie,topicId);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/getRessource/{id}")
    public ResponseEntity<Ressource> getRessourceById(@PathVariable("id") long id) {
        Ressource ressource = ressourceService.getById(id);
        if (ressource != null) {
            return new ResponseEntity<>(ressource, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/removeRessource/{id}")
    public String removeById(@PathVariable("id") long id) {
        ressourceService.removeRessource(id);
        return "Ressource avec l'ID " + id + " supprimé avec succès";
    }

    @PutMapping(value = "/update/{ressourceId}", consumes = "multipart/form-data")
    public ResponseEntity<Ressource> updateRessource(@PathVariable("ressourceId") Long ressourceId,
                                                     @ModelAttribute Ressource updatedRessource,
                                                     @RequestParam(value = "file", required = false) MultipartFile file) {
        {
            try {
                Ressource updatedR = ressourceService.updateRessource(ressourceId, updatedRessource, file);
                return ResponseEntity.ok().body(updatedR);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @GetMapping("/filterByTitre/{titre}")
    public ResponseEntity<List<Ressource>> filterRessourcesByTitre(@PathVariable("titre")String titre) {
        List<Ressource> ressources = ressourceService.filterRessourcesByTitre(titre);
        return ResponseEntity.ok(ressources);
    }

    @PutMapping("/{ressourceId}/updateRating")
    public ResponseEntity<String> updateRatingForRessource(@PathVariable Long ressourceId, @RequestParam int newRating) {
        try {
            ressourceService.updateRatingForRessource(ressourceId, newRating);
            return ResponseEntity.ok("Rating updated successfully for ressource with ID: " + ressourceId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update rating: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> rateRessource(@PathVariable Long id, @RequestBody Map<String, Integer> requestBody) {
        int rating = requestBody.get("rating");
        try {
            ressourceService.rateRessource(id, rating);
            return ResponseEntity.ok("Ressource rated successfully with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to rate ressource: " + e.getMessage());
        }
    }
    @PostMapping("/{userId}/resources/{resourceId}/rate")
    public ResponseEntity<Map<String, String>> addRating(@PathVariable Long userId, @PathVariable Long resourceId, @RequestBody Map<String, Integer> requestBody) {
        int rating = requestBody.get("rating");
        ressourceService.addRating(userId, resourceId, rating);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Rating added successfully to resource with ID: " + resourceId + " for user with ID: " + userId);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/ressources-by-topic")
    public ResponseEntity<List<Ressource>> getRessourcesByTopic(@RequestParam("topicId") Long topicId) {
        Optional<Topic> topicOptional = topicRepository.findById(topicId);
        if (topicOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Topic topic = topicOptional.get();
        List<Ressource> ressources = ressourceRepo.findByTopic(topic);
        return ResponseEntity.ok(ressources);
    }


    @GetMapping("/topics-by-option")
    public List<Topic> getTopicsByOption(@RequestParam("option") Options option) {
        List<Ressource> ressources = ressourceRepo.findByOptions(option);
        List<Topic> topics = ressources.stream()
                .map(Ressource::getTopic)
                .distinct()
                .collect(Collectors.toList());
        return topics;
    }

    @PutMapping("/{id}/archiver")
    public ResponseEntity<Map<String, String>> archiverRessource(@PathVariable Long id) {
        try {
            ressourceService.archiverRessource(id);
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "Ressource archivée avec succès.");
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Une erreur s'est produite lors de l'archivage de la ressource."));
        }
    }

    @PutMapping("/{id}/désarchiver")
    public ResponseEntity<Map<String, String>>désarchiverRessource(@PathVariable Long id) throws Exception {
        try {
            ressourceService.désarchiverRessource(id);
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "Ressource desarchivée avec succès.");
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Une erreur s'est produite lors de le désarchivage de la ressource."));
        }
    }
    @GetMapping("/{topicId}/ressources/count")
    public int getResourcesCountByTopicId(@PathVariable Long topicId) {
        return ressourceService.countResourcesByTopicId(topicId);
    }

    @GetMapping("/user/{userId}")
    public List<Ressource> getResourcesByUserId(@PathVariable Long userId) {
        return ressourceService.getResourcesByUserId(userId);
    }
    @GetMapping("/latestResourceId")
    public ResponseEntity<Map<String, Long>> getLatestResourceId() {
        Ressource latestResource = ressourceRepo.findTopByOrderByIdDesc();

        if (latestResource != null) {
            Long latestResourceId = latestResource.getId();
            Map<String, Long> response = new HashMap<>();
            response.put("id", latestResourceId);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/countRessources/{userId}")
    public String countRessourcesByUserId(@PathVariable Long userId) {
        int count = ressourceService.countRessourcesByUserId(userId);
        return "Le nombre de ressources pour l'utilisateur avec l'ID " + userId + " est : " + count;
    }
    @GetMapping("/{ressourceId}/versions")
    public List<VersionRessource> getVersionsByRessource(@PathVariable Long ressourceId) {
        return ressourceService.getVersionsByRessource(ressourceId);
    }






}


