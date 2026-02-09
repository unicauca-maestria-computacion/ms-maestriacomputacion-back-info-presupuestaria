package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteGrupos;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer.ConfiguracionReporteGruposDTORespuesta;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ConfiguracionReporteGruposMapperInfraestructuraImpl
        implements ConfiguracionReporteGruposMapperInfraestructura {

    private final PeriodoAcademicoMapperInfraestructura objPeriodoAcademicoMapper;
    private final GastoGeneralMapperInfraestructura objGastoGeneralMapper;
    private final ReportePorGruposMapperInfraestructura objReportePorGruposMapper;

    public ConfiguracionReporteGruposMapperInfraestructuraImpl(
            PeriodoAcademicoMapperInfraestructura objPeriodoAcademicoMapper,
            GastoGeneralMapperInfraestructura objGastoGeneralMapper,
            ReportePorGruposMapperInfraestructura objReportePorGruposMapper) {
        this.objPeriodoAcademicoMapper = objPeriodoAcademicoMapper;
        this.objGastoGeneralMapper = objGastoGeneralMapper;
        this.objReportePorGruposMapper = objReportePorGruposMapper;
    }

    @Override
    public ConfiguracionReporteGruposDTORespuesta mappearDeConfiguracionReporteGruposARespuesta(
            ConfiguracionReporteGrupos configuracion) {
        if (configuracion == null) {
            return null;
        }

        ConfiguracionReporteGruposDTORespuesta dto = new ConfiguracionReporteGruposDTORespuesta();
        dto.setIdConfiguracion(configuracion.getIdConfiguracion());
        dto.setAUIPorcentaje(configuracion.getaUIPorcentaje());
        dto.setExcedentesMaestria(configuracion.getExcedentesMaestria());
        dto.setAUIValor(configuracion.getaUIValor());
        dto.setIngresosNetos(configuracion.getIngresosNetos());
        dto.setValorADistribuir(configuracion.getValorADistribuir());
        dto.setItem1(configuracion.getItem1());
        dto.setItem2(configuracion.getItem2());
        dto.setImprevistos(configuracion.getImprevistos());

        if (configuracion.getObjPeriodoAcademico() != null) {
            dto.setObjPeriodoAcademico(objPeriodoAcademicoMapper
                    .mappearDePeriodoAcademicoARespuesta(configuracion.getObjPeriodoAcademico()));
        }

        if (configuracion.getGastosGenerales() != null) {
            dto.setGastosGenerales(configuracion.getGastosGenerales().stream()
                    .map(objGastoGeneralMapper::mappearDeGastoGeneralARespuesta)
                    .collect(Collectors.toList()));
        }

        if (configuracion.getReportePorGrupos() != null) {
            dto.setReportePorGrupos(configuracion.getReportePorGrupos().stream()
                    .map(objReportePorGruposMapper::mappearDeReportePorGruposARespuesta)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
