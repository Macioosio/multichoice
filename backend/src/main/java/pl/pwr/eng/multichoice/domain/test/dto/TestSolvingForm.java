package pl.pwr.eng.multichoice.domain.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pwr.eng.multichoice.domain.question.dto.SafeQuestionForm;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestSolvingForm {

    @NotNull
    private boolean navigable;

    @NotNull
    private List<SafeQuestionForm> questions;

    @NotNull
    private boolean isSolvingTime;
}
