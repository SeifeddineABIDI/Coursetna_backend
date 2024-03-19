package tn.esprit.pidev.services.evaluation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.evaluation.Reponse;
import tn.esprit.pidev.repository.evaluation.IReponseRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class GestionReponseImpl implements IGestionReponse {
    IReponseRepository reponseRepo;
    @Override
    public List<Reponse> retrieveAllReponses() {
        return reponseRepo.findAll();
    }

    @Override
    public Reponse retrieveReponse(Long numReponse) {
        return reponseRepo.findById(numReponse).get();
    }

    @Override
    public Reponse addReponse(Reponse reponse) {
        return reponseRepo.save(reponse);
    }

    @Override
    public Reponse updateReponse(Reponse reponse) {
        return reponseRepo.save(reponse);
    }

    @Override
    public void removeReponse(Long numReponse) {
        reponseRepo.deleteById(numReponse);
    }
}
