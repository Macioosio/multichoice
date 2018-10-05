package pl.pwr.eng.multichoice.domain.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AnswerService {

    @Autowired
    AnswerService answerRepository;

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

    public void modifyAnswer(Answer modifiedAnswer) {
        Answer originalAnswer = findById(modifiedAnswer.getId());
        originalAnswer.setName(modifiedAnswer.getName());
        originalAnswer.setQuestion(modifiedAnswer.getQuestion());
        save(originalAnswer);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }
}

