package tn.esprit.pidev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pidev.entities.Reclamtion;
import tn.esprit.pidev.repository.IReclamationRepository;
import tn.esprit.pidev.services.IGestionReclamation;

import java.util.List;

@RestController
@RequestMapping("/Reclamation")
public class ReclamationController {
    @Autowired
    IGestionReclamation iGestionReclamation;

    
    @GetMapping("/GetAllReclamtions")
    public List<Reclamtion> getall()
    {
        return  iGestionReclamation.retrieveAllReclamation();
    }










}
