package pl.pwr.eng.multichoice.domain.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public List<Course> findAll(){
        return courseRepository.findAll();
    }

    public void save(Course course) {
        courseRepository.save(course);
    }
}
