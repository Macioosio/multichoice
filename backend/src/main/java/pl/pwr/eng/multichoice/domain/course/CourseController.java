package pl.pwr.eng.multichoice.domain.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pwr.eng.multichoice.common.util.DTO;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
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
        return ResponseEntity.ok(course.getAreas());
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
