package tn.esprit.pidev.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pidev.dto.VoteDto;
import tn.esprit.pidev.services.IGestionUser;
import tn.esprit.pidev.services.IGestionVote;

@RestController
@RequestMapping("/votes")

public class VoteController {

    @Autowired
    IGestionVote iGestionVote;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto) {
        String userEmail = "";
        iGestionVote.vote(voteDto,userEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}