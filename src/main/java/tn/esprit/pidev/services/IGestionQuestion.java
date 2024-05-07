package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.Question;

import java.util.List;

public interface IGestionQuestion {
    List<Question> retrieveAllQuestions();
    Question retrieveQuestion (Long numQuestion);
    Question updateQuestion (Question question,Long numQuiz);
    void removeQuestion(Long numQuestion);
    Question addQuestionAndAssignToQuiz(Question question,Long numQuiz);
    List<Question> getQuestionsByQuiz(Long numQuiz);
}
