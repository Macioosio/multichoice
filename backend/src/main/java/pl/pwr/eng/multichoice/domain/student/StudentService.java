package pl.pwr.eng.multichoice.domain.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudentService {
    
    @Autowired
    StudentRepository studentRepository;

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
}
