package tn.esprit.pidev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Discussion;
import tn.esprit.pidev.entities.Message;
import tn.esprit.pidev.services.IGestionDiscussion;

import java.util.List;

@RestController
@RequestMapping("/discussion")
public class DiscussionController {
    @Autowired
    IGestionDiscussion iGestionDiscussion ;

    @PostMapping("/startDiscussionDuo")
    public Discussion cstartDiscussionDuo(@RequestParam Long userStart,@RequestParam Long userEnd) {
        return iGestionDiscussion.startDiscussionDuo(userStart,userEnd);
    }

    @PostMapping("/startDiscussionGroup")
    public Discussion cstartDiscussionGroup(@RequestParam Long userStart,@RequestParam String title, @RequestBody List<Long> userList) {
        return iGestionDiscussion.startDiscussionGroup(userStart,title,userList);
    }

    @PostMapping("/startDiscussionCommunity")
    public Discussion cstartDiscussionCommunity(@RequestParam Long userStart,@RequestParam String title, @RequestBody List<Long> userList) {
        return iGestionDiscussion.startDiscussionCommunity(userStart,title,userList);
    }

    @PutMapping("/addUserToDiscussion")
    public Discussion caddUserToDiscussion(@RequestParam Long id, @RequestBody List<Long> userList) {
        return iGestionDiscussion.addUserToDiscussion(id,userList);
    }

    @GetMapping("/retrieveAllMessages")
    public List<Message> cretrieveAllMessages(@RequestParam Long id) {
        return iGestionDiscussion.retrieveAllMessages(id);
    }

    @PutMapping("/renameDiscussion")
    public Discussion crenameDiscussion(@RequestParam Long id,@RequestParam String title) {
        return iGestionDiscussion.renameDiscussion(id,title);
    }

    @PutMapping("/deleteDiscussion")
    public boolean cdeleteDiscussion(@RequestParam Long id) {
        return iGestionDiscussion.deleteDiscussion(id);
    }
}
