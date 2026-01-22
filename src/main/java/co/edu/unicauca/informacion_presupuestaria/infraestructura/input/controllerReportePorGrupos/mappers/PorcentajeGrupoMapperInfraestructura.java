package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.PorcentajeGrupo;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer.PorcentajeGrupoDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOPeticion.PorcentajeGrupoDTOPeticion;

import java.util.List;

public interface PorcentajeGrupoMapperInfraestructura {
    PorcentajeGrupo mappearDePeticionAPorcentajeGrupo(PorcentajeGrupoDTOPeticion porcentaje);
    List<PorcentajeGrupoDTORespuesta> mappearDeListaPorcentajeGrupoARespuesta(List<PorcentajeGrupoDTOPeticion> porcentajes);
}

