package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOPeticion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GastoGeneralDTOPeticion {
    private Integer idGastoGeneral;
    private String categoria;
    private String descripcion;
    private Float monto;
}

