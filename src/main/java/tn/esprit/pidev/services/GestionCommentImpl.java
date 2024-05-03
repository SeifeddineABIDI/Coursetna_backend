package tn.esprit.pidev.services;

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
import java.net.URL;
import java.util.List;
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
    private static final String PROFANITY_API_BASE_URL = "https://api.api-ninjas.com/v1/profanityfilter";
    private static final String API_KEY = "bkFu4qUELNjMk2blZVqJaw==HNFUUE6wOQD0olt5";


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
            throw new PostNotFoundException( "Your comment contains profanity. Please remove it and try again.");
        }
    }

    private boolean checkProfanity(String text) {
        try {
            URL url = new URL(PROFANITY_API_BASE_URL + "?text=" + text);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("X-Api-Key", API_KEY);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream responseStream = connection.getInputStream();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(responseStream);
                return root.path("containsProfanity").asBoolean();
            } else {
                // Handle HTTP error response
                System.err.println("HTTP Error: " + responseCode);
                // Optionally, read and print the error response body
                InputStream errorStream = connection.getErrorStream();
                if (errorStream != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode errorRoot = mapper.readTree(errorStream);
                    System.err.println("Error Response: " + errorRoot.toString());
                }
                return false;
            }
        } catch (IOException e) {
            // Handle exception
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
