package tn.esprit.pidev.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tn.esprit.pidev.dto.CommentsDto;
import tn.esprit.pidev.entities.Comment;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-15T00:57:45+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment map(CommentsDto commentsDto, Post post, User user) {
        if ( commentsDto == null && post == null && user == null ) {
            return null;
        }

        Comment comment = new Comment();

        if ( commentsDto != null ) {
            comment.setText( commentsDto.getText() );
        }
        comment.setPost( post );
        comment.setUser( user );
        comment.setCreatedDate( java.time.Instant.now() );

        return comment;
    }

    @Override
    public CommentsDto mapToDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentsDto commentsDto = new CommentsDto();

        commentsDto.setId( comment.getId() );
        commentsDto.setCreatedDate( comment.getCreatedDate() );
        commentsDto.setText( comment.getText() );

        commentsDto.setPostId( comment.getPost().getPostId() );
        commentsDto.setEmail( comment.getUser().getEmail() );

        return commentsDto;
    }
}
