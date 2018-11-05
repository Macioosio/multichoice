package pl.pwr.eng.multichoice.domain.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pl.pwr.eng.multichoice.domain.area.Area;
import pl.pwr.eng.multichoice.domain.course.Course;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(columnDefinition = "text")
    private String content;

    @Column(nullable = false)
    private int points;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Area area;
}
