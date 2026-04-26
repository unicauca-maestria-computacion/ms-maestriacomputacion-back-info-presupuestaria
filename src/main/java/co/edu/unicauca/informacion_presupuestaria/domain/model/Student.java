package co.edu.unicauca.informacion_presupuestaria.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private String codigo;
    private String nombre;
    private String apellido;
    private Long identificacion;
    private Integer cohorte;
    private String periodoIngreso;
    private Integer semestreFinanciero;
    private Integer semestreAcademico;
    private Integer valorEnSMLV;
    private Boolean esEgresadoUnicauca;
    private Boolean aplicaVotacion;
    private List<SubjectResponse> materias;
    private List<BecaDescuentoInfo> becasDescuentos;
    private Boolean estaPago;
    private String grupoNombre;
}
