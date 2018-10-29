package pl.pwr.eng.multichoice.domain.solution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pwr.eng.multichoice.domain.answer.Answer;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswersAddingForm {

    @NotNull
    UUID questionID;

    @NotNull
    @NotEmpty
    List<UUID> answerIDs;
}
