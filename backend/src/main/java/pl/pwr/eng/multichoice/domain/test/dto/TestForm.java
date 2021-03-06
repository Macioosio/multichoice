package pl.pwr.eng.multichoice.domain.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pwr.eng.multichoice.domain.course.Course;
import pl.pwr.eng.multichoice.domain.question.Question;
import pl.pwr.eng.multichoice.domain.student.Student;
import pl.pwr.eng.multichoice.domain.test.grading.GradingMethod;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestForm {

    @NotNull
    private Date start;

    @NotNull
    private Date end;

    @NotNull
    private Course course;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GradingMethod gradingMethod;

    @NotNull
    private boolean navigable;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    private List<Question> questions;

    @NotNull
    private List<Student> students;
}
