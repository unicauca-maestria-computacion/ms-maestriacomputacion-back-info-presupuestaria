package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReportePorGrupos;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer.ReportePorGruposDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOPeticion.ReportePorGruposDTOPeticion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReportePorGruposMapperInfraestructuraImpl implements ReportePorGruposMapperInfraestructura {

    private final GastoGeneralMapperInfraestructura objGastoGeneralMapper;

    public ReportePorGruposMapperInfraestructuraImpl(GastoGeneralMapperInfraestructura objGastoGeneralMapper) {
        this.objGastoGeneralMapper = objGastoGeneralMapper;
    }

    @Override
    public ReportePorGrupos mappearDePeticionAReportePorGrupos(ReportePorGruposDTOPeticion reporte) {
        // Implementación si se necesita mapear desde petición
        // Por ahora retornamos null ya que no se usa en el controlador
        return null;
    }

    @Override
    public ReportePorGruposDTORespuesta mappearDeReportePorGruposARespuesta(ReportePorGrupos reporte) {
        if (reporte == null) {
            return null;
        }

        ReportePorGruposDTORespuesta dto = new ReportePorGruposDTORespuesta();
        dto.setTotalNeto(reporte.getTotalNeto());
        dto.setAportePrimerSemestre(reporte.getAportePrimerSemestre());
        dto.setAporteSegundoSemestre(reporte.getAporteSegundoSemestre());
        dto.setParticipacionPrimerSemestre(reporte.getParticipacionPrimerSemestre());
        dto.setParticipacionSegundoSemestre(reporte.getParticipacionSegundoSemestre());
        dto.setParticipacionPorAño(reporte.getParticipacionPorAño());
        dto.setPresupuestoPorGrupoItem1(reporte.getPresupuestoPorGrupoItem1());
        dto.setPresupuestoPorGrupoItem2(reporte.getPresupuestoPorGrupoItem2());
        dto.setPresupuestoPorGrupo(reporte.getPresupuestoPorGrupo());
        dto.setImprevistos(reporte.getImprevistos());
        dto.setPresupuestoPorGrupoImprevistos(reporte.getPresupuestoPorGrupoImprevistos());
        dto.setVigenciasAnteriores(reporte.getVigenciasAnteriores());

        if (reporte.getGastosGenerales() != null) {
            dto.setGastosGenerales(reporte.getGastosGenerales().stream()
                    .map(objGastoGeneralMapper::mappearDeGastoGeneralARespuesta)
                    .collect(Collectors.toList()));
        }

        if (reporte.getObjGrupo() != null) {
            co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer.GrupoDTORespuesta grupoDTO = new co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer.GrupoDTORespuesta();
            grupoDTO.setNombre(reporte.getObjGrupo().getNombre());
            dto.setObjGrupo(grupoDTO);
        }

        return dto;
    }
}
