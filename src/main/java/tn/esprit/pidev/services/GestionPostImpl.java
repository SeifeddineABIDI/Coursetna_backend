package tn.esprit.pidev.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.dto.PostRequest;
import tn.esprit.pidev.dto.PostResponse;
import tn.esprit.pidev.entities.CurrentUser;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.Subforum;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.exceptions.PostNotFoundException;
import tn.esprit.pidev.exceptions.SubforumNotFoundException;
import tn.esprit.pidev.exceptions.UserNotFoundException;
import tn.esprit.pidev.mapper.PostMapper;
import tn.esprit.pidev.repository.IPostRepository;
import tn.esprit.pidev.repository.ISubforumRepository;
import tn.esprit.pidev.repository.IUserRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Service
public class GestionPostImpl implements IGestionPost {

    @Autowired
    IPostRepository postRepository;
    ISubforumRepository subforumRepository;
    IUserRepository userRepository;
    PostMapper postMapper;

    @Override
    public void create(PostRequest postRequest) {
        Subforum subforum = subforumRepository.findByName(postRequest.getSubforumName())
                .orElseThrow(() -> new SubforumNotFoundException(postRequest.getSubforumName()));


        User user = userRepository.findByEmail(postRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + postRequest.getEmail()));


        Post post = postMapper.map(postRequest, subforum, user);


        postRepository.save(post);
    }


    @Override
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());

    }

    @Override
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));

        return postMapper.mapToDto(post);
    }

    @Override
    public List<PostResponse> getPostsBySubforum(Long subforumId) {
        Subforum subforum = subforumRepository.findById(subforumId).orElseThrow(() -> new SubforumNotFoundException(subforumId.toString()));
        List<Post> posts = postRepository.findAllBySubforum(subforum);

        return posts
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));
        postRepository.delete(post);
    }

    @Override
    public void updatePost(Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));
        // Update post attributes from postRequest
        post.setDescription(postRequest.getDescription());
        // Update other attributes as needed
        postRepository.save(post);
    }

   /* @Override
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
*/

}
