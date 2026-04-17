package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport.mapper;

import co.edu.unicauca.informacion_presupuestaria.domain.model.GeneralExpense;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReport;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportQuery;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport.dtoResponse.ConsultaReportePorGruposResponse;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport.dtoResponse.GastoGeneralResponseDto;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport.dtoResponse.ReportePorGrupoResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component("groupReportGruposMapper")
public class ReportePorGruposRestMapper {

    private final PeriodoAcademicoRestMapper periodoMapper;

    public ReportePorGruposRestMapper(PeriodoAcademicoRestMapper periodoMapper) {
        this.periodoMapper = periodoMapper;
    }

    public ConsultaReportePorGruposResponse toResponse(GroupReportQuery consulta) {
        if (consulta == null) {
            return null;
        }

        ConsultaReportePorGruposResponse dto = new ConsultaReportePorGruposResponse();
        dto.setPeriodo(periodoMapper.toResponse(consulta.getPeriodo()));
        dto.setAnio(consulta.getAnio());
        dto.setIngresoPeriodo1(consulta.getIngresoPeriodo1());
        dto.setIngresoPeriodo2(consulta.getIngresoPeriodo2());
        dto.setTotalIngresos(consulta.getTotalIngresos());
        dto.setAuiValor(consulta.getAuiValor());
        dto.setValorADistribuir(consulta.getValorADistribuir());

        GroupReportConfig config = consulta.getConfiguracion();
        if (config != null) {
            dto.setAuiPorcentaje(config.getAuiPorcentaje());
            dto.setExcedentesMaestria(config.getExcedentesMaestria());
            dto.setItem1(config.getItem1());
            dto.setItem2(config.getItem2());
            dto.setImprevistos(config.getImprevistos());
            dto.setIdConfiguracionReporteGrupos(config.getId());
        }

        List<GeneralExpense> gastos = consulta.getReportesPorGrupo() != null
                && !consulta.getReportesPorGrupo().isEmpty()
                ? consulta.getReportesPorGrupo().get(0).getGastosGenerales()
                : Collections.emptyList();
        dto.setGastosGenerales(toGastoGeneralResponseList(gastos));

        dto.setReportesPorGrupo(toReportePorGrupoResponseList(consulta.getReportesPorGrupo()));

        return dto;
    }

    private List<ReportePorGrupoResponse> toReportePorGrupoResponseList(
            List<GroupReport> reportes) {
        if (reportes == null) {
            return Collections.emptyList();
        }
        return reportes.stream()
                .map(this::toReportePorGrupoResponse)
                .collect(Collectors.toList());
    }

    private ReportePorGrupoResponse toReportePorGrupoResponse(GroupReport reporte) {
        if (reporte == null) {
            return null;
        }

        ReportePorGrupoResponse dto = new ReportePorGrupoResponse();

        if (reporte.getGrupo() != null) {
            dto.setGrupoId(reporte.getGrupo().getId());
            dto.setNombreGrupo(reporte.getGrupo().getNombre());
        }

        dto.setPorcentajeParticipacion(reporte.getPorcentajeParticipacion());
        dto.setPorcentajePrimerSemestre(reporte.getPorcentajePrimerSemestre());
        dto.setPorcentajeSegundoSemestre(reporte.getPorcentajeSegundoSemestre());
        dto.setVigenciasAnteriores(reporte.getVigenciasAnteriores());
        dto.setPresupuestoPorGrupo(reporte.getPresupuestoPorGrupo());
        dto.setPresupuestoPorGrupoItem1(reporte.getPresupuestoPorGrupoItem1());
        dto.setPresupuestoPorGrupoItem2(reporte.getPresupuestoPorGrupoItem2());
        dto.setImprevistosValor(reporte.getImprevistosValor());
        dto.setPresupuestoPorGrupoImprevistos(reporte.getPresupuestoPorGrupoImprevistos());
        dto.setTotalNeto(reporte.getTotalNeto());
        dto.setAportePrimerSemestre(reporte.getAportePrimerSemestre());
        dto.setAporteSegundoSemestre(reporte.getAporteSegundoSemestre());
        dto.setGastosGenerales(toGastoGeneralResponseList(reporte.getGastosGenerales()));

        return dto;
    }

    private List<GastoGeneralResponseDto> toGastoGeneralResponseList(List<GeneralExpense> gastos) {
        if (gastos == null) {
            return Collections.emptyList();
        }
        return gastos.stream()
                .map(this::toGastoGeneralResponse)
                .collect(Collectors.toList());
    }

    private GastoGeneralResponseDto toGastoGeneralResponse(GeneralExpense gasto) {
        if (gasto == null) {
            return null;
        }
        GastoGeneralResponseDto dto = new GastoGeneralResponseDto();
        dto.setId(gasto.getId());
        dto.setCategoria(gasto.getCategoria());
        dto.setDescripcion(gasto.getDescripcion());
        dto.setMonto(gasto.getMonto());
        return dto;
    }
}
