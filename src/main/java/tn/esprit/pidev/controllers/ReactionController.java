package tn.esprit.pidev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Message;
import tn.esprit.pidev.entities.Reaction;
import tn.esprit.pidev.services.IGestionReaction;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/reaction")
public class ReactionController {
    @Autowired
    IGestionReaction iGestionReaction ;

    @PostMapping("/reacting")
    public Reaction creacting(@RequestParam int user, @RequestParam Long message, @RequestBody String reaction) {
        return iGestionReaction.reacting(user,message,reaction);
    }

}
