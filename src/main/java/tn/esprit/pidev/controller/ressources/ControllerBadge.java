package tn.esprit.pidev.controller.ressources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pidev.services.ressources.GestionBadge;

@RestController
@RequestMapping("/badges")
public class ControllerBadge {

    @Autowired
    private GestionBadge badgeService;

    @GetMapping("/verifier-et-attribuer")
    public ResponseEntity<String> verifierEtAttribuerBadgesAutomatiquement() {
        badgeService.verifierEtAttribuerBadgesPlanifiee();
        return ResponseEntity.ok("Vérification et attribution de badges lancées avec succès.");
    }
}
