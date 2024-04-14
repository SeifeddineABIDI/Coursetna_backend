package tn.esprit.pidev.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.dto.PostRequest;
import tn.esprit.pidev.dto.PostResponse;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.services.IGestionPost;

import java.util.List;

import static org.springframework.web.servlet.function.ServerResponse.status;

@RestController
@RequestMapping("/posts")

public class PostController {

    @Autowired
    IGestionPost gestionPost;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        gestionPost.create(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = gestionPost.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        PostResponse post = gestionPost.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("by-subforum/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubforum(@PathVariable Long id) {
        List<PostResponse> posts = gestionPost.getPostsBySubforum(id);
        return ResponseEntity.ok(posts);
    }

}
