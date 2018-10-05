package pl.pwr.eng.multichoice.domain.question;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class QuestionForm {

    @NotNull
    private String name;

}

