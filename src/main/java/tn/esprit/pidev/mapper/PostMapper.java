package tn.esprit.pidev.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.pidev.dto.PostRequest;
import tn.esprit.pidev.dto.PostResponse;
import tn.esprit.pidev.entities.*;
import tn.esprit.pidev.repository.ICommentRepository;
import tn.esprit.pidev.repository.IUserRepository;
import tn.esprit.pidev.repository.IVoteRepository;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private IVoteRepository voteRepository;
    @Autowired
    private IUserRepository userRepository;

    private static final ThreadLocal<String> USER_EMAIL = new ThreadLocal<>();

    public void setUserEmail(String userEmail) {
        USER_EMAIL.set(userEmail);
    }

    public void removeUserEmail() {
        USER_EMAIL.remove();
    }

    // Mapping from DTO to Entity (PostRequest)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subforum", source = "subforum")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "imageUrl", source = "postRequest.imageUrl")
    public abstract Post map(PostRequest postRequest, Subforum subforum, User user);

    // Mapping from Entity to DTO (PostResponse)
    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subforumName", source = "subforum.name")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "imageUrl", source = "post.imageUrl")
    @Mapping(target = "like", expression = "java(isPostLiked(post))")
    @Mapping(target = "dislike", expression = "java(isPostDisliked(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        if (post.getCreatedDate() != null) {
            return TimeAgo.using(post.getCreatedDate().toEpochMilli());
        } else {
            return ""; // Or any default value you want to return for null createdDate
        }
    }

    boolean isPostLiked(Post post) {
        return checkVoteType(post, VoteType.LIKE, USER_EMAIL.get());
    }

    boolean isPostDisliked(Post post) {
        return checkVoteType(post, VoteType.DISLIKE, USER_EMAIL.get());
    }

    private boolean checkVoteType(Post post, VoteType voteType, String userEmail) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isPresent()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, userOptional.get());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}
