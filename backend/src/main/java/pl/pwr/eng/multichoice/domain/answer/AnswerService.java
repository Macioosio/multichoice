package pl.pwr.eng.multichoice.domain.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.eng.multichoice.domain.answer.dto.AnswerCreationForm;
import pl.pwr.eng.multichoice.domain.question.Question;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;

    public List<Answer> findAll(){
        return answerRepository.findAll();
    }

    public Answer findById(UUID uuid) {
        return answerRepository.findById(uuid);
    }

    public List<Answer> findByQuestionId(UUID uuid) {
        return answerRepository.findByQuestionId(uuid);
    }

    public void save(Answer answer) {
        answerRepository.save(answer);
    }

    public void save(List<AnswerCreationForm> answerForms, Question question) {
        List<Answer> answers = new LinkedList<>();
        for(AnswerCreationForm answerForm : answerForms){
            UUID answerId = answerForm.getId();
            Answer answer = answerId == null ? new Answer() : findById(answerId);
            answer.setContent(answerForm.getContent());
            answer.setCorrect(answerForm.isCorrect());
            answer.setQuestion(question);
            answers.add(answer);
        }
        answerRepository.saveAll(answers);
    }

    public void modifyAnswer(Answer modifiedAnswer) {
        Answer originalAnswer = findById(modifiedAnswer.getId());
        originalAnswer.setContent(modifiedAnswer.getContent());
        originalAnswer.setQuestion(modifiedAnswer.getQuestion());
        originalAnswer.setCorrect(modifiedAnswer.isCorrect());
        save(originalAnswer);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }
}

