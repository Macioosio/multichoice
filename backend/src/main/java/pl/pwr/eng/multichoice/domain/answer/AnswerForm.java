package pl.pwr.eng.multichoice.domain.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.pwr.eng.multichoice.domain.question.Question;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class AnswerForm {

    @NotNull
    private String name;

    @NotNull
    private Question question;
}

