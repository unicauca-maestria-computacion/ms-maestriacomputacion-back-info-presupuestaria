package co.edu.unicauca.informacion_presupuestaria.domain.model;

import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentfinancialreport.dtoResponse.PeriodoAcademicoResponseDto;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentfinancialreport.dtoResponse.ProyeccionEstudianteResponse;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentfinancialreport.mapper.PeriodoAcademicoRestMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentfinancialreport.mapper.ProyeccionEstudianteRestMapper;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.BigRange;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.StringLength;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Property-based tests for REST mapper round-trips.
 *
 * **Validates: Requirements 3.5**
 */
class MapperRoundTripPropertyTest {

    private final PeriodoAcademicoRestMapper periodoMapper = new PeriodoAcademicoRestMapper();
    private final ProyeccionEstudianteRestMapper proyeccionMapper =
            new ProyeccionEstudianteRestMapper(periodoMapper);

    /**
     * Property 3: round-trip de mappers REST preserva datos — StudentProjection.
     *
     * domain → response DTO preserves relevant fields.
     *
     * **Validates: Requirements 3.5**
     */
    @Property(tries = 200)
    @Label("Feature: hexagonal-refactoring, Property 3: round-trip mapper HTTP preserva datos")
    void proyeccionEstudianteRoundTripPreservaDatos(
            @ForAll @StringLength(min = 1, max = 20) String codigo,
            @ForAll @StringLength(min = 1, max = 30) String nombre,
            @ForAll @StringLength(min = 1, max = 30) String apellido,
            @ForAll boolean estaPago,
            @ForAll @BigRange(min = "0.00", max = "1.00") BigDecimal porcentajeBeca,
            @ForAll @BigRange(min = "0.00", max = "1.00") BigDecimal porcentajeVotacion,
            @ForAll @BigRange(min = "0.00", max = "1.00") BigDecimal porcentajeEgresado) {

        // Arrange — build domain model
        StudentProjection original = new StudentProjection();
        original.setCodigoEstudiante(codigo);
        original.setNombre(nombre);
        original.setApellido(apellido);
        original.setEstaPago(estaPago);
        original.setPorcentajeBeca(porcentajeBeca);
        original.setAplicaVotacion(porcentajeVotacion != null && porcentajeVotacion.compareTo(BigDecimal.ZERO) > 0);
        original.setAplicaEgresado(porcentajeEgresado != null && porcentajeEgresado.compareTo(BigDecimal.ZERO) > 0);

        // Act — domain → response DTO
        ProyeccionEstudianteResponse response = proyeccionMapper.toResponse(original);

        // Assert — relevant fields preserved in response
        assertThat(response.getCodigoEstudiante()).isEqualTo(original.getCodigoEstudiante());
        assertThat(response.getNombre()).isEqualTo(original.getNombre());
        assertThat(response.getApellido()).isEqualTo(original.getApellido());
        assertThat(response.getEstaPago()).isEqualTo(original.getEstaPago());
        assertThat(response.getPorcentajeBeca()).isEqualByComparingTo(original.getPorcentajeBeca());
        assertThat(response.getAplicaVotacion()).isEqualTo(original.getAplicaVotacion());
        assertThat(response.getAplicaEgresado()).isEqualTo(original.getAplicaEgresado());
    }

    /**
     * Property 3: round-trip de mappers REST preserva datos — AcademicPeriod.
     *
     * **Validates: Requirements 3.5**
     */
    @Property(tries = 200)
    @Label("Feature: hexagonal-refactoring, Property 3: round-trip mapper HTTP preserva datos")
    void periodoAcademicoRoundTripPreservaDatos(
            @ForAll @IntRange(min = 1, max = 2) Integer tagPeriodo,
            @ForAll @IntRange(min = 2000, max = 2099) Integer anio) {

        // Arrange — build domain model
        AcademicPeriod original = new AcademicPeriod(
                1L, tagPeriodo, anio,
                LocalDate.of(anio, 1, 15),
                LocalDate.of(anio, 6, 30),
                LocalDate.of(anio, 12, 31),
                "Período " + anio + "-" + tagPeriodo,
                AcademicPeriodStatus.ACTIVO);

        // Act — domain → response DTO
        PeriodoAcademicoResponseDto response = periodoMapper.toResponse(original);

        // Assert — relevant fields preserved
        assertThat(response.getTagPeriodo()).isEqualTo(original.getTagPeriodo());
        assertThat(response.getAnio()).isEqualTo(original.getAño());
        assertThat(response.getActivo()).isTrue();
        assertThat(response.getEstado()).isEqualTo("ACTIVO");
    }
}
