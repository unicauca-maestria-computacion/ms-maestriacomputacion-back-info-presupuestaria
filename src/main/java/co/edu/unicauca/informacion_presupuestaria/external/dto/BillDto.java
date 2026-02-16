package co.edu.unicauca.informacion_presupuestaria.external.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO del servicio externo (SIMCA/CINCA). Información de una factura.
 * Estado: an - anulada, ca - cancelada, ac - activa.
 */
@Data
public class BillDto {
    private String periodo;
    private String fecha_creacion;
    private String fecha_vencimiento;
    private boolean pagadoTotalmente;
    private String estado;
    private int numero_cuotas;
    private Long monto_total;
    private Long saldo_pendiente;
    private Long monto_pagado;
    private List<FeeDto> cuotas;
}
