package tn.esprit.pidev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Message;
import tn.esprit.pidev.entities.Message;
import tn.esprit.pidev.services.IGestionMessage;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    IGestionMessage iGestionMessage ;

    @PostMapping("/sendMessage")
    public Message csendMessage(@RequestParam Long userSender,@RequestParam Long discussion,@RequestBody String message) {
        return iGestionMessage.sendMessage(userSender,discussion,message);
    }

    @PutMapping("/modifyMessage")
    public Message cmodifyMessage(@RequestParam Long id,@RequestBody String message) {
        return iGestionMessage.modifyMessage(id,message);
    }

    @PutMapping("/deleteMessage")
    public boolean cdeleteMessage(@RequestParam Long id) {
        return iGestionMessage.deleteMessage(id);
    }

}
