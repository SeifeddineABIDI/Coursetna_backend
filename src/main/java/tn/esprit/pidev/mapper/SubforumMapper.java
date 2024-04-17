package tn.esprit.pidev.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.pidev.dto.SubforumDto;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.Subforum;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.repository.IPostRepository;
import tn.esprit.pidev.repository.IUserRepository;
import tn.esprit.pidev.services.IGestionPost;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubforumMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subforum.getPosts()))")
    SubforumDto mapSubforumToDto(Subforum subforum);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    public abstract Subforum mapDtoToSubforum(SubforumDto subforumDto, User user);

}