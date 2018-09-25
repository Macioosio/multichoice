package pl.pwr.eng.multichoice.domain.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class CourseController {
    @Autowired
    CourseService courseService;

    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public List<Course> getAllSubjects(){
        return courseService.findAll();
    }
}
