package tn.esprit.pidev.services;


import tn.esprit.pidev.entities.Stage;

import java.util.List;

public interface StageService {
    List<Stage> getAllStages();
    Stage getStageById(int id);
    void addStage(Stage stage);
    void updateStage(int id, Stage stage);
    void deleteStage(int id);

}
