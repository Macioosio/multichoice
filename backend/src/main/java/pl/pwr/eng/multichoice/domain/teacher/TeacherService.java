package pl.pwr.eng.multichoice.domain.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pwr.eng.multichoice.domain.teacher.Teacher;
import pl.pwr.eng.multichoice.domain.teacher.TeacherRepository;

import java.util.List;
import java.util.UUID;

@Service
public class TeacherService {
    
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    public Teacher findById(UUID id){
        return teacherRepository.findById(id);
    }
    public void addTeacher(Teacher teacher) {
        String encodedPassword = bCryptPasswordEncoder.encode(teacher.getPassword());
        teacher.setPassword(encodedPassword);
        save(teacher);
    }

    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public void modifyTeacher(Teacher modifiedTeacher) {
        Teacher originalTeacher = findById(modifiedTeacher.getId());
        originalTeacher.setEmail(modifiedTeacher.getEmail());
        String encodedPassword = bCryptPasswordEncoder.encode(modifiedTeacher.getPassword());
        originalTeacher.setPassword(encodedPassword);
        save(originalTeacher);
    }
}
