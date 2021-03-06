package pl.pwr.eng.multichoice.domain.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.pwr.eng.multichoice.domain.question.Question;
import pl.pwr.eng.multichoice.domain.question.QuestionService;
import pl.pwr.eng.multichoice.domain.question.dto.SafeQuestionForm;
import pl.pwr.eng.multichoice.domain.solution.Solution;
import pl.pwr.eng.multichoice.domain.solution.SolutionService;
import pl.pwr.eng.multichoice.domain.teacher.Teacher;
import pl.pwr.eng.multichoice.domain.teacher.TeacherService;
import pl.pwr.eng.multichoice.domain.test.dto.TestSolvingForm;


import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TestService {

    @Autowired
    TestRepository testRepository;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private SolutionService solutionService;


    public List<Test> findAll(){
        return testRepository.findAll();
    }

    public Test findById(UUID uuid) {
        return testRepository.findById(uuid);
    }

    public void save(Test test) {
        testRepository.save(test);
    }

    public List<Test> findAllOfAuthor(){
        Teacher author = getTeacherFromAuthentication();
        List<Test> allTests = findAll();
        List<Test> testsOfAuthor = allTests
                .stream()
                .filter(test -> test.getAuthor().equals(author))
                .collect(Collectors.toList());
        return testsOfAuthor;
    }

    public void addTest(Test test) {
        Teacher author = getTeacherFromAuthentication();
        test.setAuthor(author);
        test.setPoints(sumPoints(test));
        save(test);
    }

    private int sumPoints(Test test) {
        int sumPoints = test.getQuestions().stream()
                .map(Question::getPoints)
                .filter(points -> points >= 0)
                .reduce(0, Integer::sum);
        return sumPoints;
    }

    public List<SafeQuestionForm> getTestsQuestionsSafe(UUID uuid) {
        Test test = findById(uuid);
        List<SafeQuestionForm> questionFormList = test.getQuestions()
                .stream()
                .map(question ->
                        new SafeQuestionForm (
                        question.getId(),
                        question.getContent(),
                        questionService.getAnswersSafe(question)
                        )
                )
                .collect(Collectors.toList());
        Collections.shuffle(questionFormList);
        return questionFormList;
    }

    public void modifyTest(Test modifiedTest) {
        Test originalTest = findById(modifiedTest.getId());
        originalTest.setStart(modifiedTest.getStart());
        originalTest.setEnd(modifiedTest.getEnd());
        originalTest.setCourse(modifiedTest.getCourse());
        originalTest.setPassword(modifiedTest.getPassword());
        originalTest.setNavigable(modifiedTest.isNavigable());
        originalTest.setGradingMethod(modifiedTest.getGradingMethod());
        originalTest.setQuestions(modifiedTest.getQuestions());
        originalTest.setStudents(modifiedTest.getStudents());
        originalTest.setAuthor(getTeacherFromAuthentication());
        originalTest.setPoints(sumPoints(modifiedTest));
        save(originalTest);
    }

    public void delete(Test test) {
        testRepository.delete(test);
    }

    private Teacher getTeacherFromAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Teacher teacher = teacherService.findByEmail(authentication.getName());
        return teacher;
    }

    public boolean authorizeSolving(UUID uuid, String password) {
        Test test = findById(uuid);
        return password.equals(test.getPassword());
    }

    public TestSolvingForm getTestSolving(UUID uuid) {
        Test test = findById(uuid);
        boolean navigable = test.isNavigable();
        List<SafeQuestionForm> questions = getTestsQuestionsSafe(uuid);
        boolean isSolvingTime = isSolvingTime(test);
        return new TestSolvingForm(navigable, questions, isSolvingTime);
    }

    private boolean isSolvingTime(Test test) {
        Date a = test.getStart();
        Date b = test.getEnd();
        Date c = new Date();
        return a.compareTo(c) * c.compareTo(b) > 0;
    }

    public List<Solution> getTestsSolutions(UUID uuid) {
        Test test = findById(uuid);
        List<Solution> testSolutions = solutionService.findAll().stream()
                .filter(solution -> solution.getTest().equals(test))
                .collect(Collectors.toList());
        return testSolutions;
    }
}
