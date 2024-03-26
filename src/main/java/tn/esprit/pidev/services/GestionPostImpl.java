package tn.esprit.pidev.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.repository.IPostRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class GestionPostImpl implements IGestionPost {

    @Autowired
    IPostRepository postRepository;

    @Override
    public List<Post> retrieveAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post retrievePost(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    @Override
    public void removePost(Long postId) {
        postRepository.deleteById(postId);
    }


}
