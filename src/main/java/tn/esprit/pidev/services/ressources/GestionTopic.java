package tn.esprit.pidev.services.ressources;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.entities.ressources.Topic;
import tn.esprit.pidev.repository.ressources.ItopicRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;



@Service
public class GestionTopic implements IGestionTopic {
    @Autowired
    ItopicRepository topicRepo;

    @Value("${upload.directory}")
    private String uploadDirectory;


    @Override
    public List<Topic> getAll() {
        return topicRepo.findAll();
    }
    @Override
    @Transactional
    public Topic addTopic(Topic topic, MultipartFile photo) throws IOException {
        if (photo == null || photo.isEmpty()) {
            throw new IllegalArgumentException("La photo est vide ou absente.");
        }
        String photoUrl = savePhoto(photo);
        topic.setPhoto(photoUrl);
        Topic savedTopic = topicRepo.save(topic);


        return savedTopic;
    }

    public String savePhoto(MultipartFile photo) throws IOException {
        String fileName = StringUtils.cleanPath(photo.getOriginalFilename());
        String photoUrl = uploadDirectory + "/" + fileName;

        try {
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = photo.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                return photoUrl;
            }
        } catch (IOException ex) {
            throw new IOException("Impossible de télécharger le fichier : " + fileName, ex);
        }
    }
    @Override
    public boolean existeDeja(String nom) {
        Topic topic = topicRepo.findByNom(nom);
        return topic != null;
    }



}