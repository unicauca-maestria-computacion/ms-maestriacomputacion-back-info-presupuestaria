package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantesv2.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ValorGrupo;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantesv2.DTOPeticion.ValorGrupoDTOPeticion;

import java.util.List;

public interface ValorGrupoMapperInfraestructura {
    ValorGrupo mappearDePeticionAValorGrupo(ValorGrupoDTOPeticion valores);
    List<ValorGrupo> mappearDeListaPeticionAValorGrupo(List<ValorGrupoDTOPeticion> valores);
}

