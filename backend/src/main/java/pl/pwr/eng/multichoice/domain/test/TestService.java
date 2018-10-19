package pl.pwr.eng.multichoice.domain.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TestService {

    @Autowired
    TestRepository testRepository;


    public List<Test> findAll(){
        return testRepository.findAll();
    }

    public Test findById(UUID uuid) {
        return testRepository.findById(uuid);
    }

    public void save(Test test) {
        testRepository.save(test);
    }

    public void modifyTest(Test modifiedTest) {
        Test originalTest = findById(modifiedTest.getId());
        originalTest.setStart(modifiedTest.getStart());
        originalTest.setEnd(modifiedTest.getEnd());
        originalTest.setCourse(modifiedTest.getCourse());
        originalTest.setPassword(modifiedTest.getPassword());
        originalTest.setQuestions(modifiedTest.getQuestions());
        originalTest.setStudents(modifiedTest.getStudents());
        save(originalTest);
    }

    public void delete(Test test) {
        testRepository.delete(test);
    }
}
