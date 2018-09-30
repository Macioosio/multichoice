package pl.pwr.eng.multichoice.domain.area;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.pwr.eng.multichoice.domain.course.Course;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class AreaForm {

    @NotNull
    private String name;

    @NotNull
    private Course course;
}

