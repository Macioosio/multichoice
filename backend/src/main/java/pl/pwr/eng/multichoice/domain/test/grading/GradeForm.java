package pl.pwr.eng.multichoice.domain.test.grading;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GradeForm {

    int points;

    int maxPoints;

    double percent;
}
