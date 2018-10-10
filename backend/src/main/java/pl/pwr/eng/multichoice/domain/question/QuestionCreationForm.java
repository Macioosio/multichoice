package pl.pwr.eng.multichoice.domain.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pwr.eng.multichoice.domain.answer.AnswerCreationForm;
import pl.pwr.eng.multichoice.domain.area.Area;
import pl.pwr.eng.multichoice.domain.course.Course;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCreationForm {

    @NotNull
    private String content;

    private Area area;

    @NotNull
    private Course course;

    @NotNull
    private List<AnswerCreationForm> answers;
}

