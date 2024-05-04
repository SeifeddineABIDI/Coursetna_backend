package tn.esprit.pidev.services.ressources;

import org.springframework.scheduling.annotation.Scheduled;

public interface IGestionBadge {
    @Scheduled(cron = "0 0 0 * * MON") // Exécuter chaque lundi à minuit
    void verifierEtAttribuerBadgesPlanifiee();

    void verifierEtAttribuerBadges(Long userId);

    void attribuerBadge(Long userId, String badgeNom);
}
