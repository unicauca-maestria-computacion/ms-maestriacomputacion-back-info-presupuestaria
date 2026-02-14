package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ValorGrupo;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOPeticion.ValorGrupoDTOPeticion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ValorGrupoMapperInfraestructuraImpl implements ValorGrupoMapperInfraestructura {
    
    @Override
    public ValorGrupo mappearDePeticionAValorGrupo(ValorGrupoDTOPeticion valores) {
        if (valores == null) {
            return null;
        }
        if (valores.getIdGrupo() != null && !valores.getIdGrupo().isBlank()) {
            return new ValorGrupo(valores.getIdGrupo(), valores.getNombreGrupo(), valores.getValor());
        }
        return new ValorGrupo(valores.getNombreGrupo(), valores.getValor());
    }
    
    @Override
    public List<ValorGrupo> mappearDeListaPeticionAValorGrupo(List<ValorGrupoDTOPeticion> valores) {
        if (valores == null) {
            return List.of();
        }
        
        return valores.stream()
                .map(this::mappearDePeticionAValorGrupo)
                .collect(Collectors.toList());
    }
}
