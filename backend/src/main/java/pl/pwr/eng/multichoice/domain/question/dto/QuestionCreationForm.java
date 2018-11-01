package pl.pwr.eng.multichoice.domain.question.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pwr.eng.multichoice.domain.answer.dto.AnswerCreationForm;
import pl.pwr.eng.multichoice.domain.area.Area;
import pl.pwr.eng.multichoice.domain.course.Course;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCreationForm {

    private UUID id;

    @NotNull
    private String content;

    @NotNull
    private Course course;

    private Area area;

    @NotNull
    private List<AnswerCreationForm> answers;
}

