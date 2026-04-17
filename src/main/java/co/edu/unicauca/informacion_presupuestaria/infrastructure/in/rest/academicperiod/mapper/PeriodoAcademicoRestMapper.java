package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.academicperiod.mapper;

import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.academicperiod.dtoResponse.PeriodoAcademicoResponseDto;
import org.springframework.stereotype.Component;

@Component("academicPeriodPeriodoMapper")
public class PeriodoAcademicoRestMapper {

    public PeriodoAcademicoResponseDto toResponse(AcademicPeriod periodo) {
        if (periodo == null) {
            return null;
        }

        PeriodoAcademicoResponseDto dto = new PeriodoAcademicoResponseDto();
        dto.setId(periodo.getId());
        dto.setTagPeriodo(periodo.getTagPeriodo());
        dto.setPeriodo(periodo.getTagPeriodo());
        dto.setAnio(periodo.getAño());
        dto.setFechaInicio(periodo.getFechaInicio());
        dto.setFechaFin(periodo.getFechaFin());
        dto.setFechaFinMatricula(periodo.getFechaFinMatricula());
        dto.setDescripcion(periodo.getDescripcion());

        if (periodo.getEstado() != null) {
            String estadoStr = periodo.getEstado().name();
            dto.setEstado(estadoStr);
            dto.setActivo("ACTIVO".equals(estadoStr));
        }

        return dto;
    }
}
