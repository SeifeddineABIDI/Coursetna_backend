package tn.esprit.pidev.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tn.esprit.pidev.dto.CommentsDto;
import tn.esprit.pidev.entities.Comment;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-06T16:00:09+0100",
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

        commentsDto.setNom( commentUserNom( comment ) );
        commentsDto.setPrenom( commentUserPrenom( comment ) );
        Integer id = commentUserId( comment );
        if ( id != null ) {
            commentsDto.setUserId( id );
        }
        commentsDto.setId( comment.getId() );
        commentsDto.setText( comment.getText() );

        commentsDto.setPostId( comment.getPost().getPostId() );
        commentsDto.setEmail( comment.getUser().getEmail() );
        commentsDto.setCreatedDate( getDuration(comment) );

        return commentsDto;
    }

    private String commentUserNom(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        User user = comment.getUser();
        if ( user == null ) {
            return null;
        }
        String nom = user.getNom();
        if ( nom == null ) {
            return null;
        }
        return nom;
    }

    private String commentUserPrenom(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        User user = comment.getUser();
        if ( user == null ) {
            return null;
        }
        String prenom = user.getPrenom();
        if ( prenom == null ) {
            return null;
        }
        return prenom;
    }

    private Integer commentUserId(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        User user = comment.getUser();
        if ( user == null ) {
            return null;
        }
        Integer id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
