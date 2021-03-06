package pl.pwr.eng.multichoice.domain.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.eng.multichoice.domain.answer.Answer;
import pl.pwr.eng.multichoice.domain.answer.AnswerService;
import pl.pwr.eng.multichoice.domain.answer.dto.AnswerCreationForm;
import pl.pwr.eng.multichoice.domain.answer.dto.SafeAnswerForm;
import pl.pwr.eng.multichoice.domain.question.dto.QuestionCreationForm;

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
        UUID questionId = questionForm.getId();
        Question question = questionId == null ? new Question() : findById(questionId);
        question.setContent(questionForm.getContent());
        question.setArea(questionForm.getArea());
        question.setCourse(questionForm.getCourse());
        question.setPoints(getPoints(questionForm.getAnswers()));
        save(question);
        answerService.save(questionForm.getAnswers(), question);
    }

    private int getPoints(List<AnswerCreationForm> answers) {
        int pointsSum = answers.stream()
                .map(AnswerCreationForm::getPoints)
                .filter(p -> p > 0)
                .reduce(0, Integer::sum);
        return pointsSum;
    }

    public void modifyQuestion(Question modifiedQuestion) {
        Question originalQuestion = findById(modifiedQuestion.getId());
        originalQuestion.setContent(modifiedQuestion.getContent());
        originalQuestion.setArea(modifiedQuestion.getArea());
        originalQuestion.setPoints(sumPoints(modifiedQuestion));
        save(originalQuestion);
    }

    private int sumPoints(Question question) {
        int sumPoints = getAnswers(question).stream()
                .map(Answer::getPoints)
                .filter(points -> points >= 0)
                .reduce(0, Integer::sum);
        return sumPoints;
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

    public List<SafeAnswerForm> getAnswersSafe(Question question) {
        List<SafeAnswerForm> answers = getAnswers(question)
                .stream()
                .map(answer -> new SafeAnswerForm(answer.getId(), answer.getContent(), answer.getQuestion().getId()))
                .collect(Collectors.toList());
        return answers;
    }
}

