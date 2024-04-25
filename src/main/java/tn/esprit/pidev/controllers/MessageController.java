package tn.esprit.pidev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Message;
import tn.esprit.pidev.entities.Message;
import tn.esprit.pidev.services.IGestionMessage;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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

        @PostMapping("/replyMessage")
    public Message creplyMessage(@RequestParam Long userSender,@RequestParam Long discussion,@RequestParam Long message,@RequestBody String reply) {
        return iGestionMessage.replyMessage(userSender,discussion,message,reply);
    }

    @GetMapping("/retrieveAllMessages")
    public List<Message> cretrieveAllMessages(@RequestParam Long id) {
        return iGestionMessage.retrieveAllMessages(id);
    }

    @GetMapping("/retrieve20Messages")
    public List<Message> cretrieve20Messages(@RequestParam Long id) {
        return iGestionMessage.retrieve20Messages(id);
    }

    @GetMapping("/retrieveA20Messages")
    public List<Message> cretrieveA20Messages(@RequestParam Long id,@RequestParam int pageNumber) {
        return iGestionMessage.retrieveA20Messages(id,pageNumber);
    }

    @GetMapping("/retrieveRecentMessages")
    public List<Message> cretrieveRecentMessages(@RequestParam Long id,@RequestParam String recentDate) {
        return iGestionMessage.retrieveRecentMessages(id,recentDate);
    }
    @PutMapping("/pinMessage")
    public Message cpinMessage(@RequestParam Long id) {
        return iGestionMessage.pinMessage(id);
    }

    
}
