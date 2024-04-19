package tn.esprit.pidev.controller.stage;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.stage.Stage;
import tn.esprit.pidev.services.stage.StageService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/stage")
public class StageController {

      StageService serviceStage;

    @GetMapping
    public ResponseEntity<List<Stage>> getAllStages() {
        List<Stage> stages = serviceStage.getAllStages();
        return new ResponseEntity<>(stages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stage> getStageById(@PathVariable("id") int id) {
        Stage stage = serviceStage.getStageById(id);
        if (stage != null) {
            return new ResponseEntity<>(stage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> addStage(@RequestBody Stage stage) {
        serviceStage.addStage(stage);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStage(@PathVariable("id") int id, @RequestBody Stage stage) {
        serviceStage.updateStage(id, stage);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStage(@PathVariable("id") int id) {
        serviceStage.deleteStage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
