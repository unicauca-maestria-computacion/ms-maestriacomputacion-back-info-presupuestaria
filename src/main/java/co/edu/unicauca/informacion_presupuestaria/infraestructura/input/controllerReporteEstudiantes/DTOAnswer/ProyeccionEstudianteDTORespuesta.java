package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOAnswer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProyeccionEstudianteDTORespuesta {
    private String codigoEstudiante;
    private Boolean estaPago;
    private Float porcentajeVotacion;
    private Float porcentajeBeca;
    private Float porcentajeEgresado;
}

