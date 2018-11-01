package pl.pwr.eng.multichoice.domain.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SafeAnswerForm {

    private UUID id;

    @NotNull
    private String content;

}
