package pl.pwr.eng.multichoice.domain.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pwr.eng.multichoice.common.util.ConstraintViolationHandler;
import pl.pwr.eng.multichoice.common.util.DTO;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@PreAuthorize("hasAuthority('TEACHER')")
@RestController
@RequestMapping("/api/tests")
public class TestController implements ConstraintViolationHandler {
    @Autowired
    TestService testService;

    @GetMapping
    public List<Test> getAllTests(){
        return testService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getTest(@PathVariable(value = "id") UUID uuid){
        Test test = testService.findById(uuid);
        if (test == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(test);
    }

    @PostMapping
    public ResponseEntity addTest(@Valid @RequestBody @DTO(TestForm.class) Test test, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        testService.save(test);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity modifyTest(@Valid @RequestBody Test test, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        testService.modifyTest(test);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTest(@PathVariable(value = "id") UUID uuid){
        Test test = testService.findById(uuid);
        if (test == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        testService.delete(test);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

