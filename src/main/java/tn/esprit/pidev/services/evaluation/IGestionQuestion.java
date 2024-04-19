package tn.esprit.pidev.services.evaluation;

import tn.esprit.pidev.entities.evaluation.Question;

import java.util.List;

public interface IGestionQuestion {
    List<Question> retrieveAllQuestions();
    Question retrieveQuestion (Long numQuestion);
    Question addQuestion(Question question);
    Question updateQuestion (Question question);
    void removeQuestion(Long numQuestion);

    Question addQuestionAndAssignToQuiz(Question question,Long numQuiz);
    List<Question> getQuestionsByQuiz(Long numQuiz);
}
