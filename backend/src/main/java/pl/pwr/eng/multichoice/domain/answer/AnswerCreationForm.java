package pl.pwr.eng.multichoice.domain.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerCreationForm {

    @NotNull
    private String content;

    @NotNull
    private boolean isCorrect;
}
