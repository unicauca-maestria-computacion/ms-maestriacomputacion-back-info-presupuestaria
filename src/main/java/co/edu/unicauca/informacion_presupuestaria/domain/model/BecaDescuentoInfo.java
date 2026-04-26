package co.edu.unicauca.informacion_presupuestaria.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BecaDescuentoInfo {
    private String tipo;
    private Float porcentaje;
    private String resolucion;
    private String estado;
    private String avaladoConcejo;
}
