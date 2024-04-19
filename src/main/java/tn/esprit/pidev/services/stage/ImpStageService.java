package tn.esprit.pidev.services.stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.stage.Stage;
import tn.esprit.pidev.repository.stage.StageReposetory;

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
    public void deleteStage(int id) {
        stageRepository.deleteById(id);
    }
}
