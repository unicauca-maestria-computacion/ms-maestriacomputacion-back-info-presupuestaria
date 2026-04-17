package co.edu.unicauca.informacion_presupuestaria.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScholarshipResponse {
    private String tipo;
    private String titulo;
    private String dedicacion;
    private String entidadAsociada;
    private Boolean esOfrecidaPorUnicauca;
}
