package co.edu.unicauca.informacion_presupuestaria.domain.model;

import co.edu.unicauca.informacion_presupuestaria.domain.enums.StudentProjectionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentProjection {

    private Long id;
    private String codigoEstudiante;
    private Long identificacion;
    private String nombre;
    private String apellido;
    private Boolean estaPago;
    private Boolean aplicaVotacion;
    private BigDecimal porcentajeBeca;
    private Boolean aplicaEgresado;
    private String grupoInvestigacion;
    private StudentProjectionStatus projectionStatus;
    private Integer valorEnSMLV;
    private AcademicPeriod academicPeriod;
    private List<SubjectResponse> materias;
}
