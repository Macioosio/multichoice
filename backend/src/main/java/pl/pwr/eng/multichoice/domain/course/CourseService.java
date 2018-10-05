package pl.pwr.eng.multichoice.domain.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.eng.multichoice.domain.area.Area;
import pl.pwr.eng.multichoice.domain.area.AreaService;
import pl.pwr.eng.multichoice.domain.question.Question;
import pl.pwr.eng.multichoice.domain.question.QuestionService;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    AreaService areaService;

    @Autowired
    QuestionService questionService;

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

    public List<Area> getAreas(Course course){
        return areaService.findByCourseId(course.getId());
    }

    public void delete(Course course) {
        courseRepository.delete(course);
    }

    public List<Question> getQuestions(Course course) {
        return questionService.findByCourseId(course.getId());
    }
}
