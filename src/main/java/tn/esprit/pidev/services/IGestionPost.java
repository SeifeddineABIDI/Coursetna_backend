package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.Post;

import java.util.List;

public interface IGestionPost {
    List<Post> retrieveAllPosts();
    Post addPost(Post post);
    Post updatePost(Post post);
    void removePost(Long postId);
    Post retrievePost(Long postId);
}
