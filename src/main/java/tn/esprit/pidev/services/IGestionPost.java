package tn.esprit.pidev.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.dto.PostRequest;
import tn.esprit.pidev.dto.PostResponse;
import tn.esprit.pidev.entities.Post;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface IGestionPost {
    void create(PostRequest postRequest, MultipartFile photo) throws IOException;
    List<PostResponse> getAllPosts();
    PostResponse getPostById(Long id);
    List<PostResponse> getPostsBySubforum(Long subforumId);
    void deletePost(Long postId);
    List<PostResponse> getMostInteractivePosts();
    Collection<PostResponse> getAllPaginated(final Integer pageNumber);
    //List<PostResponse> getPostsByUsername(String username);

    //void removePost(Long postId);

}
