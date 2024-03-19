package tn.esprit.pidev.entities.evaluation;

import jakarta.persistence.*;
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

    @ManyToOne
    private Quiz quiz;

}
