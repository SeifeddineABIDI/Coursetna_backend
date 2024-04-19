package tn.esprit.pidev.services.reclamation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.reclamation.Reclamtion;
import tn.esprit.pidev.entities.user.User;
import tn.esprit.pidev.repository.reclamation.IReclamationRepository;
import tn.esprit.pidev.repository.user.IUserRepository;

import java.util.List;

@Service
public class GestionReclamationImpl implements IGestionReclamation {
    @Autowired
    IReclamationRepository iReclamationRepository;
    @Autowired
    IUserRepository iUserRepository;
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

    @Override
    public Reclamtion addReclamtionAndAssignToUser(Reclamtion reclamtion, Integer id) {
        User user=iUserRepository.findById(id).get();
        reclamtion.setUser(user);
        return iReclamationRepository.save(reclamtion);

    }
    /*getReclamationByUserandReponse*/
    @Override
    public Reclamtion getReclamationByUserandReponse(Integer id, long idrep) {
        return iReclamationRepository.getReclamationByUserAndResponse(id,idrep);
    }

    @Override
    public List<Reclamtion> findAllReclamationsWithUserAndResponse() {
        return iReclamationRepository.findAllReclamationsWithUserAndResponse();
    }
}
