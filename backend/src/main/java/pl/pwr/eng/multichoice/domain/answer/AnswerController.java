package pl.pwr.eng.multichoice.domain.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pwr.eng.multichoice.common.util.DTO;
import pl.pwr.eng.multichoice.common.util.ContraintViolationHandler;


import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@PreAuthorize("hasAuthority('TEACHER')")
@RestController
@RequestMapping("/api/answers")
public class AnswerController implements ContraintViolationHandler {
    @Autowired
    AnswerService answerService;

    @GetMapping
    public List<Answer> getAllAnswers(){
        return answerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getAnswer(@PathVariable(value = "id") UUID uuid){
        Answer answer = answerService.findById(uuid);
        if (answer == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(answer);
    }

    @PostMapping
    public ResponseEntity addAnswer(@Valid @RequestBody @DTO(AnswerForm.class) Answer answer, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        answerService.save(answer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity modifyAnswer(@Valid @RequestBody Answer answer, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        answerService.modifyAnswer(answer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAnswer(@PathVariable(value = "id") UUID uuid){
        Answer answer = answerService.findById(uuid);
        if (answer == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        answerService.delete(answer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
