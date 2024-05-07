package tn.esprit.pidev.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tn.esprit.pidev.dto.PostRequest;
import tn.esprit.pidev.dto.PostResponse;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.Subforum;
import tn.esprit.pidev.entities.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-06T13:02:17+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class PostMapperImpl extends PostMapper {

    @Override
    public Post map(PostRequest postRequest, Subforum subforum, User user) {
        if ( postRequest == null && subforum == null && user == null ) {
            return null;
        }

        Post.PostBuilder post = Post.builder();

        if ( postRequest != null ) {
            post.description( postRequest.getDescription() );
            post.imageUrl( postRequest.getImageUrl() );
            post.postId( postRequest.getPostId() );
            post.postName( postRequest.getPostName() );
            post.url( postRequest.getUrl() );
        }
        post.subforum( subforum );
        post.user( user );
        post.createdDate( java.time.Instant.now() );
        post.voteCount( 0 );

        return post.build();
    }

    @Override
    public PostResponse mapToDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostResponse postResponse = new PostResponse();

        postResponse.setId( post.getPostId() );
        postResponse.setSubforumName( postSubforumName( post ) );
        postResponse.setEmail( postUserEmail( post ) );
        postResponse.setImageUrl( post.getImageUrl() );
        postResponse.setNom( postUserNom( post ) );
        postResponse.setPrenom( postUserPrenom( post ) );
        Integer id = postUserId( post );
        if ( id != null ) {
            postResponse.setUserId( id );
        }
        postResponse.setPostName( post.getPostName() );
        postResponse.setUrl( post.getUrl() );
        postResponse.setDescription( post.getDescription() );
        postResponse.setVoteCount( post.getVoteCount() );

        postResponse.setCommentCount( commentCount(post) );
        postResponse.setDuration( getDuration(post) );
        postResponse.setLike( isPostLiked(post) );
        postResponse.setDislike( isPostDisliked(post) );

        return postResponse;
    }

    private String postSubforumName(Post post) {
        if ( post == null ) {
            return null;
        }
        Subforum subforum = post.getSubforum();
        if ( subforum == null ) {
            return null;
        }
        String name = subforum.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String postUserEmail(Post post) {
        if ( post == null ) {
            return null;
        }
        User user = post.getUser();
        if ( user == null ) {
            return null;
        }
        String email = user.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private String postUserNom(Post post) {
        if ( post == null ) {
            return null;
        }
        User user = post.getUser();
        if ( user == null ) {
            return null;
        }
        String nom = user.getNom();
        if ( nom == null ) {
            return null;
        }
        return nom;
    }

    private String postUserPrenom(Post post) {
        if ( post == null ) {
            return null;
        }
        User user = post.getUser();
        if ( user == null ) {
            return null;
        }
        String prenom = user.getPrenom();
        if ( prenom == null ) {
            return null;
        }
        return prenom;
    }

    private Integer postUserId(Post post) {
        if ( post == null ) {
            return null;
        }
        User user = post.getUser();
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
