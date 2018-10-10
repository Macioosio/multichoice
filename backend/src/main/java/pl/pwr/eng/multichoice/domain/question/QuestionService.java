package pl.pwr.eng.multichoice.domain.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.eng.multichoice.domain.answer.Answer;
import pl.pwr.eng.multichoice.domain.answer.AnswerCreationForm;
import pl.pwr.eng.multichoice.domain.answer.AnswerService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerService answerService;

    public List<Question> findAll(){
        return questionRepository.findAll();
    }

    public Question findById(UUID uuid) {
        return questionRepository.findById(uuid);
    }

    public void save(Question question) {
        questionRepository.save(question);
    }

    public void save(QuestionCreationForm questionForm) {
        Question question = new Question();
        question.setContent(questionForm.getContent());
        question.setArea(questionForm.getArea());
        question.setCourse(questionForm.getCourse());
        save(question);
        answerService.save(questionForm.getAnswers(), question);
    }

    public void modifyQuestion(Question modifiedQuestion) {
        Question originalQuestion = findById(modifiedQuestion.getId());
        originalQuestion.setContent(modifiedQuestion.getContent());
        originalQuestion.setArea(modifiedQuestion.getArea());
        save(originalQuestion);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public List<Answer> getAnswers(Question question) {
        return answerService.findByQuestionId(question.getId());
    }

    public List<Question> findByAreaId(UUID id) {
        return questionRepository.findByAreaId(id);
    }

    public List<Question> findByCourseId(UUID id) {
        return questionRepository.findByCourseId(id);
    }
}

