package tn.esprit.pidev.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.pidev.dto.CommentsDto;
import tn.esprit.pidev.entities.Comment;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment map(CommentsDto commentsDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "email", expression = "java(comment.getUser().getEmail())")
    @Mapping(target = "createdDate", expression = "java(getDuration(comment))")
    CommentsDto mapToDto(Comment comment);
    default String getDuration(Comment comment) {
        if (comment.getCreatedDate() != null) {
            return TimeAgo.using(comment.getCreatedDate().toEpochMilli());
        } else {
            return ""; // Or any default value you want to return for null createdDate
        }
    }
}