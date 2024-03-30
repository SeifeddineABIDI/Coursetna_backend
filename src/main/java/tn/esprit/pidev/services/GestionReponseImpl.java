package tn.esprit.pidev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.Reponse;
import tn.esprit.pidev.repository.IReponseRepository;

import java.util.List;

@Service
public class GestionReponseImpl implements IGestionReponse{
    @Autowired
    IReponseRepository iReponseRepository;


    @Override
    public List<Reponse> retrieveAllReponses() {
        return iReponseRepository.findAll();
    }

    @Override
    public Reponse addReponse(Reponse reponse) {
        return iReponseRepository.save(reponse);
    }

    @Override
    public Reponse updateReponse(Reponse reponse) {
        return iReponseRepository.save(reponse);
    }

    @Override
    public Reponse RetrieveReponse(long idrep) {
        return iReponseRepository.findById(idrep).get();
    }

    @Override
    public void deleteReponse(long idrep) {
        iReponseRepository.deleteById(idrep);

    }
}
