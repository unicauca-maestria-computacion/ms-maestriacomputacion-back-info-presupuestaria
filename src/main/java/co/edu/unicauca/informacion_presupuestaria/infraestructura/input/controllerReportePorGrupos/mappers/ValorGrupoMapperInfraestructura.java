package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ValorGrupo;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOPeticion.ValorGrupoDTOPeticion;

import java.util.List;

public interface ValorGrupoMapperInfraestructura {
    ValorGrupo mappearDePeticionAValorGrupo(ValorGrupoDTOPeticion valores);
    List<ValorGrupo> mappearDeListaPeticionAValorGrupo(List<ValorGrupoDTOPeticion> valores);
}

