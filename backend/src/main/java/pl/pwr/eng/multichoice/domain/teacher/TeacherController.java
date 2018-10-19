package pl.pwr.eng.multichoice.domain.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pwr.eng.multichoice.common.util.ConstraintViolationHandler;
import pl.pwr.eng.multichoice.common.util.DTO;
import pl.pwr.eng.multichoice.domain.teacher.Teacher;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController implements ConstraintViolationHandler {

    @Autowired
    TeacherService teacherService;

    @PreAuthorize("denyAll()")
    @GetMapping
    public List<Teacher> getAllTeachers(){
        return teacherService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @GetMapping("/{id}")
    public ResponseEntity getTeacher(@PathVariable(value = "id") UUID uuid){
        Teacher teacher = teacherService.findById(uuid);
        if (teacher == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(teacher);
    }

    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity addTeacher(@Valid @RequestBody @DTO(TeacherForm.class) Teacher teacher, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        teacherService.addTeacher(teacher);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @PutMapping
    public ResponseEntity modifyTeacher(@Valid @RequestBody Teacher teacher, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        teacherService.modifyTeacher(teacher);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
