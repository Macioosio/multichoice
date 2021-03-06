package pl.pwr.eng.multichoice.domain.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pwr.eng.multichoice.domain.course.Course;
import pl.pwr.eng.multichoice.domain.question.Question;
import pl.pwr.eng.multichoice.domain.student.Student;
import pl.pwr.eng.multichoice.domain.teacher.Teacher;
import pl.pwr.eng.multichoice.domain.test.grading.GradingMethod;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Test {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Date start;

    @Column(nullable = false)
    private Date end;

    @Column(nullable = false)
    @NotEmpty
    private String password;

    @Column(nullable = false)
    private int points;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GradingMethod gradingMethod;

    @Column(nullable = false)
    private boolean navigable;

    @ManyToOne
    private Course course;

    @ManyToMany
    private List<Question> questions;

    @ManyToMany
    private List<Student> students;

    @ManyToOne
    private Teacher author;
}
