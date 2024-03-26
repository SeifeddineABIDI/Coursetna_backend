package tn.esprit.pidev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.services.IGestionPost;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    IGestionPost gestionPost;


    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = gestionPost.retrieveAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> addPost(@RequestBody Post post) {
        Post addedPost = gestionPost.addPost(post);
        return new ResponseEntity<>(addedPost, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        Post post = gestionPost.retrievePost(postId);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody Post post) {
        post.setPostId(postId);
        Post updatedPost = gestionPost.updatePost(post);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        gestionPost.removePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
