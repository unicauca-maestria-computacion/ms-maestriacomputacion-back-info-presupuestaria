package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantesv2.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.PorcentajeGrupo;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantesv2.DTOAnswer.PorcentajeGrupoDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantesv2.DTOPeticion.PorcentajeGrupoDTOPeticion;

import java.util.List;

public interface PorcentajeGrupoMapperInfraestructura {
    PorcentajeGrupo mappearDePeticionAPorcentajeGrupo(PorcentajeGrupoDTOPeticion porcentaje);
    List<PorcentajeGrupoDTORespuesta> mappearDeListaPorcentajeGrupoARespuesta(List<PorcentajeGrupoDTOPeticion> porcentajes);
}

