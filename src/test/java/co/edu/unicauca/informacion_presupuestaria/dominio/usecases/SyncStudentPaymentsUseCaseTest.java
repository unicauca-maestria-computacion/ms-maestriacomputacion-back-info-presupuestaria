package co.edu.unicauca.informacion_presupuestaria.dominio.usecases;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.ExternalPaymentsPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.StudentPaymentsRepositoryPort;
import co.edu.unicauca.informacion_presupuestaria.external.dto.BillDto;
import co.edu.unicauca.informacion_presupuestaria.external.dto.PaymentsInformationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class SyncStudentPaymentsUseCaseTest {

    private ExternalPaymentsPort externalPaymentsPort;
    private StudentPaymentsRepositoryPort studentPaymentsRepositoryPort;
    private SyncStudentPaymentsUseCase useCase;

    @BeforeEach
    void setUp() {
        externalPaymentsPort = mock(ExternalPaymentsPort.class);
        studentPaymentsRepositoryPort = mock(StudentPaymentsRepositoryPort.class);
        useCase = new SyncStudentPaymentsUseCase(externalPaymentsPort, studentPaymentsRepositoryPort);
    }

    @Test
    void cuandoCodigoVacio_retornaFalseYNoLlamaExterno() {
        boolean result = useCase.syncPayments("", Optional.empty());
        assertFalse(result);
        verify(externalPaymentsPort, never()).getPayments(any(), any());
        verify(studentPaymentsRepositoryPort, never()).upsertStudentPayments(any());
    }

    @Test
    void cuandoExternoRetornaVacio_noPersisteYRetornaFalse() {
        when(externalPaymentsPort.getPayments(eq("123"), any())).thenReturn(Optional.empty());
        boolean result = useCase.syncPayments("123", Optional.of("1-2024"));
        assertFalse(result);
        verify(studentPaymentsRepositoryPort, never()).upsertStudentPayments(any());
    }

    @Test
    void cuandoExternoRetornaPagos_mapeaYPersiste() {
        BillDto bill = new BillDto();
        bill.setPeriodo("1-2024");
        bill.setMonto_total(5_000_000L);
        bill.setMonto_pagado(5_000_000L);
        bill.setPagadoTotalmente(true);
        bill.setNumero_cuotas(1);
        bill.setCuotas(List.of());
        PaymentsInformationDto dto = new PaymentsInformationDto();
        dto.setCodigo("123");
        dto.setPagos(List.of(bill));
        when(externalPaymentsPort.getPayments(eq("123"), any())).thenReturn(Optional.of(dto));

        boolean result = useCase.syncPayments("123", Optional.empty());

        assertTrue(result);
        verify(studentPaymentsRepositoryPort, times(1)).upsertStudentPayments(argThat(p -> {
            assertEquals(123L, p.getCodigoEstudiante());
            assertNotNull(p.getPagos());
            assertEquals(1, p.getPagos().size());
            assertEquals("1-2024", p.getPagos().get(0).getPeriodoPagado());
            return true;
        }));
    }

    @Test
    void cuandoExternoRetornaListaVacia_retornaTrueSinPersistir() {
        PaymentsInformationDto dto = new PaymentsInformationDto();
        dto.setCodigo("456");
        dto.setPagos(List.of());
        when(externalPaymentsPort.getPayments(eq("456"), any())).thenReturn(Optional.of(dto));

        boolean result = useCase.syncPayments("456", Optional.empty());

        assertTrue(result);
        verify(studentPaymentsRepositoryPort, never()).upsertStudentPayments(any());
    }
}
