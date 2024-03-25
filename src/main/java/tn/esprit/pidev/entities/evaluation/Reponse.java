package tn.esprit.pidev.entities.evaluation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.pidev.entities.User;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reponse implements Serializable {
    @Id
    @GeneratedValue
    private Long numReponse;
    private String selectedOption;
    @ManyToOne
    private User user;
    @ManyToOne
    private Question question;

}
