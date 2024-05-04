package tn.esprit.pidev.services;

import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.entities.Topic;

import java.io.IOException;
import java.util.List;

public interface IGestionTopic {
    List<Topic> getAll();

//    @Transactional
//    Topic AddTopic(Topic topic, MultipartFile photo) throws IOException;

    @Transactional
    Topic addTopic(Topic topic, MultipartFile photo) throws IOException;

    boolean existeDeja(String nom);
}
