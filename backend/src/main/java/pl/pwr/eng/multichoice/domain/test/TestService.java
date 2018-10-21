package pl.pwr.eng.multichoice.domain.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.pwr.eng.multichoice.domain.teacher.Teacher;
import pl.pwr.eng.multichoice.domain.teacher.TeacherService;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TestService {

    @Autowired
    TestRepository testRepository;

    @Autowired
    private TeacherService teacherService;


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
        save(test);
    }

    public void modifyTest(Test modifiedTest) {
        Test originalTest = findById(modifiedTest.getId());
        originalTest.setStart(modifiedTest.getStart());
        originalTest.setEnd(modifiedTest.getEnd());
        originalTest.setCourse(modifiedTest.getCourse());
        originalTest.setPassword(modifiedTest.getPassword());
        originalTest.setQuestions(modifiedTest.getQuestions());
        originalTest.setStudents(modifiedTest.getStudents());
        originalTest.setAuthor(getTeacherFromAuthentication());
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
}
