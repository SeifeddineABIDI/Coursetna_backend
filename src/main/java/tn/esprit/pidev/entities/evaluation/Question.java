package tn.esprit.pidev.entities.evaluation;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Question implements Serializable {
    @Id
    @GeneratedValue
    private Long numQuestion;
    private String question;
    private String choice1;
    private String choice2;
    private String choice3;
    private String correctAnswer;
    private int points;

    @ManyToOne
    @JsonIgnore
    private Quiz quiz;

}
