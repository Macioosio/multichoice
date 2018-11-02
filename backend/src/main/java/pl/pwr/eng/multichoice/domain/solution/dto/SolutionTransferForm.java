package pl.pwr.eng.multichoice.domain.solution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pwr.eng.multichoice.domain.answer.dto.SafeAnswerForm;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolutionTransferForm {

    private UUID solutionId;

    private boolean posted;

    private List<SafeAnswerForm> answers;
}
