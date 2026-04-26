package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient;

import co.edu.unicauca.informacion_presupuestaria.domain.model.BecaDescuentoInfo;
import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.domain.model.SubjectResponse;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto.BecaDescuentoInfoResponse;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto.PeriodoAcademicoResponse;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto.StudentResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FinancialEnrollmentClientMapper {

    public BecaDescuentoInfo toDomain(BecaDescuentoInfoResponse dto) {
        if (dto == null) return null;
        return new BecaDescuentoInfo(
                dto.getTipo(),
                dto.getPorcentaje(),
                dto.getResolucion(),
                dto.getEstado(),
                dto.getAvaladoConcejo()
        );
    }

    public Student toDomain(StudentResponse response) {
        if (response == null) {
            return null;
        }

        List<SubjectResponse> materias = response.getMaterias() != null
                ? response.getMaterias().stream()
                        .map(m -> new SubjectResponse(m.getCodigo(), m.getNombre(), m.getCreditos()))
                        .collect(Collectors.toList())
                : Collections.emptyList();

        List<BecaDescuentoInfo> becasDescuentos = response.getBecasDescuentos() != null
                ? response.getBecasDescuentos().stream()
                        .map(this::toDomain)
                        .collect(Collectors.toList())
                : Collections.emptyList();

        return new Student(
                response.getCodigo(),
                response.getNombre(),
                response.getApellido(),
                response.getIdentificacion(),
                response.getCohorte(),
                response.getPeriodoIngreso(),
                response.getSemestreFinanciero(),
                response.getSemestreAcademico(),
                response.getValorEnSMLV(),
                response.getEsEgresadoUnicauca(),
                response.getAplicaVotacion(),
                materias,
                becasDescuentos,
                response.getEstaPago(),
                response.getGrupoNombre());
    }

    public List<Student> toDomainList(List<StudentResponse> responses) {
        if (responses == null) {
            return Collections.emptyList();
        }
        return responses.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    public AcademicPeriod toDomain(PeriodoAcademicoResponse response) {
        if (response == null) {
            return null;
        }

        Integer año = response.getFechaInicio() != null
                ? response.getFechaInicio().getYear()
                : null;

        AcademicPeriodStatus estado = null;
        if (response.getEstado() != null) {
            try {
                estado = AcademicPeriodStatus.valueOf(response.getEstado());
            } catch (IllegalArgumentException e) {
                estado = AcademicPeriodStatus.INACTIVO;
            }
        }

        return new AcademicPeriod(
                response.getId(),
                response.getTagPeriodo(),
                año,
                response.getFechaInicio(),
                response.getFechaFin(),
                response.getFechaFinMatricula(),
                response.getDescripcion(),
                estado);
    }

    public List<AcademicPeriod> toDomainPeriodoList(List<PeriodoAcademicoResponse> responses) {
        if (responses == null) {
            return Collections.emptyList();
        }
        return responses.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}
