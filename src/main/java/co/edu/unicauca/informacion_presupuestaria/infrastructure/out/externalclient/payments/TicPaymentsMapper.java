package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.payments;

import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.ExternalBill;
import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.ExternalFee;
import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.ExternalPaymentInformation;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto.BillDto;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto.FeeDto;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto.PaymentsInformationDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component
public class TicPaymentsMapper {

    public ExternalPaymentInformation toDomain(PaymentsInformationDto dto) {
        if (dto == null) {
            return new ExternalPaymentInformation();
        }

        return new ExternalPaymentInformation(
                dto.getCodigo(),
                dto.getPagos() == null ? List.of() : dto.getPagos().stream().map(this::toDomain).toList());
    }

    public ExternalBill toDomain(BillDto dto) {
        ExternalBill bill = new ExternalBill();
        bill.setPeriod(dto.getPeriodo());
        bill.setCreationDate(parseDate(dto.getFecha_creacion()));
        bill.setDueDate(parseDate(dto.getFecha_vencimiento()));
        bill.setFullyPaid(dto.isPagadoTotalmente());
        bill.setState(dto.getEstado());
        bill.setFeeCount(dto.getNumero_cuotas());
        bill.setTotalAmount(toMoney(dto.getMonto_total()));
        bill.setPendingBalance(toMoney(dto.getSaldo_pendiente()));
        bill.setPaidAmount(toMoney(dto.getMonto_pagado()));
        bill.setFees(dto.getCuotas() == null ? List.of() : dto.getCuotas().stream().map(this::toDomain).toList());
        return bill;
    }

    public ExternalFee toDomain(FeeDto dto) {
        return new ExternalFee(
                toMoney(dto.getMonto()),
                toMoney(dto.getSaldo_pendiente()),
                parseDate(dto.getFecha_vencimiento()),
                dto.isPagadoTotalmente());
    }

    private BigDecimal toMoney(Long value) {
        return value == null ? BigDecimal.ZERO : BigDecimal.valueOf(value);
    }

    private LocalDate parseDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException ignored) {
            return null;
        }
    }
}
