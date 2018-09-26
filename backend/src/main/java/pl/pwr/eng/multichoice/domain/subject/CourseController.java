package pl.pwr.eng.multichoice.domain.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses(){
        return courseService.findAll();
    }

    @PostMapping
    public ResponseEntity addSubject(@Valid @RequestBody Course course, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        courseService.save(course);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
