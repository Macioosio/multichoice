package pl.pwr.eng.multichoice.domain.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public List<Question> findAll(){
        return questionRepository.findAll();
    }

    public Question findById(UUID uuid) {
        return questionRepository.findById(uuid);
    }

    public void save(Question question) {
        questionRepository.save(question);
    }

    public void modifyQuestion(Question modifiedQuestion) {
        Question originalQuestion = findById(modifiedQuestion.getId());
        originalQuestion.setName(modifiedQuestion.getName());
        save(originalQuestion);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }
}

