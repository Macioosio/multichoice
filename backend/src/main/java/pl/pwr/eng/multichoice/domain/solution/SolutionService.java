package pl.pwr.eng.multichoice.domain.solution;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.eng.multichoice.domain.answer.Answer;
import pl.pwr.eng.multichoice.domain.answer.AnswerService;
import pl.pwr.eng.multichoice.domain.question.Question;
import pl.pwr.eng.multichoice.domain.question.QuestionService;
import pl.pwr.eng.multichoice.domain.student.Student;
import pl.pwr.eng.multichoice.domain.student.StudentService;
import pl.pwr.eng.multichoice.domain.test.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SolutionService {

    @Autowired
    SolutionRepository solutionRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    public List<Solution> findAll(){
        return solutionRepository.findAll();
    }

    public Solution findById(UUID uuid) {
        return solutionRepository.findById(uuid);
    }

    public void save(Solution solution) {
        solutionRepository.save(solution);
    }

    public Solution addOrGetSolution(SolutionForm solutionForm) {
        Test test = solutionForm.getTest();
        Student author = studentService.getStudentFromAuthentication();
        List<Solution> solutionsOfTest = solutionRepository.findAllByTest(test);
        Solution solution = solutionsOfTest
                .stream()
                .findFirst()
                .orElse(null);
        if (solution == null) {
            solution = newSolutionFromForm(solutionForm);
        }
        return solution;
    }

    public Solution newSolutionFromForm(SolutionForm solutionForm) {
        Solution solution = new Solution();
        solution.setTest(solutionForm.getTest());
        solution.setAuthor(studentService.getStudentFromAuthentication());
        solution.setPoints(0);
        solution.setPosted(false);
        solution.setAnswers(new LinkedList<>());
        solutionRepository.save(solution);
        return solution;
    }

    public void modifySolution(Solution modifiedSolution) {
        Solution originalSolution = findById(modifiedSolution.getId());
        originalSolution.setAnswers(modifiedSolution.getAnswers());
        originalSolution.setAuthor(modifiedSolution.getAuthor());
        originalSolution.setPoints(modifiedSolution.getPoints());
        originalSolution.setTest(modifiedSolution.getTest());
        save(originalSolution);
    }

    public void delete(Solution solution) {
        solutionRepository.delete(solution);
    }

    public void addAnswers(AnswersAddingForm answersAddingForm, UUID uuid) {
        Solution solution = findById(uuid);
        List<Answer> answersInSolution = solution.getAnswers();
        Question question = questionService.findById(answersAddingForm.getQuestionID());
        List<Answer> submittedAnswers = answersAddingForm.getAnswerIDs()
                .stream()
                .map(answerService::findById)
                .collect(Collectors.toList());


        List<Answer> existingAnswersToQuestion = answersInSolution
                .stream()
                .filter(answer -> answer.getQuestion().equals(question))
                .collect(Collectors.toList());

        if (existingAnswersToQuestion.size() != 0) {
            answersInSolution.removeAll(existingAnswersToQuestion);
        }

        answersInSolution.addAll(submittedAnswers);
        save(solution);
    }
}

