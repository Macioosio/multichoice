package pl.pwr.eng.multichoice.domain.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerCreationForm {

    private UUID id;

    @NotNull
    private String content;

    @NotNull
    private boolean isCorrect;
}
