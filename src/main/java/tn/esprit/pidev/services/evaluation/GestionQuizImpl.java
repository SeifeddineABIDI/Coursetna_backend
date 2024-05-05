package tn.esprit.pidev.services.evaluation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.pidev.entities.evaluation.Quiz;
import tn.esprit.pidev.entities.Topic;
import tn.esprit.pidev.repository.evaluation.IQuizRepository;
import tn.esprit.pidev.repository.ItopicRepository;

import java.util.List;

@AllArgsConstructor
@Slf4j//LOGGER
@Service
public class GestionQuizImpl implements IGestionQuiz{
    IQuizRepository quizRepo;
    ItopicRepository topicRepo;
    @Override
    public List<Quiz> retrieveAllQuizs() {
        return quizRepo.findAll();
    }

    @Override
    public Quiz retrieveQuiz(Long numQuiz) {
        return quizRepo.findById(numQuiz).get();
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return quizRepo.save(quiz);
    }

    /*@Override
    public void removeQuiz(Long numQuiz) {
        quizRepo.deleteById(numQuiz);
    }*/
    @Override
    public void removeQuiz(Long numQuiz) {
        Quiz q = quizRepo.findById(numQuiz).get();
        Topic topic = topicRepo.findTopicByQuizId(numQuiz);

            if (topic != null) {
                topic.getListQuiz().remove(q);
                topicRepo.save(topic); // Save the updated topic
            }
        quizRepo.delete(q); // Delete the quiz
    }

    @Override
    public Quiz addQuizAndAssignToTopic(Quiz quiz, Long numTopic){
        Topic topic=topicRepo.findById(numTopic).get();
        topic.getListQuiz().add(quiz);

        return quizRepo.save(quiz);
    }
/**************************************************/
    @Transactional
    @Scheduled(cron="* */30 * * * *")
    @Override
    public void removeQuizWithNoQuestion(){
      List<Quiz>listQuiz=quizRepo.findAll();

        for(Quiz q:listQuiz)
          if (!q.isStatus())
          {
              log.info("le quiz supprimé est :" +q.getNumQuiz());
              quizRepo.delete(q);
          }
    }

    @Override
    public List<Quiz> getQuizNotEmpty(){
        return quizRepo.findAllByListQuestionNotNullAndStatus(true);
    }
    @Override
    public int getdureeByQuiz(Long numQuiz){
        return quizRepo.findById(numQuiz).get().getDuree();
    }

    public void updateQuizStatusToTrue(Long quizId) {
        Quiz quiz = quizRepo.findById(quizId).get();
        if (quiz != null) {
            quiz.setStatus(true);
            quizRepo.save(quiz);
        }
    }


}
