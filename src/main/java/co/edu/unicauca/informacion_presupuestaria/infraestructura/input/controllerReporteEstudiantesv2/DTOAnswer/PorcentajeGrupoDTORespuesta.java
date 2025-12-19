package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantesv2.DTOAnswer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PorcentajeGrupoDTORespuesta {
    private String nombreGrupo;
    private Float porcentaje;
}

