package pl.pwr.eng.multichoice.domain.question;

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
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping
    public List<Question> getAllQuestions(){
        return questionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getQuestion(@PathVariable(value = "id") UUID uuid){
        Question question = questionService.findById(uuid);
        if (question == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(question);
    }

    @PostMapping
    public ResponseEntity addQuestion(@Valid @RequestBody @DTO(QuestionForm.class) Question question, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        questionService.save(question);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity modifyQuestion(@Valid @RequestBody Question question, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        questionService.modifyQuestion(question);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteQuestion(@PathVariable(value = "id") UUID uuid){
        Question question = questionService.findById(uuid);
        if (question == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        questionService.delete(question);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
