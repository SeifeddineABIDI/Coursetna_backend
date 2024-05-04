package tn.esprit.pidev.services.ressources;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.entities.ressources.Badge;
import tn.esprit.pidev.repository.IUserRepository;
import tn.esprit.pidev.repository.ressources.IBadgeRepo;

import java.util.List;

@Service
public class GestionBadge implements IGestionBadge {


    @Autowired
    IUserRepository userRepository;
    @Autowired
    IGestionRessource ressourceService;
    @Autowired
    IBadgeRepo badgeRepo;

    @Override
//    @Scheduled(cron = "0 0 0 * * MON")
    @Scheduled(fixedDelay = 60000) // Exécuter toutes les minutes
    public void verifierEtAttribuerBadgesPlanifiee() {
        List<User> utilisateurs = userRepository.findAll();
        for (User utilisateur : utilisateurs) {
            verifierEtAttribuerBadges(utilisateur.getId());
        }
    }

    @Transactional
    public void verifierEtAttribuerBadges(Long userId) {
        User utilisateur = userRepository.findById(userId).orElse(null);
        if (utilisateur != null) {
            utilisateur.getBadges().size(); // Assure le chargement des badges de l'utilisateur
            int nombreRessources = ressourceService.countRessourcesByUserId(userId);
            if (nombreRessources > 10 && !utilisateurHasBadge(utilisateur, "Badge pour plus de 10 ressources")) {
                attribuerBadge(utilisateur.getId(), "Badge pour plus de 10 ressources");
                System.out.println("Badge attribué à l'utilisateur avec l'ID " + userId + " pour avoir plus de 10 ressources.");
            }
        }
    }

    @Override
    public void attribuerBadge(Long userId, String badgeNom) {

    }


    private boolean utilisateurHasBadge(User utilisateur, String nomBadge) {
        return utilisateur.getBadges().stream().anyMatch(b -> b.getNom().equals(nomBadge));
    }

    public void attribuerBadge(Long userId, String badgeNom, String niveau) {
        User utilisateur = userRepository.findById(userId).orElse(null);
        if (utilisateur != null) {
            Badge badge = new Badge();
            badge.setNom(badgeNom);
            badge.setDescription("Description par défaut"); // Assurez-vous de définir une description
            badge.setNiveau(niveau); // Définir le niveau du badge
            badge = badgeRepo.save(badge); // Sauvegarder d'abord le badge
            utilisateur.addBadge(badge);
            userRepository.save(utilisateur);
            System.out.println("Badge '" + badgeNom + "' attribué à l'utilisateur avec l'ID " + userId);
        }
    }


}
