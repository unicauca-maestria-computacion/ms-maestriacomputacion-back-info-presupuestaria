package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pago_sincronizado", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"codigo_estudiante", "periodo"})
})
@Data
public class PagoSincronizadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_estudiante", nullable = false)
    private Long codigoEstudiante;

    @Column(nullable = false, length = 20)
    private String periodo;

    @Column(name = "valor_total")
    private Float valorTotal;

    @Column(name = "valor_pagado")
    private Float valorPagado;

    @Column(name = "saldo_pendiente")
    private Float saldoPendiente;

    @Column(name = "pagado_totalidad")
    private Boolean pagadoEnTotalidad;

    @Column(name = "numero_cuotas")
    private Integer numeroCuotas;

    @Column(name = "cuotas_pagadas")
    private Integer cuotasPagadas;

    @Column(length = 5)
    private String estado;
}
