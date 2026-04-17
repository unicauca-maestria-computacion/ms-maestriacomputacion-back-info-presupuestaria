package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaymentsInformationDto {
    private String codigo;
    private List<BillDto> pagos;
}
