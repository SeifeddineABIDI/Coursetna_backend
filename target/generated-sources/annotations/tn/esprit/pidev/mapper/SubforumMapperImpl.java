package tn.esprit.pidev.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tn.esprit.pidev.dto.SubforumDto;
import tn.esprit.pidev.entities.Subforum;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-15T00:57:45+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
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
    public Subforum mapDtoToSubforum(SubforumDto subredditDto) {
        if ( subredditDto == null ) {
            return null;
        }

        Subforum subforum = new Subforum();

        subforum.setId( subredditDto.getId() );
        subforum.setName( subredditDto.getName() );
        subforum.setDescription( subredditDto.getDescription() );

        return subforum;
    }
}
