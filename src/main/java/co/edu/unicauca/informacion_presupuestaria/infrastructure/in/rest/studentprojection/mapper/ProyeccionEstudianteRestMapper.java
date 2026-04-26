package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.mapper;

import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentFinancialReport;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.model.SubjectResponse;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoRequest.ActualizarProyeccionRequest;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoResponse.ConfiguracionReporteFinancieroResponse;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoResponse.MateriaResponseDto;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoResponse.ProyeccionEstudianteResponse;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoResponse.ReporteEstudiantesResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component("studentProjectionProyeccionMapper")
public class ProyeccionEstudianteRestMapper {

    private final PeriodoAcademicoRestMapper periodoMapper;

    public ProyeccionEstudianteRestMapper(PeriodoAcademicoRestMapper periodoMapper) {
        this.periodoMapper = periodoMapper;
    }

    public ProyeccionEstudianteResponse toResponse(StudentProjection proyeccion) {
        if (proyeccion == null) {
            return null;
        }

        ProyeccionEstudianteResponse dto = new ProyeccionEstudianteResponse();
        dto.setCodigoEstudiante(proyeccion.getCodigoEstudiante());
        dto.setIdentificacion(proyeccion.getIdentificacion());
        dto.setNombre(proyeccion.getNombre());
        dto.setApellido(proyeccion.getApellido());
        dto.setEstaPago(proyeccion.getEstaPago());
        dto.setAplicaVotacion(Boolean.TRUE.equals(proyeccion.getAplicaVotacion()));
        dto.setPorcentajeBeca(proyeccion.getPorcentajeBeca());
        dto.setAplicaEgresado(Boolean.TRUE.equals(proyeccion.getAplicaEgresado()));
        dto.setGrupoInvestigacion(proyeccion.getGrupoInvestigacion());
        dto.setValorEnSMLV(proyeccion.getValorEnSMLV());
        dto.setMaterias(toMateriaResponseList(proyeccion.getMaterias()));

        return dto;
    }

    public List<ProyeccionEstudianteResponse> toResponseList(List<StudentProjection> proyecciones) {
        if (proyecciones == null) {
            return Collections.emptyList();
        }
        return proyecciones.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public StudentProjection toDomain(ActualizarProyeccionRequest request) {
        if (request == null) {
            return null;
        }

        StudentProjection proyeccion = new StudentProjection();
        proyeccion.setCodigoEstudiante(request.getCodigoEstudiante());
        proyeccion.setEstaPago(request.getEstaPago());
        proyeccion.setAplicaVotacion(Boolean.TRUE.equals(request.getAplicaVotacion()));
        proyeccion.setPorcentajeBeca(request.getPorcentajeBeca());
        proyeccion.setAplicaEgresado(Boolean.TRUE.equals(request.getAplicaEgresado()));
        proyeccion.setGrupoInvestigacion(request.getGrupoInvestigacion());


        return proyeccion;
    }

    public ReporteEstudiantesResponse toReporteResponse(StudentFinancialReport reporte) {
        if (reporte == null) {
            return null;
        }

        ReporteEstudiantesResponse dto = new ReporteEstudiantesResponse();
        dto.setPeriodo(periodoMapper.toResponse(reporte.getPeriodo()));
        dto.setConfiguracion(toConfiguracionResponse(reporte.getFinancialReportConfig()));
        dto.setEstudiantes(toResponseList(reporte.getEstudiantes()));
        dto.setTotalNeto(reporte.getTotalNeto());
        dto.setTotalDescuentos(reporte.getTotalDescuentos());
        dto.setTotalIngresos(reporte.getTotalIngresos());

        return dto;
    }

    private ConfiguracionReporteFinancieroResponse toConfiguracionResponse(
            FinancialReportConfig config) {
        if (config == null) {
            return null;
        }

        ConfiguracionReporteFinancieroResponse dto = new ConfiguracionReporteFinancieroResponse();
        dto.setId(config.getId());
        dto.setBiblioteca(config.getBiblioteca());
        dto.setRecursosComputacionales(config.getRecursosComputacionales());
        dto.setValorSMLV(config.getValorSMLV());
        dto.setEsReporteFinal(config.getEsReporteFinal());
        dto.setPorcentajeVotacionFijo(config.getPorcentajeVotacionFijo());
        dto.setPorcentajeEgresadoFijo(config.getPorcentajeEgresadoFijo());
        dto.setPeriodo(periodoMapper.toResponse(config.getAcademicPeriod()));

        return dto;
    }

    private List<MateriaResponseDto> toMateriaResponseList(List<SubjectResponse> materias) {
        if (materias == null) {
            return Collections.emptyList();
        }
        return materias.stream()
                .map(m -> new MateriaResponseDto(m.getCodigo(), m.getNombre(), m.getCreditos()))
                .collect(Collectors.toList());
    }
}
