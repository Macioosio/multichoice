package pl.pwr.eng.multichoice.domain.course;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CourseForm {

    @NotNull
    private String name;
}
