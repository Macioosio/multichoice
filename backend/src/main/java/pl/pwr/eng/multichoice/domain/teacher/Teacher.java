package pl.pwr.eng.multichoice.domain.teacher;

import lombok.Data;
import pl.pwr.eng.multichoice.common.security.Roles;
import pl.pwr.eng.multichoice.domain.user.User;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table
@Data
@DiscriminatorValue(Roles.TEACHER)
public class Teacher extends User {

    @Id
    @GeneratedValue
    private UUID id;

}