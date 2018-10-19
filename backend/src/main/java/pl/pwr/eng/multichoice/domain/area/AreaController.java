package pl.pwr.eng.multichoice.domain.area;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pwr.eng.multichoice.common.util.ConstraintViolationHandler;
import pl.pwr.eng.multichoice.common.util.DTO;
import pl.pwr.eng.multichoice.domain.question.Question;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@PreAuthorize("hasAuthority('TEACHER')")
@RestController
@RequestMapping("/api/areas")
public class AreaController implements ConstraintViolationHandler {
    @Autowired
    AreaService areaService;

    @GetMapping
    public List<Area> getAllAreas(){
        return areaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getArea(@PathVariable(value = "id") UUID uuid){
        Area area = areaService.findById(uuid);
        if (area == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(area);
    }

    @GetMapping("/{id}/questions")
    public ResponseEntity getQuestions(@PathVariable(value = "id") UUID uuid){
        Area area = areaService.findById(uuid);
        if (area == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        List<Question> questions = areaService.getQuestions(area);
        return ResponseEntity.ok(questions);
    }

    @PostMapping
    public ResponseEntity addArea(@Valid @RequestBody @DTO(AreaForm.class) Area area, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        areaService.save(area);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity modifyArea(@Valid @RequestBody Area area, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        areaService.modifyArea(area);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteArea(@PathVariable(value = "id") UUID uuid){
        Area area = areaService.findById(uuid);
        if (area == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        areaService.delete(area);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
