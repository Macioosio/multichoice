package pl.pwr.eng.multichoice.domain.solution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pwr.eng.multichoice.common.util.ConstraintViolationHandler;
import pl.pwr.eng.multichoice.common.util.DTO;
import pl.pwr.eng.multichoice.domain.answer.Answer;


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

    @PostMapping
    public ResponseEntity addSolution(@Valid @RequestBody @DTO(SolutionForm.class) Solution solution, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        solutionService.save(solution);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity modifySolution(@Valid @RequestBody Solution solution, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        solutionService.modifySolution(solution);
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
