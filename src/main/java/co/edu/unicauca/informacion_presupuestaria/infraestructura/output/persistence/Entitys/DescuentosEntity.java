package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "descuentos")
@Data
public class DescuentosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    
    @Column(name = "tipo_descuento")
    private String tipoDescuento;
    
    @Column(name = "num_acta_des")
    private String numActaDes;
    
    @Column(name = "fecha_acta_des")
    @Temporal(TemporalType.DATE)
    private Date fechaActaDes;
    
    @Column(name = "poliza")
    private String poliza;
    
    private String estado;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_codigo")
    private EstudianteEntity estudiante;
}

