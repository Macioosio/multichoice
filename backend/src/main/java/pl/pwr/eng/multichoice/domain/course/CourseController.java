package pl.pwr.eng.multichoice.domain.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pwr.eng.multichoice.common.util.DTO;
import pl.pwr.eng.multichoice.domain.area.Area;
import pl.pwr.eng.multichoice.common.util.ContraintViolationHandler;
import pl.pwr.eng.multichoice.domain.question.Question;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController implements ContraintViolationHandler {
    @Autowired
    CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses(){
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getCourse(@PathVariable(value = "id") UUID uuid){
        Course course = courseService.findById(uuid);
        if (course == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(course);
    }

    @GetMapping("/{id}/areas")
    public ResponseEntity getAreas(@PathVariable(value = "id") UUID uuid){
        Course course = courseService.findById(uuid);
        if (course == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        List<Area> areas = courseService.getAreas(course);
        return ResponseEntity.ok(areas);
    }

    @GetMapping("/{id}/questions")
    public ResponseEntity getQuestions(@PathVariable(value = "id") UUID uuid){
        Course course = courseService.findById(uuid);
        if (course == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        List<Question> questions = courseService.getQuestions(course);
        return ResponseEntity.ok(questions);
    }


    @PostMapping
    public ResponseEntity addCourse(@Valid @RequestBody @DTO(CourseForm.class) Course course, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        courseService.save(course);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity modifyCourse(@Valid @RequestBody Course course, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        courseService.modifyCourse(course);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCourse(@PathVariable(value = "id") UUID uuid){
        Course course = courseService.findById(uuid);
        if (course == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        courseService.delete(course);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
