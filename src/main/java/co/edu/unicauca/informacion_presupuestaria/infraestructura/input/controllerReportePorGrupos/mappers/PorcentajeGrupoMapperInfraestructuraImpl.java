package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.PorcentajeGrupo;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer.PorcentajeGrupoDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOPeticion.PorcentajeGrupoDTOPeticion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PorcentajeGrupoMapperInfraestructuraImpl implements PorcentajeGrupoMapperInfraestructura {
    
    @Override
    public PorcentajeGrupo mappearDePeticionAPorcentajeGrupo(PorcentajeGrupoDTOPeticion porcentaje) {
        if (porcentaje == null) {
            return null;
        }
        
        return new PorcentajeGrupo(porcentaje.getNombreGrupo(), porcentaje.getPorcentaje());
    }
    
    @Override
    public List<PorcentajeGrupoDTORespuesta> mappearDeListaPorcentajeGrupoARespuesta(List<PorcentajeGrupoDTOPeticion> porcentajes) {
        if (porcentajes == null) {
            return List.of();
        }
        
        return porcentajes.stream()
                .map(p -> new PorcentajeGrupoDTORespuesta(p.getNombreGrupo(), p.getPorcentaje()))
                .collect(Collectors.toList());
    }
}
