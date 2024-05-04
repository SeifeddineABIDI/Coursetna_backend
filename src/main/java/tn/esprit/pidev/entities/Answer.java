package tn.esprit.pidev.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Answer implements Serializable {
    @Id
    @GeneratedValue
    private Long numAnswer;
    private String selectedChoice;

    @ManyToOne
    private User user;
    @ManyToOne
    private Question question;

}
