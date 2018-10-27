package pl.pwr.eng.multichoice.domain.solution;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.pwr.eng.multichoice.domain.answer.Answer;
import pl.pwr.eng.multichoice.domain.student.Student;
import pl.pwr.eng.multichoice.domain.test.Test;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class SolutionForm {

    @NotNull
    private Student author;

    @NotNull
    private Test test;

    @NotNull
    private List<Answer> answers;
}

