package tn.esprit.pidev.services.evaluation;


import tn.esprit.pidev.entities.evaluation.Question;
import tn.esprit.pidev.entities.evaluation.Quiz;

import java.util.List;

public interface IGestionQuiz {
    List<Quiz> retrieveAllQuizs();
    Quiz retrieveQuiz (Long numQuiz);

    Quiz updateQuiz (Quiz quiz);
    void removeQuiz(Long numQuiz);
    Quiz addQuizAndAssignToTopic(Quiz quiz, Long numTopic);

/********************************************/
    void removeQuizWithNoQuestion();
    List<Quiz> getQuizNotEmpty();


}
