package tn.esprit.pidev.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Service
public class GestionPostImpl implements IGestionPost {

    @Autowired
    IPostRepository postRepository;
    ISubforumRepository subforumRepository;
    IUserRepository userRepository;
    PostMapper postMapper;

    private static String uploadDirectory = "src/main/resources/templates";



    @Override
    public void create(PostRequest postRequest, MultipartFile file) throws IOException {
        Subforum subforum = subforumRepository.findByName(postRequest.getSubforumName())
                .orElseThrow(() -> new SubforumNotFoundException(postRequest.getSubforumName()));

        User user = userRepository.findByEmail(postRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + postRequest.getEmail()));

        Post post = postMapper.map(postRequest, subforum, user);

        postRepository.save(post); // Save the post first

        // Check if a file is provided and not empty
        if (file != null && !file.isEmpty()) {
            // Save the image and update the post with the image URL
            String imageUrl = uploadImage(file);
            post.setImageUrl(imageUrl);
            postRepository.save(post);// Update the post with the image URL
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());

    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));

        return postMapper.mapToDto(post);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubforum(Long subforumId) {
        Subforum subforum = subforumRepository.findById(subforumId).orElseThrow(() -> new SubforumNotFoundException(subforumId.toString()));
        List<Post> posts = postRepository.findAllBySubforum(subforum);

        return posts
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getMostInteractivePosts() {
        List<Object[]> mostInteractivePostsData = postRepository.findMostInteractivePosts();
        List<Post> mostInteractivePosts = mostInteractivePostsData.stream()
                .map(data -> (Post) data[0])
                .collect(Collectors.toList());
        return mostInteractivePosts.stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));
        postRepository.delete(post);
    }



    @Override
    public Collection<PostResponse> getAllPaginated(final Integer pageNumber) {
        Integer index = pageNumber - 1;
        Page<Post> posts = this.postRepository.findAll(PageRequest.of(index, 2));
        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }


    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String photoUrl = uploadDirectory + "/" + fileName;

        try {
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                return photoUrl;
            }
        } catch (IOException ex) {
            throw new IOException("Impossible de télécharger le fichier : " + fileName, ex);
        }
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
