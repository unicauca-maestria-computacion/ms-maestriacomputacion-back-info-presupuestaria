package co.edu.unicauca.informacion_presupuestaria.dominio.usecases;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.input.SyncStudentPaymentsInputPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.ExternalPaymentsPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.StudentPaymentsRepositoryPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Pago;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PagosEstudiante;
import co.edu.unicauca.informacion_presupuestaria.external.dto.BillDto;
import co.edu.unicauca.informacion_presupuestaria.external.dto.FeeDto;
import co.edu.unicauca.informacion_presupuestaria.external.dto.PaymentsInformationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Caso de uso: sincronizar pagos de un estudiante desde el servicio externo y persistir.
 * No depende de HTTP ni de BD concreta; usa puertos.
 */
public class SyncStudentPaymentsUseCase implements SyncStudentPaymentsInputPort {

    private static final Logger log = LoggerFactory.getLogger(SyncStudentPaymentsUseCase.class);

    private final ExternalPaymentsPort externalPaymentsPort;
    private final StudentPaymentsRepositoryPort studentPaymentsRepositoryPort;

    public SyncStudentPaymentsUseCase(
            ExternalPaymentsPort externalPaymentsPort,
            StudentPaymentsRepositoryPort studentPaymentsRepositoryPort) {
        this.externalPaymentsPort = externalPaymentsPort;
        this.studentPaymentsRepositoryPort = studentPaymentsRepositoryPort;
    }

    @Override
    public boolean syncPayments(String codigoEstudiante, Optional<String> periodo) {
        if (codigoEstudiante == null || codigoEstudiante.isBlank()) {
            log.warn("syncPayments: codigoEstudiante vacío");
            return false;
        }
        try {
            Optional<PaymentsInformationDto> dtoOpt = externalPaymentsPort.getPayments(codigoEstudiante, periodo);
            if (dtoOpt.isEmpty()) {
                log.debug("Sin datos de pagos para estudiante {}", codigoEstudiante);
                return false;
            }
            PagosEstudiante domain = mapToDomain(dtoOpt.get());
            if (domain.getPagos() == null || domain.getPagos().isEmpty()) {
                log.debug("Lista de pagos vacía para estudiante {}", codigoEstudiante);
                return true;
            }
            studentPaymentsRepositoryPort.upsertStudentPayments(domain);
            log.debug("Sincronizados {} pagos para estudiante {}", domain.getPagos().size(), codigoEstudiante);
            return true;
        } catch (Exception e) {
            log.error("Error sincronizando pagos para estudiante {}: {}", codigoEstudiante, e.getMessage());
            return false;
        }
    }

    private PagosEstudiante mapToDomain(PaymentsInformationDto dto) {
        Long codigo = parseCodigo(dto.getCodigo());
        PagosEstudiante pagosEstudiante = new PagosEstudiante();
        pagosEstudiante.setCodigoEstudiante(codigo != null ? codigo : 0L);
        List<Pago> pagos = new ArrayList<>();
        if (dto.getPagos() != null) {
            for (BillDto bill : dto.getPagos()) {
                Pago pago = mapBillToPago(bill);
                if (pago != null) {
                    pagos.add(pago);
                }
            }
        }
        pagosEstudiante.setPagos(pagos);
        return pagosEstudiante;
    }

    private Long parseCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return null;
        }
        try {
            return Long.parseLong(codigo.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Pago mapBillToPago(BillDto bill) {
        if (bill == null) {
            return null;
        }
        Pago pago = new Pago();
        pago.setPeriodoPagado(bill.getPeriodo());
        pago.setValorTotal(bill.getMonto_total() != null ? bill.getMonto_total().floatValue() : null);
        pago.setValorPagado(bill.getMonto_pagado() != null ? bill.getMonto_pagado().floatValue() : null);
        pago.setPagadoEnTotalidad(bill.isPagadoTotalmente());
        boolean tieneCuotas = bill.getNumero_cuotas() > 1 || (bill.getCuotas() != null && !bill.getCuotas().isEmpty());
        pago.setTieneCuotas(tieneCuotas);
        pago.setNumCuotasTotales(bill.getNumero_cuotas() > 0);
        int cuotasPagadas = countCuotasPagadas(bill.getCuotas());
        pago.setNumCuotasPagadas((float) cuotasPagadas);
        return pago;
    }

    private int countCuotasPagadas(List<FeeDto> cuotas) {
        if (cuotas == null) {
            return 0;
        }
        int count = 0;
        for (FeeDto fee : cuotas) {
            if (fee != null && fee.isPagadoTotalmente()) {
                count++;
            }
        }
        return count;
    }
}
