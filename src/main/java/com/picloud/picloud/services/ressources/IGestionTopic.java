package com.picloud.picloud.services.ressources;

import com.picloud.picloud.entities.ressources.Topic;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
