package pl.pwr.eng.multichoice.domain.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pwr.eng.multichoice.domain.test.dto.StudentTransferTestForm;
import pl.pwr.eng.multichoice.domain.test.TestService;
import pl.pwr.eng.multichoice.domain.user.User;
import pl.pwr.eng.multichoice.domain.user.UserService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {
    
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    UserService userService;
    
    @Autowired
    TestService testService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(UUID id){
        return studentRepository.findById(id);
    }
    public void addStudent(Student student) {
        String encodedPassword = bCryptPasswordEncoder.encode(student.getPassword());
        student.setPassword(encodedPassword);
        save(student);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public void modifyStudent(Student modifiedStudent) {
        Student originalStudent = findById(modifiedStudent.getId());
        originalStudent.setEmail(modifiedStudent.getEmail());
        String encodedPassword = bCryptPasswordEncoder.encode(modifiedStudent.getPassword());
        originalStudent.setPassword(encodedPassword);
        save(originalStudent);
    }

    public List<StudentTransferTestForm> findTestsOfStudent() {
        Student student = getStudentFromAuthentication();
        List<StudentTransferTestForm> studentsTests = testService.findAll()
                .stream()
                .filter(test -> test.getStudents().contains(student))
                .map(StudentTransferTestForm::new)
                .collect(Collectors.toList());
        return studentsTests;
    }
    
    public Student getStudentFromAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Student student = findByEmail(authentication.getName());
        return student;
    }

    private Student findByEmail(String email) {
        User user = userService.findByEmail(email);
        Student student = findById(user.getId());
        return student;
    }
}
