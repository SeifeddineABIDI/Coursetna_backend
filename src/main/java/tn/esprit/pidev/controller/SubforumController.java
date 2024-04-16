package tn.esprit.pidev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.dto.SubforumDto;
import tn.esprit.pidev.services.IGestionSubforum;

import java.util.List;

@RestController
@RequestMapping("/subforums")
public class SubforumController {

    @Autowired
    private IGestionSubforum gestionSubforum;

    @PostMapping
    public ResponseEntity<SubforumDto> createSubforum(@RequestBody SubforumDto subforumDto) {
        SubforumDto savedSubforumDto = gestionSubforum.save(subforumDto);
        return new ResponseEntity<>(savedSubforumDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SubforumDto>> getAllSubforums() {
        List<SubforumDto> subforums = gestionSubforum.getAll();
        return ResponseEntity.ok(subforums);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubforumDto> getSubforumById(@PathVariable Long id) {
        SubforumDto subforum = gestionSubforum.getSubforum(id);
        return ResponseEntity.ok(subforum);
    }

}
