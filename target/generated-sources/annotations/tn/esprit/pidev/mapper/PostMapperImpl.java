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
    date = "2024-04-15T00:57:45+0100",
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
        postResponse.setPostName( post.getPostName() );
        postResponse.setUrl( post.getUrl() );
        postResponse.setDescription( post.getDescription() );
        postResponse.setVoteCount( post.getVoteCount() );

        postResponse.setCommentCount( commentCount(post) );
        postResponse.setDuration( getDuration(post) );

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
}
