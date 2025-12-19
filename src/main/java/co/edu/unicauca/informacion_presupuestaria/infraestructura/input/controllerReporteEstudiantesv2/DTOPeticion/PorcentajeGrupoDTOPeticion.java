package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantesv2.DTOPeticion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PorcentajeGrupoDTOPeticion {
    private String nombreGrupo;
    private Float porcentaje;
}

