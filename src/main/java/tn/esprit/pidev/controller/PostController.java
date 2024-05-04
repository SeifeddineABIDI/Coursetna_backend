package tn.esprit.pidev.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.dto.PostRequest;
import tn.esprit.pidev.dto.PostResponse;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.services.IGestionPost;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import static org.springframework.web.servlet.function.ServerResponse.status;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")

public class PostController {

    @Autowired
    IGestionPost gestionPost;

    /*@PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        gestionPost.create(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }*/
    @PostMapping
    public ResponseEntity<Void> createPost(@ModelAttribute PostRequest postRequest,
                                           @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            gestionPost.create(postRequest, file);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            // Handle IO exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@GetMapping
    public ResponseEntity<Collection<PostResponse>> getAllPosts(@RequestParam(value = "page", defaultValue = "1") Integer pageNumber) {
        Collection<PostResponse> paginatedPosts = gestionPost.getAllPaginated(pageNumber);
        return ResponseEntity.ok(paginatedPosts);
    }*/

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        gestionPost.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/most-interactive")
    public ResponseEntity<List<PostResponse>> getMostInteractivePosts() {
        List<PostResponse> mostInteractivePosts = gestionPost.getMostInteractivePosts();
        return ResponseEntity.ok(mostInteractivePosts);
    }

    @GetMapping("/{postId}/image")
    public ResponseEntity<byte[]> getPostImage(@PathVariable Long postId) {
        // Retrieve post by ID
        PostResponse post = gestionPost.getPostById(postId);
        if (post == null || post.getImageUrl() == null) {
            return ResponseEntity.notFound().build();
        }

        // Read image data from the file system using the image URL stored in the database
        Path imagePath = Paths.get(post.getImageUrl());
        byte[] imageData;
        try {
            imageData = Files.readAllBytes(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Set content type header based on image type (adjust accordingly if not JPEG)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        // Return image data as response
        return ResponseEntity.ok()
                .headers(headers)
                .body(imageData);
    }


}
