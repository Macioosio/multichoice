package pl.pwr.eng.multichoice.domain.solution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pwr.eng.multichoice.common.util.ConstraintViolationHandler;
import pl.pwr.eng.multichoice.domain.answer.Answer;
import pl.pwr.eng.multichoice.domain.solution.dto.AnswersAddingForm;
import pl.pwr.eng.multichoice.domain.solution.dto.SolutionCreationForm;
import pl.pwr.eng.multichoice.domain.solution.dto.SolutionTransferForm;
import pl.pwr.eng.multichoice.domain.test.grading.GradeForm;


import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/solutions")
public class SolutionController implements ConstraintViolationHandler {
    @Autowired
    SolutionService solutionService;

    @GetMapping
    public List<Solution> getAllSolutions(){
        return solutionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getSolution(@PathVariable(value = "id") UUID uuid){
        Solution solution = solutionService.findById(uuid);
        if (solution == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(solution);
    }

    @GetMapping("/{id}/answers")
    public ResponseEntity getAnswers(@PathVariable(value = "id") UUID uuid){
        Solution solution = solutionService.findById(uuid);
        if (solution == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        List<Answer> answers = solution.getAnswers();
        return ResponseEntity.ok(answers);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping
    public ResponseEntity addOrGetSolution(@Valid @RequestBody SolutionCreationForm solutionCreationForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        SolutionTransferForm solution = solutionService.addOrGetSolution(solutionCreationForm);
        return ResponseEntity.ok(solution);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PatchMapping("/{id}/add/answers")
    public ResponseEntity addAnswer(@PathVariable(value = "id") UUID uuid, @Valid @RequestBody AnswersAddingForm answers, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        solutionService.addAnswers(answers, uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PatchMapping("/{id}/post")
    public ResponseEntity postSolution(@PathVariable(value = "id") UUID uuid) {
        solutionService.postSolution(uuid);
        GradeForm grade = solutionService.gradeSolution(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSolution(@PathVariable(value = "id") UUID uuid){
        Solution solution = solutionService.findById(uuid);
        if (solution == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        solutionService.delete(solution);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
