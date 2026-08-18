package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.AcademicPeriodGatewayPort;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias del caso de uso de periodos académicos.
 *
 * El caso de uso no incorpora reglas propias: su responsabilidad es delegar en
 * el puerto de salida. La prueba documenta esa delegación y protege frente a
 * un error frecuente en clases de este tipo, que es invocar el método
 * equivocado del puerto al añadir nuevas operaciones.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ManageAcademicPeriodUseCaseImpl - delegación en el puerto de salida")
class ManageAcademicPeriodUseCaseImplTest {

    @Mock
    private AcademicPeriodGatewayPort gateway;

    @InjectMocks
    private ManageAcademicPeriodUseCaseImpl useCase;

    private AcademicPeriod periodo(Long id, int tag, int anio, AcademicPeriodStatus estado) {
        AcademicPeriod p = new AcademicPeriod();
        p.setId(id);
        p.setTagPeriodo(tag);
        p.setAño(anio);
        p.setEstado(estado);
        p.setFechaInicio(LocalDate.of(anio, tag == 1 ? 1 : 8, 15));
        p.setFechaFin(LocalDate.of(anio, tag == 1 ? 6 : 12, 15));
        return p;
    }

    @Test
    @DisplayName("obtenerPeriodosAcademicos delega en el puerto")
    void obtenerPeriodosAcademicos_delegates() {
        when(gateway.obtenerPeriodosAcademicos())
                .thenReturn(List.of(periodo(1L, 1, 2024, AcademicPeriodStatus.ACTIVO)));

        assertThat(useCase.obtenerPeriodosAcademicos()).hasSize(1);
        verify(gateway).obtenerPeriodosAcademicos();
    }

    @Test
    @DisplayName("obtenerPeriodosActivos devuelve únicamente los periodos en estado ACTIVO")
    void obtenerPeriodosActivos_delegates() {
        when(gateway.obtenerPeriodosActivos())
                .thenReturn(List.of(periodo(1L, 1, 2024, AcademicPeriodStatus.ACTIVO)));

        List<AcademicPeriod> result = useCase.obtenerPeriodosActivos();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEstado()).isEqualTo(AcademicPeriodStatus.ACTIVO);
        verify(gateway).obtenerPeriodosActivos();
    }

    @Test
    @DisplayName("obtenerPeriodosCerrados devuelve únicamente los periodos en estado CERRADO")
    void obtenerPeriodosCerrados_delegates() {
        when(gateway.obtenerPeriodosCerrados())
                .thenReturn(List.of(periodo(2L, 2, 2023, AcademicPeriodStatus.CERRADO)));

        List<AcademicPeriod> result = useCase.obtenerPeriodosCerrados();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEstado()).isEqualTo(AcademicPeriodStatus.CERRADO);
        verify(gateway).obtenerPeriodosCerrados();
    }

    @Test
    @DisplayName("obtenerPeriodosActivosYCerrados combina ambos estados")
    void obtenerPeriodosActivosYCerrados_delegates() {
        when(gateway.obtenerPeriodosActivosYCerrados()).thenReturn(List.of(
                periodo(1L, 1, 2024, AcademicPeriodStatus.ACTIVO),
                periodo(2L, 2, 2023, AcademicPeriodStatus.CERRADO)));

        assertThat(useCase.obtenerPeriodosActivosYCerrados()).hasSize(2);
        verify(gateway).obtenerPeriodosActivosYCerrados();
    }

    @Test
    @DisplayName("finalizarProyeccion propaga el resultado del puerto")
    void finalizarProyeccion_propagatesResult() {
        when(gateway.finalizarProyeccion()).thenReturn(Boolean.TRUE);

        assertThat(useCase.finalizarProyeccion()).isTrue();
        verify(gateway).finalizarProyeccion();
    }

    @Test
    @DisplayName("finalizarReporteGrupos propaga el resultado del puerto")
    void finalizarReporteGrupos_propagatesResult() {
        when(gateway.finalizarReporteGrupos()).thenReturn(Boolean.FALSE);

        assertThat(useCase.finalizarReporteGrupos()).isFalse();
        verify(gateway).finalizarReporteGrupos();
    }

    @Test
    @DisplayName("Una lista vacía se propaga sin transformación")
    void emptyListIsPropagated() {
        when(gateway.obtenerPeriodosActivos()).thenReturn(List.of());

        assertThat(useCase.obtenerPeriodosActivos()).isEmpty();
    }

    // ------------------------------------------------------------------
    // Reglas de edición del propio modelo de dominio
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Un periodo ACTIVO es editable para el reporte")
    void activePeriodIsEditableForReport() {
        AcademicPeriod p = periodo(1L, 1, 2024, AcademicPeriodStatus.ACTIVO);

        assertThat(p.esEditableParaReporte()).isTrue();
    }

    @Test
    @DisplayName("Un periodo CERRADO cuya fecha de fin ya pasó no es editable")
    void closedAndExpiredPeriodIsNotEditable() {
        AcademicPeriod p = periodo(2L, 2, 2020, AcademicPeriodStatus.CERRADO);

        assertThat(p.esEditableParaReporte()).isFalse();
    }

    @Test
    @DisplayName("Un periodo no activo pero vigente sigue siendo editable para el reporte")
    void inactiveButCurrentPeriodIsStillEditable() {
        AcademicPeriod p = new AcademicPeriod();
        p.setEstado(AcademicPeriodStatus.INACTIVO);
        p.setFechaFin(LocalDate.now().plusDays(30));

        assertThat(p.esEditableParaReporte()).isTrue();
    }
}
