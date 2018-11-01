package pl.pwr.eng.multichoice.domain.test.dto;

import lombok.Data;
import pl.pwr.eng.multichoice.domain.course.Course;
import pl.pwr.eng.multichoice.domain.test.Test;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
public class StudentTransferTestForm {
    @NotNull
    private UUID id;

    @NotNull
    private Date start;

    @NotNull
    private Date end;

    @NotNull
    private Course course;

    public StudentTransferTestForm(Test test) {
        this.setId(test.getId());
        this.setStart(test.getStart());
        this.setEnd(test.getEnd());
        this.setCourse(test.getCourse());
    }
}
