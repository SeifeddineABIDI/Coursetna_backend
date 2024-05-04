package tn.esprit.pidev.entities.ressources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import tn.esprit.pidev.entities.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Badge  implements Serializable {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

       @Column(nullable = false)
        private String nom;

        @Column(nullable = false)
        private String description;

        @Column(nullable = false)
        private String niveau;


        @ManyToMany(mappedBy = "badges", fetch = FetchType.EAGER)
        private Set<User> users = new HashSet<>();


    public Badge(String s, String descriptionDuBadge, String niveauDuBadge) {
    }
}
