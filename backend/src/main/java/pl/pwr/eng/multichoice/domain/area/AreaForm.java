package pl.pwr.eng.multichoice.domain.area;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class AreaForm {

    @NotNull
    private String name;
}

