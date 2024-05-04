package tn.esprit.pidev.entities.ressources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tn.esprit.pidev.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotif;

    private String message;
    private Date dateEnvoi;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User destinataire;

    private boolean estLue;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ressource_id")
    private Ressource ressource;

    @ManyToOne
    @JoinColumn(name = "commentaire_id")
    private Commentaire commentaire;

    @Enumerated(EnumType.STRING)
    private TypeNotif type;


}
