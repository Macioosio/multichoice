package pl.pwr.eng.multichoice.domain.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pwr.eng.multichoice.common.util.ConstraintViolationHandler;
import pl.pwr.eng.multichoice.common.util.DTO;
import pl.pwr.eng.multichoice.domain.test.StudentTransferTestForm;
import pl.pwr.eng.multichoice.domain.test.Test;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
public class StudentController implements ConstraintViolationHandler {

    @Autowired
    StudentService studentService;

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping
    public List<Student> getAllStudents(){
        return studentService.findAll();
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity getStudent(@PathVariable(value = "id") UUID uuid){
        Student student = studentService.findById(uuid);
        if (student == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(student);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/tests/mine")
    public ResponseEntity getStudentsTests(){
        List<StudentTransferTestForm> tests = studentService.findTestsOfStudent();
        if (tests == null || tests.size() == 0) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tests);
    }

    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity addStudent(@Valid @RequestBody @DTO(StudentForm.class) Student student, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        studentService.addStudent(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PutMapping
    public ResponseEntity modifyStudent(@Valid @RequestBody Student student, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        studentService.modifyStudent(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
