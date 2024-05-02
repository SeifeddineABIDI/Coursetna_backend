package tn.esprit.pidev.entities.ressources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class VersionRessource implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String versionName;

        private String cheminFichier;

        private String contenu;

        @ManyToOne
        @JoinColumn(name = "ressource_id")
        private Ressource ressource;

}

