package tn.esprit.pidev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Discussion;
import tn.esprit.pidev.entities.Message;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.services.IGestionDiscussion;
import tn.esprit.pidev.services.IGestionUser;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/discussion")
public class DiscussionController {

    @Autowired
    IGestionDiscussion iGestionDiscussion ;

    @Autowired
    IGestionUser iGestionUser ;

    @PostMapping("/startDiscussionDuo")
    public Discussion cstartDiscussionDuo(@RequestParam int userStart,@RequestParam int userEnd) {
        return iGestionDiscussion.startDiscussionDuo(userStart,userEnd);
    }

    @PostMapping("/startDiscussionGroup")
    public Discussion cstartDiscussionGroup(@RequestParam int userStart, @RequestParam String title, @RequestParam String userList, @RequestBody String image) {
        return iGestionDiscussion.startDiscussionGroup(userStart,title,userList,image);
    }

    @PostMapping("/startDiscussionCommunity")
    public Discussion cstartDiscussionCommunity(@RequestParam int userStart,@RequestParam String title, @RequestParam String userList,@RequestParam String discussionList, @RequestBody String image) {
        return iGestionDiscussion.startDiscussionCommunity(userStart,title,userList,discussionList,image);
    }

    @PutMapping("/modifyDiscussionGroup")
    public ResponseEntity<String> cmodifyDiscussionGroup(@RequestParam Long discussion, @RequestParam int userStart, @RequestParam String title, @RequestParam String userList, @RequestParam int admin, @RequestBody String image) {
        return iGestionDiscussion.modifyDiscussionGroup(discussion,userStart,title,userList,admin,image);
    }

    @PutMapping("/modifyDiscussionCommunity")
    public ResponseEntity<String> cmodifyDiscussionCommunity(@RequestParam long discussion, @RequestParam int userStart, @RequestParam String title, @RequestParam String userList, @RequestParam String discussionList, @RequestParam int admin, @RequestBody String image) {
        return iGestionDiscussion.modifyDiscussionCommunity(discussion,userStart,title,userList,discussionList,admin,image);
    }

    @PutMapping("/addAdminsToDiscussion")
    public ResponseEntity<String> caddAdminsToDiscussion(@RequestParam Long discussion, @RequestParam int admin, @RequestParam String userList) {
        return iGestionDiscussion.addAdminsToDiscussion(discussion,admin,userList);
    }

    @GetMapping("/retrieveAllDiscussions")
    public List<Discussion> cretrieveAllDiscussions(@RequestParam int id) {
        return iGestionDiscussion.retrieveAllDiscussions(id);
    }

    @GetMapping("/retrieveAllCommunities")
    public List<Discussion> cretrieveAllCommunities(@RequestParam int id) {
        return iGestionDiscussion.retrieveAllCommunities(id);
    }

    @PutMapping("/leaveDiscussion")
    public Discussion cleaveDiscussion(@RequestParam int user, @RequestParam Long discussion) {
        return iGestionDiscussion.leaveDiscussion(user,discussion);
    }
    @PutMapping("/deleteDiscussion")
    public ResponseEntity<String> cdeleteDiscussion(@RequestParam int user, @RequestParam Long discussion) {
        return iGestionDiscussion.deleteDiscussion(user,discussion);
    }

    @GetMapping("/retrieveUsersForDiscussion")
    public List<User> cretrieveUsersForDiscussion(@RequestParam String prenom, @RequestParam String nom) {
        return iGestionUser.findByNomOrPrenom(prenom,nom);
    }

}