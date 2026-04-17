package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.gateway;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.AcademicPeriodGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper.AcademicPeriodPersistenceMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.AcademicPeriodJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AcademicPeriodGatewayAdapter implements AcademicPeriodGatewayPort {

    private final AcademicPeriodJpaRepository periodoRepository;
    private final AcademicPeriodPersistenceMapper periodoMapper;

    public AcademicPeriodGatewayAdapter(
            AcademicPeriodJpaRepository periodoRepository,
            AcademicPeriodPersistenceMapper periodoMapper) {
        this.periodoRepository = periodoRepository;
        this.periodoMapper = periodoMapper;
    }

    @Override
    public Boolean finalizarProyeccion() {
        return true;
    }

    @Override
    public Boolean finalizarReporteGrupos() {
        return true;
    }

    @Override
    public List<AcademicPeriod> obtenerPeriodosAcademicos() {
        return periodoRepository.findAllOrderByFechaInicioDesc().stream()
                .map(periodoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<AcademicPeriod> obtenerPeriodosActivos() {
        return periodoRepository.findByEstadoOrderByFechaInicioDesc(AcademicPeriodStatus.ACTIVO).stream()
                .map(periodoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<AcademicPeriod> obtenerPeriodosCerrados() {
        return periodoRepository.findByEstadoOrderByFechaInicioDesc(AcademicPeriodStatus.CERRADO).stream()
                .map(periodoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<AcademicPeriod> obtenerPeriodosActivosYCerrados() {
        return periodoRepository.findActivosYCerradosOrderByFechaInicioDesc().stream()
                .map(periodoMapper::toDomain)
                .collect(Collectors.toList());
    }
}
