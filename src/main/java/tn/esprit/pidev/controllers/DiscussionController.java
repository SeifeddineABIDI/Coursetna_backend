package tn.esprit.pidev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Discussion;
import tn.esprit.pidev.entities.Message;
import tn.esprit.pidev.services.IGestionDiscussion;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
    public Discussion cstartDiscussionCommunity(@RequestParam Long userStart,@RequestParam String title, @RequestBody List<Long> userList,@RequestParam String discussionList) {
        return iGestionDiscussion.startDiscussionCommunity(userStart,title,userList,discussionList);
    }

    @PutMapping("/addUserToDiscussion")
    public Discussion caddUserToDiscussion(@RequestParam Long id, @RequestBody List<Long> userList) {
        return iGestionDiscussion.addUserToDiscussion(id,userList);
    }

    @PutMapping("/addDiscussionToCommunity")
    public Discussion caddDiscussionToCommunity(@RequestParam Long id, @RequestParam String discussionList) {
        return iGestionDiscussion.addDiscussionToCommunity(id,discussionList);
    }

    @GetMapping("/retrieveAllDiscussions")
    public List<Discussion> cretrieveAllDiscussions(@RequestParam Long id) {
        return iGestionDiscussion.retrieveAllDiscussions(id);
    }

    @GetMapping("/retrieveAllCommunities")
    public List<Discussion> cretrieveAllCommunities(@RequestParam Long id) {
        return iGestionDiscussion.retrieveAllCommunities(id);
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
