package tn.esprit.pidev.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessageRemovedException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.dto.CommentsDto;
import tn.esprit.pidev.entities.Comment;
import tn.esprit.pidev.entities.NotificationEmail;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.exceptions.PostNotFoundException;
import tn.esprit.pidev.exceptions.SubforumNotFoundException;
import tn.esprit.pidev.exceptions.UserNotFoundException;
import tn.esprit.pidev.mapper.CommentMapper;
import tn.esprit.pidev.repository.ICommentRepository;
import tn.esprit.pidev.repository.IPostRepository;
import tn.esprit.pidev.repository.IUserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GestionCommentImpl implements IGestionComment {
    @Autowired
    ICommentRepository commentRepository;
    IPostRepository postRepository;
    CommentMapper commentMapper;
    IUserRepository userRepository;
    MailService mailService;
    MailContentBuilder mailContentBuilder;
    private static final String POST_URL = "";
    private static final String PROFANITY_API_BASE_URL = "https://neutrinoapi-bad-word-filter.p.rapidapi.com/bad-word-filter";
    private static final String API_KEY = "cd0fd78975msh65069d8c049fc5ep1a23f0jsn976f4f1a0cc9";



    @Override
    public void create(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId()).orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));

        User user = userRepository.findByEmail(commentsDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + commentsDto.getEmail()));

        String commentText = commentsDto.getText();

        // Check if comment contains profanity
        boolean containsProfanity = checkProfanity(commentText);

        if (!containsProfanity) {
            Comment comment = commentMapper.map(commentsDto, post, user);
            commentRepository.save(comment);
            String message = mailContentBuilder.build(post.getUser().getNom() + " posted a comment on your post." + POST_URL);
            sendCommentNotification(message, post.getUser());
        } else {
            throw new SubforumNotFoundException("Your comment contains bad words. Please remove it and try again.");        }
    }

    private boolean checkProfanity(String text) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(PROFANITY_API_BASE_URL))
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("X-RapidAPI-Key", API_KEY)
                    .header("X-RapidAPI-Host", "neutrinoapi-bad-word-filter.p.rapidapi.com")
                    .method("POST", HttpRequest.BodyPublishers.ofString("content=" + text + "&censor-character=*"))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            // Parsing JSON response to check if "is-bad" is true
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(responseBody, new TypeReference<Map<String, Object>>(){});
            Boolean isBad = (Boolean) responseMap.get("is-bad");
            return isBad != null && isBad;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }


    private void sendCommentNotification(String message, User user) {
        if (user != null) {
            String fullName = user.getPrenom() + " " + user.getNom();
            mailService.sendMail(new NotificationEmail(fullName + " Commented on your post", user.getEmail(), message));
        }
    }

    @Override
    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).toList();
    }
    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }


}
