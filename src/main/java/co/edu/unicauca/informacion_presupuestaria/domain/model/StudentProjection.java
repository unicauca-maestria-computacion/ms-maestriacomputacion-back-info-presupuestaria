package co.edu.unicauca.informacion_presupuestaria.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class StudentProjection {

    public StudentProjection(Long id, String codigoEstudiante, Long identificacion, String nombre, String apellido, 
                             Boolean estaPago, Boolean aplicaVotacion, BigDecimal porcentajeBeca, Boolean aplicaEgresado, 
                             String grupoInvestigacion, Integer valorEnSMLV, 
                             AcademicPeriod academicPeriod, List<SubjectResponse> materias) {
        this.id = id;
        this.codigoEstudiante = codigoEstudiante;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.estaPago = estaPago;
        this.aplicaVotacion = aplicaVotacion;
        this.porcentajeBeca = porcentajeBeca;
        this.aplicaEgresado = aplicaEgresado;
        this.grupoInvestigacion = grupoInvestigacion;
        this.valorEnSMLV = valorEnSMLV;
        this.academicPeriod = academicPeriod;
        this.materias = materias;
    }

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
    private Integer valorEnSMLV;
    private AcademicPeriod academicPeriod;
    private List<SubjectResponse> materias;
    private Boolean estadoMatriculaFinanciera;

    // Campos calculados por el servicio financiero
    private BigDecimal valorMatricula;
    private BigDecimal valorDescuentoVoto;
    private BigDecimal valorDescuentoBeca;
    private BigDecimal valorDescuentoEgresado;
    private BigDecimal totalDescuentos;
    private BigDecimal valorNeto;
    private BigDecimal totalNetoConDerechos;
}
