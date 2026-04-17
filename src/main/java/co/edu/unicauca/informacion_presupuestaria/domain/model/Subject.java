package co.edu.unicauca.informacion_presupuestaria.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    private String codigoOid;
    private Integer semestreAcademico;
    private String materia;
    private Teacher objDocente;
    private String grupoClase;
}
