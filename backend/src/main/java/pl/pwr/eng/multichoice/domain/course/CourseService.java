package pl.pwr.eng.multichoice.domain.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public List<Course> findAll(){
        return courseRepository.findAll();
    }

    public Course findById(UUID uuid) {
        return courseRepository.findById(uuid);
    }

    public void save(Course course) {
        courseRepository.save(course);
    }

    public void modifyCourse(Course modifiedCourse) {
        Course originalCourse = findById(modifiedCourse.getId());
        originalCourse.setName(modifiedCourse.getName());
        save(originalCourse);
    }

    public void delete(Course course) {
        courseRepository.delete(course);
    }
}
