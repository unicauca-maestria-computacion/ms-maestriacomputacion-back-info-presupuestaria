package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.external;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.ExternalPaymentsPort;
import co.edu.unicauca.informacion_presupuestaria.external.dto.BillDto;
import co.edu.unicauca.informacion_presupuestaria.external.dto.PaymentsInformationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Adaptador stub del servicio externo de pagos. Devuelve datos desde JSON en stubs/ o datos mínimos.
 * Activado cuando external.payments.mode=stub.
 */
@Component
@ConditionalOnProperty(name = "external.payments.mode", havingValue = "stub", matchIfMissing = true)
public class StubExternalPaymentsAdapter implements ExternalPaymentsPort {

    private static final Logger log = LoggerFactory.getLogger(StubExternalPaymentsAdapter.class);
    private static final String STUB_EMPTY = "stubs/payments_empty.json";
    private static final String STUB_SUCCESS = "stubs/payments_success.json";
    private static final String STUB_PARTIAL = "stubs/payments_partial.json";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Optional<PaymentsInformationDto> getPayments(String codigoEstudiante, Optional<String> periodo) {
        if (codigoEstudiante == null || codigoEstudiante.isBlank()) {
            return Optional.empty();
        }
        try {
            PaymentsInformationDto fromFile = loadFromStub(codigoEstudiante);
            if (fromFile != null) {
                return Optional.of(fromFile);
            }
            return Optional.of(buildMinimalResponse(codigoEstudiante));
        } catch (Exception e) {
            log.warn("Stub: error simulando respuesta para estudiante {}: {}", codigoEstudiante, e.getMessage());
            return Optional.empty();
        }
    }

    private PaymentsInformationDto loadFromStub(String codigoEstudiante) {
        String path = chooseStubPath(codigoEstudiante);
        try (InputStream is = new ClassPathResource(path).getInputStream()) {
            PaymentsInformationDto dto = objectMapper.readValue(is, PaymentsInformationDto.class);
            if (dto != null && dto.getCodigo() == null) {
                dto.setCodigo(codigoEstudiante);
            }
            return dto;
        } catch (Exception e) {
            return null;
        }
    }

    private String chooseStubPath(String codigoEstudiante) {
        if ("0".equals(codigoEstudiante) || "empty".equalsIgnoreCase(codigoEstudiante)) {
            return STUB_EMPTY;
        }
        if ("partial".equalsIgnoreCase(codigoEstudiante)) {
            return STUB_PARTIAL;
        }
        return STUB_SUCCESS;
    }

    private PaymentsInformationDto buildMinimalResponse(String codigoEstudiante) {
        PaymentsInformationDto dto = new PaymentsInformationDto();
        dto.setCodigo(codigoEstudiante);
        BillDto bill = new BillDto();
        bill.setPeriodo("1-2024");
        bill.setPagadoTotalmente(true);
        bill.setNumero_cuotas(1);
        bill.setMonto_total(5_000_000L);
        bill.setMonto_pagado(5_000_000L);
        bill.setSaldo_pendiente(0L);
        bill.setEstado("ca");
        bill.setCuotas(new ArrayList<>());
        dto.setPagos(List.of(bill));
        return dto;
    }
}
