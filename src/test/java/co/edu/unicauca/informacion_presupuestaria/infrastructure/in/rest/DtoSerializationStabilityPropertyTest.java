package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest;

import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoResponse.ProyeccionEstudianteResponse;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentfinancialreport.dtoResponse.ReporteEstudiantesResponse;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentfinancialreport.dtoResponse.ConfiguracionReporteFinancieroResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.BigRange;
import net.jqwik.api.constraints.StringLength;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Property 4: JSON serialization of response DTOs is stable across the migration.
 * Validates: Requirements 4.1–4.17, 4.18
 */
class DtoSerializationStabilityPropertyTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * ProyeccionEstudianteResponse preserves Spanish-style camelCase field names in JSON.
     * The frontend depends on: codigoEstudiante, estadoProyeccion, estaPago, etc.
     */
    @Property(tries = 100)
    void proyeccionEstudianteResponsePreservesJsonFieldNames(
            @ForAll @StringLength(min = 1, max = 20) String codigo,
            @ForAll boolean estaPago) throws Exception {

        ProyeccionEstudianteResponse dto = new ProyeccionEstudianteResponse();
        dto.setCodigoEstudiante(codigo);
        dto.setEstaPago(estaPago);
        dto.setEstadoProyeccion("PROYECCION");

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"codigoEstudiante\"");
        assertThat(json).contains("\"estaPago\"");
        assertThat(json).contains("\"estadoProyeccion\"");
    }

    /**
     * ConfiguracionReporteFinancieroResponse preserves field names in JSON.
     */
    @Property(tries = 100)
    void configuracionReporteFinancieroResponsePreservesJsonFieldNames(
            @ForAll @BigRange(min = "0.01", max = "5000000") BigDecimal valorSMLV) throws Exception {

        ConfiguracionReporteFinancieroResponse dto = new ConfiguracionReporteFinancieroResponse();
        dto.setValorSMLV(valorSMLV);
        dto.setEsReporteFinal(false);

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"valorSMLV\"");
        assertThat(json).contains("\"esReporteFinal\"");
    }

    /**
     * ReporteEstudiantesResponse preserves field names in JSON.
     */
    @Property(tries = 100)
    void reporteEstudiantesResponsePreservesJsonFieldNames(
            @ForAll @BigRange(min = "0", max = "100000000") BigDecimal totalNeto) throws Exception {

        ReporteEstudiantesResponse dto = new ReporteEstudiantesResponse();
        dto.setTotalNeto(totalNeto);

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"totalNeto\"");
    }
}
