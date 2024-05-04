package tn.esprit.pidev.entities.ressources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Topic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String contenuTopic;
    private String photo;
    @JsonIgnore
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Ressource> ressources;

}
