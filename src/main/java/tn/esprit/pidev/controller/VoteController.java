package tn.esprit.pidev.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.dto.VoteDto;
import tn.esprit.pidev.services.IGestionUser;
import tn.esprit.pidev.services.IGestionVote;

@RestController
@RequestMapping("/votes")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")

public class VoteController {

    @Autowired
    IGestionVote iGestionVote;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto) {
        String userEmail = voteDto.getEmail();
        iGestionVote.vote(voteDto,userEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteVote(@PathVariable Long postId, @RequestParam String email) {
        iGestionVote.deleteVote(postId, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}