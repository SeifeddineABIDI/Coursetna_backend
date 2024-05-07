package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.Stage;
import tn.esprit.pidev.repository.StageReposetory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ImpStageService implements StageService {

    @Autowired
     StageReposetory stageRepository;




    @Override
    public List<Stage> getAllStages() {
        return stageRepository.findAll();
    }

    @Override
    public Stage getStageById(int id) {
        return stageRepository.findById(id).orElse(null);
    }

    @Override
    public void addStage(Stage stage) {
        stageRepository.save(stage);

        Date currentDate = new Date();
        stage.setPublicationDate(currentDate);


    }

    @Override
    public void updateStage(int id, Stage stage) {
        Stage existingStage = stageRepository.findById(id).orElse(null);
        if (existingStage != null) {
            stage.setOffreid(existingStage.getOffreid());
            stageRepository.save(stage);
        }
    }

    @Override
    public List<Stage> searchStageByTitre(String title) {
        return stageRepository.findByTitleContaining(title);
    }


    @Override
    public void deleteStage(int id) {
        stageRepository.deleteById(id);
    }
}
