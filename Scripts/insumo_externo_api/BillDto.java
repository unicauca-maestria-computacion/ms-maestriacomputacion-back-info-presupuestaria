package co.edu.unicauca.kira.api.rest.dto.pagos;

import java.util.List;

import lombok.Data;

/**
 * @author Santiago Fernandez
 * Guarda la informacion de una factura
 */
@Data
public class BillDto {

    private String periodo;
    private String fecha_creacion;
    private String fecha_vencimiento;
    private boolean pagadoTotalmente;
    // State of the bill (an - anulada,ca - cancelada,ac-activa)
    private String estado;
    private int numero_cuotas;
    private Long monto_total;
    private Long saldo_pendiente;
    private Long monto_pagado;

    private List<FeeDto> cuotas;
}
