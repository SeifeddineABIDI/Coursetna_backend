package tn.esprit.pidev.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.dto.CommentsDto;
import tn.esprit.pidev.entities.Comment;
import tn.esprit.pidev.entities.NotificationEmail;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.exceptions.PostNotFoundException;
import tn.esprit.pidev.exceptions.UserNotFoundException;
import tn.esprit.pidev.mapper.CommentMapper;
import tn.esprit.pidev.repository.ICommentRepository;
import tn.esprit.pidev.repository.IPostRepository;
import tn.esprit.pidev.repository.IUserRepository;

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

    @Override
    public void create(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId()).orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));

        User user = userRepository.findByEmail(commentsDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + commentsDto.getEmail()));

        Comment comment = commentMapper.map(commentsDto, post, user);

        commentRepository.save(comment);
        String message = mailContentBuilder.build(post.getUser().getNom() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }
    private void sendCommentNotification(String message, User user) {
        String fullName = user.getPrenom() + " " + user.getNom();
        mailService.sendMail(new NotificationEmail(fullName + " Commented on your post", user.getEmail(), message));
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
