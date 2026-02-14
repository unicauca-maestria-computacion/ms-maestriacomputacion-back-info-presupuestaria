package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOPeticion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValorGrupoDTOPeticion {
    private String idGrupo;
    private String nombreGrupo;
    private Float valor;
}

