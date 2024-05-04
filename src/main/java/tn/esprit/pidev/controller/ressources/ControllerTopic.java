package tn.esprit.pidev.controller.ressources;

import tn.esprit.pidev.entities.ressources.Topic;
import tn.esprit.pidev.repository.ressources.IRessourceRepository;
import tn.esprit.pidev.repository.ressources.ItopicRepository;
import tn.esprit.pidev.services.ressources.IGestionTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@CrossOrigin
@RequestMapping("/topic")
public class ControllerTopic {
    @Autowired
    private IGestionTopic gestionTopic;

    @Autowired
    private ItopicRepository topicRepository;


    @Autowired
    private IRessourceRepository ressourceRepo;

    @GetMapping("/getAll")
    public List<Topic> getall() {
        return gestionTopic.getAll();
    }

    @GetMapping("/verifierNomDeSujet/{nom}")
    public ResponseEntity<Boolean> verifierNomDeSujet(@PathVariable String nom) {
        boolean existeDeja = gestionTopic.existeDeja(nom);
        return ResponseEntity.ok(existeDeja);
    }

    @PostMapping("/addTopic")
    public ResponseEntity<Topic> addTopic(@RequestParam("photo") MultipartFile photo,
                                          @RequestParam("nom") String nom,
                                          @RequestParam("ContenuTopic") String contenuTopic) {
        if (gestionTopic.existeDeja(nom)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            Topic topic = new Topic();
            topic.setNom(nom);
            topic.setContenuTopic(contenuTopic);
            Topic savedTopic = gestionTopic.addTopic(topic, photo);
            return ResponseEntity.ok().body(savedTopic);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/names")
    public List<String> getAllTopicNames() {
        return topicRepository.findAllNames();
    }



}
