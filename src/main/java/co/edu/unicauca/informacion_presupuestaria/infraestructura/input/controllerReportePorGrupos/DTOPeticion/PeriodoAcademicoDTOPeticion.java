package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOPeticion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PeriodoAcademicoDTOPeticion {
    private Integer periodo;
    private Integer anio;

    @JsonCreator
    public PeriodoAcademicoDTOPeticion(
            @JsonProperty("periodo") Integer periodo,
            @JsonProperty("anio") Integer anio) {
        this.periodo = periodo;
        this.anio = anio;
    }
}

