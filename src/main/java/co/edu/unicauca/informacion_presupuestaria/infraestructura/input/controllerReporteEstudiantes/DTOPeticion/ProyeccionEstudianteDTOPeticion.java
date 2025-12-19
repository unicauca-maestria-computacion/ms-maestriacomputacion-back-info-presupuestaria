package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOPeticion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProyeccionEstudianteDTOPeticion {
    private String codigoEstudiante;
    private Boolean estaPago;
    private Float porcentajeVotacion;
    private Float porcentajeBeca;
    private Float porcentajeEgresado;
}

