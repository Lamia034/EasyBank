package dto;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@NotNull
public class Agency {

    private Integer code;
    private String name;
    private String adresse;
    private String phone;
    private List<Poste> postes;
}

