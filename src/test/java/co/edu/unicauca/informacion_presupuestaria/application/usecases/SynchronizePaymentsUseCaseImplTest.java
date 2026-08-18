package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.ExternalBill;
import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.ExternalPaymentInformation;
import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.PaymentSyncCandidate;
import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.PaymentSynchronizationResult;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.ExternalPaymentsClientPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.PaymentSynchronizationGatewayPort;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias de la sincronización de pagos con el sistema externo.
 *
 * Las dos conexiones externas del caso de uso (el cliente HTTP del sistema de
 * pagos y el acceso a la base de datos) se sustituyen por dobles de Mockito,
 * de modo que la prueba se concentra en la lógica de coordinación: selección
 * del periodo activo, correspondencia entre la factura externa y el candidato
 * local, y contabilidad del resultado de la ejecución.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("SynchronizePaymentsUseCaseImpl - sincronización de pagos")
class SynchronizePaymentsUseCaseImplTest {

    @Mock
    private ExternalPaymentsClientPort externalPaymentsClient;

    @Mock
    private PaymentSynchronizationGatewayPort gateway;

    private SynchronizePaymentsUseCaseImpl useCase(boolean onlyPending) {
        return new SynchronizePaymentsUseCaseImpl(externalPaymentsClient, gateway, onlyPending);
    }

    private PaymentSyncCandidate candidate(String localCode, String externalCode, String periodo) {
        return new PaymentSyncCandidate(1L, 1L, 10L, localCode, externalCode, periodo);
    }

    private ExternalBill bill(String periodo, BigDecimal pendiente) {
        ExternalBill b = new ExternalBill();
        b.setPeriod(periodo);
        b.setPendingBalance(pendiente);
        b.setFullyPaid(pendiente != null && pendiente.compareTo(BigDecimal.ZERO) == 0);
        return b;
    }

    // ------------------------------------------------------------------

    @Test
    @DisplayName("Sin periodo académico activo no se consulta el sistema externo")
    void whenNoActivePeriod_doesNothing() {
        when(gateway.findLatestActiveAcademicPeriodId()).thenReturn(Optional.empty());

        PaymentSynchronizationResult result = useCase(true).synchronizePendingPayments();

        assertThat(result.getProcessed()).isZero();
        assertThat(result.getUpdated()).isZero();
        verifyNoInteractions(externalPaymentsClient);
        verify(gateway, never()).findCandidatesByAcademicPeriod(anyLong(), anyBoolean());
    }

    @Test
    @DisplayName("Sin candidatos el resultado queda en cero")
    void whenNoCandidates_resultIsEmpty() {
        when(gateway.findLatestActiveAcademicPeriodId()).thenReturn(Optional.of(10L));
        when(gateway.findCandidatesByAcademicPeriod(10L, true)).thenReturn(List.of());

        PaymentSynchronizationResult result = useCase(true).synchronizePendingPayments();

        assertThat(result.getProcessed()).isZero();
        verifyNoInteractions(externalPaymentsClient);
    }

    @Test
    @DisplayName("Una factura del mismo periodo actualiza el estado de pago")
    void whenBillMatchesPeriod_paymentStatusIsUpdated() {
        PaymentSyncCandidate candidato = candidate("EST001", "1061234567", "2024-1");
        ExternalBill factura = bill("2024-1", BigDecimal.ZERO);

        when(gateway.findLatestActiveAcademicPeriodId()).thenReturn(Optional.of(10L));
        when(gateway.findCandidatesByAcademicPeriod(10L, true)).thenReturn(List.of(candidato));
        when(externalPaymentsClient.findPaymentsByStudent(eq("1061234567"), any()))
                .thenReturn(new ExternalPaymentInformation("1061234567", List.of(factura)));

        PaymentSynchronizationResult result = useCase(true).synchronizePendingPayments();

        assertThat(result.getProcessed()).isEqualTo(1);
        assertThat(result.getUpdated()).isEqualTo(1);
        assertThat(result.getWithoutExternalData()).isZero();
        assertThat(result.getFailed()).isZero();
        verify(gateway).updatePaymentStatus(candidato, factura);
    }

    @Test
    @DisplayName("Si ninguna factura corresponde al periodo se contabiliza como sin datos externos")
    void whenNoBillMatchesPeriod_isCountedAsWithoutExternalData() {
        PaymentSyncCandidate candidato = candidate("EST001", "1061234567", "2024-1");

        when(gateway.findLatestActiveAcademicPeriodId()).thenReturn(Optional.of(10L));
        when(gateway.findCandidatesByAcademicPeriod(10L, true)).thenReturn(List.of(candidato));
        when(externalPaymentsClient.findPaymentsByStudent(any(), any()))
                .thenReturn(new ExternalPaymentInformation("1061234567",
                        List.of(bill("2023-2", BigDecimal.ZERO))));

        PaymentSynchronizationResult result = useCase(true).synchronizePendingPayments();

        assertThat(result.getProcessed()).isEqualTo(1);
        assertThat(result.getWithoutExternalData()).isEqualTo(1);
        assertThat(result.getUpdated()).isZero();
        verify(gateway, never()).updatePaymentStatus(any(), any());
    }

    @Test
    @DisplayName("Una respuesta nula del sistema externo se contabiliza como sin datos")
    void whenExternalResponseIsNull_isCountedAsWithoutExternalData() {
        when(gateway.findLatestActiveAcademicPeriodId()).thenReturn(Optional.of(10L));
        when(gateway.findCandidatesByAcademicPeriod(10L, true))
                .thenReturn(List.of(candidate("EST001", "1061234567", "2024-1")));
        when(externalPaymentsClient.findPaymentsByStudent(any(), any())).thenReturn(null);

        PaymentSynchronizationResult result = useCase(true).synchronizePendingPayments();

        assertThat(result.getWithoutExternalData()).isEqualTo(1);
        assertThat(result.getFailed()).isZero();
    }

    /**
     * El sistema externo entrega el periodo en un orden que puede estar
     * invertido. La normalización debe permitir la correspondencia.
     */
    @Test
    @DisplayName("El periodo de la factura se normaliza antes de compararlo")
    void billPeriodIsNormalizedBeforeMatching() {
        PaymentSyncCandidate candidato = candidate("EST001", "1061234567", "2024-1");
        ExternalBill factura = bill("1-2024", BigDecimal.ZERO);

        when(gateway.findLatestActiveAcademicPeriodId()).thenReturn(Optional.of(10L));
        when(gateway.findCandidatesByAcademicPeriod(10L, true)).thenReturn(List.of(candidato));
        when(externalPaymentsClient.findPaymentsByStudent(any(), any()))
                .thenReturn(new ExternalPaymentInformation("1061234567", List.of(factura)));

        PaymentSynchronizationResult result = useCase(true).synchronizePendingPayments();

        assertThat(result.getUpdated()).isEqualTo(1);
        verify(gateway).updatePaymentStatus(candidato, factura);
    }

    /**
     * El fallo de un estudiante no debe interrumpir la sincronización de los
     * demás: es la propiedad que hace utilizable la ejecución programada.
     */
    @Test
    @DisplayName("Un fallo aislado no interrumpe el procesamiento de los demás candidatos")
    void whenOneCandidateFails_theRestAreStillProcessed() {
        PaymentSyncCandidate falla = candidate("EST001", "111", "2024-1");
        PaymentSyncCandidate correcto = candidate("EST002", "222", "2024-1");
        ExternalBill factura = bill("2024-1", BigDecimal.ZERO);

        when(gateway.findLatestActiveAcademicPeriodId()).thenReturn(Optional.of(10L));
        when(gateway.findCandidatesByAcademicPeriod(10L, true)).thenReturn(List.of(falla, correcto));
        when(externalPaymentsClient.findPaymentsByStudent(eq("111"), any()))
                .thenThrow(new IllegalStateException("servicio externo no disponible"));
        when(externalPaymentsClient.findPaymentsByStudent(eq("222"), any()))
                .thenReturn(new ExternalPaymentInformation("222", List.of(factura)));

        PaymentSynchronizationResult result = useCase(true).synchronizePendingPayments();

        assertThat(result.getProcessed()).isEqualTo(2);
        assertThat(result.getFailed()).isEqualTo(1);
        assertThat(result.getUpdated()).isEqualTo(1);
        verify(gateway, times(1)).updatePaymentStatus(any(), any());
    }

    @Test
    @DisplayName("La bandera onlyPending se propaga a la consulta de candidatos")
    void onlyPendingFlagIsForwardedToTheGateway() {
        when(gateway.findLatestActiveAcademicPeriodId()).thenReturn(Optional.of(10L));
        when(gateway.findCandidatesByAcademicPeriod(10L, false)).thenReturn(List.of());

        useCase(false).synchronizePendingPayments();

        verify(gateway).findCandidatesByAcademicPeriod(10L, false);
    }

    @Test
    @DisplayName("El periodo del candidato se transmite al cliente externo")
    void candidatePeriodIsForwardedToTheExternalClient() {
        when(gateway.findLatestActiveAcademicPeriodId()).thenReturn(Optional.of(10L));
        when(gateway.findCandidatesByAcademicPeriod(anyLong(), anyBoolean()))
                .thenReturn(List.of(candidate("EST001", "1061234567", "2024-1")));
        when(externalPaymentsClient.findPaymentsByStudent(any(), any()))
                .thenReturn(new ExternalPaymentInformation("1061234567", List.of()));

        useCase(true).synchronizePendingPayments();

        @SuppressWarnings("unchecked")
        ArgumentCaptor<Optional<String>> captor = ArgumentCaptor.forClass(Optional.class);
        verify(externalPaymentsClient).findPaymentsByStudent(eq("1061234567"), captor.capture());
        assertThat(captor.getValue()).contains("2024-1");
    }

    @Test
    @DisplayName("Se procesan todos los candidatos devueltos por la consulta")
    void allCandidatesAreProcessed() {
        when(gateway.findLatestActiveAcademicPeriodId()).thenReturn(Optional.of(10L));
        when(gateway.findCandidatesByAcademicPeriod(anyLong(), anyBoolean())).thenReturn(List.of(
                candidate("EST001", "111", "2024-1"),
                candidate("EST002", "222", "2024-1"),
                candidate("EST003", "333", "2024-1")));
        when(externalPaymentsClient.findPaymentsByStudent(any(), any()))
                .thenReturn(new ExternalPaymentInformation("x", List.of()));

        PaymentSynchronizationResult result = useCase(true).synchronizePendingPayments();

        assertThat(result.getProcessed()).isEqualTo(3);
        assertThat(result.getWithoutExternalData()).isEqualTo(3);
    }
}
