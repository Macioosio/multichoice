package pl.pwr.eng.multichoice.domain.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
public class StudentForm {
    @Email
    @NotNull
    @Length(min=1)
    private String email;

    @NotNull
    @Length(min=1)
    private String password;
}
