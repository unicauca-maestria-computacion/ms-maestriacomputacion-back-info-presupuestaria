package co.edu.unicauca.informacion_presupuestaria.domain.model;

import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcademicPeriod {

    private Long id;
    private Integer tagPeriodo;
    private Integer año;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDate fechaFinMatricula;
    private String descripcion;
    private AcademicPeriodStatus estado;

    public boolean esEditable() {
        return AcademicPeriodStatus.ACTIVO.equals(this.estado)
                || !LocalDate.now().isAfter(this.fechaFinMatricula);
    }
}
