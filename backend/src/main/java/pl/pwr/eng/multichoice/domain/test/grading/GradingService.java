package pl.pwr.eng.multichoice.domain.test.grading;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.eng.multichoice.domain.answer.Answer;
import pl.pwr.eng.multichoice.domain.question.Question;
import pl.pwr.eng.multichoice.domain.question.QuestionService;
import pl.pwr.eng.multichoice.domain.solution.Solution;
import pl.pwr.eng.multichoice.domain.solution.SolutionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradingService {

    @Autowired
    QuestionService questionService;

    @Autowired
    SolutionService solutionService;

    public GradeForm gradeSolution(Solution solution) {
        int maxPoints = solution.getTest().getPoints();
        int points = gradeWithMethod(solution);
        double percent = (points * 100d) / maxPoints;
        solution.setPoints(points);
        solutionService.save(solution);
        return new GradeForm(points, maxPoints, percent);
    }

    private int gradeWithMethod(Solution solution) {
        GradingMethod gradingMethod = solution.getTest().getGradingMethod();
        List<Question> questions = solution.getTest().getQuestions();
        List<Answer> answers = solution.getAnswers();
        int points = 0;
        if(gradingMethod.equals(GradingMethod.DO)){
            for (Question question : questions) {
                List<Answer> questionAnswers = questionService.getAnswers(question);
                List<Answer> correctAnswers = questionAnswers.stream().filter(a -> a.isCorrect()).collect(Collectors.toList());
                if (answers.containsAll(correctAnswers)) {
                    points += correctAnswers.stream().map(Answer::getPoints).reduce(0, Integer::sum);
                }
            }
        }
        if (gradingMethod.equals(GradingMethod.CO)) {
            for (Question question : questions) {
                List<Answer> answered = answers.stream().filter(a -> a.getQuestion().equals(question)).collect(Collectors.toList());
                boolean containsWrongAnswer = answered.stream().filter(a -> !a.isCorrect()).findFirst().isPresent();
                if (containsWrongAnswer) {
                    points += 0;
                }else{
                    points += answered.stream()
                            .map(Answer::getPoints)
                            .reduce(0, Integer::sum);
                }
            }
        }
        if (gradingMethod.equals(GradingMethod.TO)) {
            for (Question question : questions) {
                List<Answer> answered = answers.stream().filter(a -> a.getQuestion().equals(question)).collect(Collectors.toList());
                int answeredPoints  = answered.stream()
                        .map(Answer::getPoints)
                        .reduce(0, Integer::sum);
                points += answeredPoints < 0 ? 0: answeredPoints;
            }
        }
        if (gradingMethod.equals(GradingMethod.TO_MINUS)) {
            points = answers.stream()
                    .map(Answer::getPoints)
                    .reduce(0, Integer::sum);
        }
        return points;
    }
}
