package tn.esprit.pidev.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tn.esprit.pidev.dto.SubforumDto;
import tn.esprit.pidev.entities.Subforum;
import tn.esprit.pidev.entities.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-05T16:01:18+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class SubforumMapperImpl implements SubforumMapper {

    @Override
    public SubforumDto mapSubforumToDto(Subforum subforum) {
        if ( subforum == null ) {
            return null;
        }

        SubforumDto.SubforumDtoBuilder subforumDto = SubforumDto.builder();

        subforumDto.id( subforum.getId() );
        subforumDto.name( subforum.getName() );
        subforumDto.description( subforum.getDescription() );

        subforumDto.numberOfPosts( mapPosts(subforum.getPosts()) );

        return subforumDto.build();
    }

    @Override
    public Subforum mapDtoToSubforum(SubforumDto subforumDto, User user) {
        if ( subforumDto == null && user == null ) {
            return null;
        }

        Subforum subforum = new Subforum();

        if ( subforumDto != null ) {
            subforum.setName( subforumDto.getName() );
            subforum.setDescription( subforumDto.getDescription() );
        }
        subforum.setUser( user );
        subforum.setCreatedDate( java.time.Instant.now() );

        return subforum;
    }
}
