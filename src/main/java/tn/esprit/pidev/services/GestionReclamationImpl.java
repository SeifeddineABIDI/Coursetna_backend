package tn.esprit.pidev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.Reclamtion;
import tn.esprit.pidev.repository.IReclamationRepository;

import java.util.List;

@Service
public class GestionReclamationImpl implements IGestionReclamation {
    @Autowired
    IReclamationRepository iReclamationRepository;
    @Override
    public List<Reclamtion> retrieveAllReclamation() {
        return iReclamationRepository.findAll();
    }

    @Override
    public Reclamtion addReclamation(Reclamtion reclamtion) {
        return iReclamationRepository.save(reclamtion);
    }

    @Override
    public Reclamtion updateReclamtion(Reclamtion reclamtion) {
        return iReclamationRepository.save(reclamtion);
    }

    @Override
    public Reclamtion RetrieveReclamation(long idrec) {
        return iReclamationRepository.findById(idrec).get() ;
    }

    @Override
    public void deleteReclamation(long idrec) {
        iReclamationRepository.deleteById(idrec);
    }
}
