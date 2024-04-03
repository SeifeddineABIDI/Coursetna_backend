package tn.esprit.pidev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Message;
import tn.esprit.pidev.entities.Reaction;
import tn.esprit.pidev.services.IGestionReaction;

@RestController
@RequestMapping("/reaction")
public class ReactionController {
    @Autowired
    IGestionReaction iGestionReaction ;

    @PostMapping("/addReaction")
    public Reaction caddReaction(@RequestParam Long user, @RequestParam Long message, @RequestBody String reaction) {
        return iGestionReaction.addReaction(user,message,reaction);
    }

    @PutMapping("/deleteReaction")
    public boolean cdeleteReaction(@RequestParam Long id) {
        return iGestionReaction.deleteReaction(id);
    }

}
