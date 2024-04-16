package tn.esprit.pidev.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.pidev.dto.SubforumDto;
import tn.esprit.pidev.entities.Subforum;
import tn.esprit.pidev.exceptions.SpringSubforumException;
import tn.esprit.pidev.mapper.PostMapper;
import tn.esprit.pidev.mapper.SubforumMapper;
import tn.esprit.pidev.repository.IPostRepository;
import tn.esprit.pidev.repository.ISubforumRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class GestionSubforumImpl implements IGestionSubforum {

    @Autowired
    IPostRepository postRepository;
    ISubforumRepository subforumRepository;
    PostMapper postMapper;
    SubforumMapper subforumMapper;


    @Override
    public SubforumDto save(SubforumDto subforumDto) {
        Subforum save = subforumRepository.save(subforumMapper.mapDtoToSubforum(subforumDto));
        subforumDto.setId(save.getId());
        return subforumDto;
    }

    @Override
    public List<SubforumDto> getAll() {
        return subforumRepository.findAll()
                .stream()
                .map(subforumMapper::mapSubforumToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubforumDto getSubforum(Long id) {
        Subforum subforum = subforumRepository.findById(id)
                .orElseThrow(() -> new SpringSubforumException("No subforum found with ID - " + id));
        return subforumMapper.mapSubforumToDto(subforum);
    }
}
