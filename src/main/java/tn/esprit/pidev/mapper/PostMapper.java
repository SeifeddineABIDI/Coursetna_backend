package tn.esprit.pidev.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.pidev.dto.PostRequest;
import tn.esprit.pidev.dto.PostResponse;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.Subforum;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.repository.ICommentRepository;
import tn.esprit.pidev.repository.IVoteRepository;

import static tn.esprit.pidev.entities.VoteType.UPVOTE;


@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private IVoteRepository voteRepository;


    //Mapping from DTO to Entity (PostRequest)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subforum", source = "subforum")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, Subforum subforum, User user);

    //Mapping from Entity to DTO (PostResponse)
    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subforumName", source = "subforum.name")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

//    boolean isPostUpVoted(Post post) {
//        return checkVoteType(post, UPVOTE);
//    }

//    boolean isPostDownVoted(Post post) {
//        return checkVoteType(post, DOWNVOTE);
//    }

//    private boolean checkVoteType(Post post, VoteType voteType) {
//        if (authService.isLoggedIn()) {
//            Optional<Vote> voteForPostByUser =
//                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
//                            authService.getCurrentUser());
//            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
//                    .isPresent();
//        }
//        return false;
//    }

}