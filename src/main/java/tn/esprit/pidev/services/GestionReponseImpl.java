package tn.esprit.pidev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.Reclamtion;
import tn.esprit.pidev.entities.Reponse;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.repository.IReclamationRepository;
import tn.esprit.pidev.repository.IReponseRepository;
import tn.esprit.pidev.repository.IUserRepository;

import java.util.List;

@Service
public class GestionReponseImpl implements IGestionReponse{
    @Autowired
    IReponseRepository iReponseRepository;
    @Autowired
    IReclamationRepository iReclamationRepository;
    IUserRepository iUserRepository;


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


    @Override
    public Reponse addReponseAndAssignToReclamtion(Reponse reponse, long idrep) {
        Reclamtion reclamtion=iReclamationRepository.findById(idrep).get();
        reponse.setReclamtion(reclamtion);
        return iReponseRepository.save(reponse);
    }


}
