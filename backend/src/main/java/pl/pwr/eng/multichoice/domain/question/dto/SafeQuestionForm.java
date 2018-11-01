package pl.pwr.eng.multichoice.domain.question.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pwr.eng.multichoice.domain.answer.dto.SafeAnswerForm;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SafeQuestionForm {

    private UUID id;

    private String content;

    private List<SafeAnswerForm> answers;
}
