package pl.pwr.eng.multichoice.domain.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.pwr.eng.multichoice.domain.area.Area;
import pl.pwr.eng.multichoice.domain.course.Course;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class QuestionForm {

    @NotNull
    private String content;

    @NotNull
    private Course course;

    @NotNull
    private Area area;

}

