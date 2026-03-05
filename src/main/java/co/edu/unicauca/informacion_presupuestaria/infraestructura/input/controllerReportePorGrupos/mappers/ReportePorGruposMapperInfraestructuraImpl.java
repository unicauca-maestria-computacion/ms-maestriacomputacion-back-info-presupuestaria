package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReportePorGrupos;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer.ConfiguracionReporteGruposDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer.ReportePorGruposDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOPeticion.ReportePorGruposDTOPeticion;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class ReportePorGruposMapperInfraestructuraImpl implements ReportePorGruposMapperInfraestructura {
    
    private final GastoGeneralMapperInfraestructura objGastoGeneralMapper;
    private final PeriodoAcademicoMapperInfraestructura objPeriodoAcademicoMapper;
    
    public ReportePorGruposMapperInfraestructuraImpl(GastoGeneralMapperInfraestructura objGastoGeneralMapper,
                                                     PeriodoAcademicoMapperInfraestructura objPeriodoAcademicoMapper) {
        this.objGastoGeneralMapper = objGastoGeneralMapper;
        this.objPeriodoAcademicoMapper = objPeriodoAcademicoMapper;
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
        
        if (reporte.getObjConfiguracionReporteGrupos() != null) {
            dto.setObjConfiguracionReporteGrupos(mappearConfiguracionARespuesta(reporte.getObjConfiguracionReporteGrupos()));
        }
        
        return dto;
    }
    
    private ConfiguracionReporteGruposDTORespuesta mappearConfiguracionARespuesta(co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteGrupos config) {
        ConfiguracionReporteGruposDTORespuesta dto = new ConfiguracionReporteGruposDTORespuesta();
        if (config.getId() != null) {
            dto.setIdConfiguracionReporteGrupos(config.getId());
        }
        dto.setAUIPorcentaje(config.getaUIPorcentaje());
        dto.setExcedentesMaestria(config.getExcedentesMaestria());
        dto.setAUIValor(config.getaUIValor());
        dto.setIngresosNetos(config.getIngresosNetos());
        dto.setValorADistribuir(config.getValorADistribuir());
        dto.setItem1(config.getItem1());
        dto.setItem2(config.getItem2());
        dto.setImprevistos(config.getImprevistos());
        if (config.getObjPeriodoAcademico() != null) {
            dto.setObjPeriodoAcademico(objPeriodoAcademicoMapper.mappearDePeriodoAcademicoARespuesta(config.getObjPeriodoAcademico()));
        }
        if (config.getGastosGenerales() != null && !config.getGastosGenerales().isEmpty()) {
            dto.setGastosGenerales(config.getGastosGenerales().stream()
                    .map(objGastoGeneralMapper::mappearDeGastoGeneralARespuesta)
                    .collect(Collectors.toList()));
        } else {
            dto.setGastosGenerales(Collections.emptyList());
        }
        return dto;
    }
}
