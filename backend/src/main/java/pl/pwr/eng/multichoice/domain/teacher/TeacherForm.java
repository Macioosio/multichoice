package pl.pwr.eng.multichoice.domain.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
public class TeacherForm {
    @Email
    @NotNull
    @Length(min=1)
    private String email;

    @NotNull
    @Length(min=1)
    private String password;
}
