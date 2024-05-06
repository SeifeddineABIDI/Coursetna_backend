//package com.example.picloud.controllers;
//
//import com.example.picloud.entities.Forum;
//import com.example.picloud.services.CVServiceImp;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//
//public class ForumController {
//
//    @Autowired
//    private  CVServiceImp cvService;
//
//
//
//    @PostMapping("/forums")
//    public ResponseEntity<Forum> createForum(@RequestBody Forum forum) {
//        Forum savedForum = forumService.saveForum(forum);
//        return new ResponseEntity<>(savedForum, HttpStatus.CREATED);
//    }
//
//}
