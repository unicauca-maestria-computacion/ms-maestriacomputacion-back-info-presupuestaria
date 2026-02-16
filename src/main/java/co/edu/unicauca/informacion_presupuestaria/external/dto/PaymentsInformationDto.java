package co.edu.unicauca.informacion_presupuestaria.external.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO del servicio externo (SIMCA/CINCA). Información de pagos de un estudiante.
 */
@Data
public class PaymentsInformationDto {
    private String codigo;
    private List<BillDto> pagos;
}
