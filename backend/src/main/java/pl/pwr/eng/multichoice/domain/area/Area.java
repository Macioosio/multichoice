package pl.pwr.eng.multichoice.domain.area;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pl.pwr.eng.multichoice.domain.course.Course;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Area {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course;
}
