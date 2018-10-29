package pl.pwr.eng.multichoice.domain.solution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pwr.eng.multichoice.domain.answer.Answer;
import pl.pwr.eng.multichoice.domain.student.Student;
import pl.pwr.eng.multichoice.domain.test.Test;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Solution {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @ManyToOne
    private Student author;

    @NotNull
    @ManyToOne
    private Test test;

    @ManyToMany
    private List<Answer> answers;

    private int points;

    private boolean posted;
}
