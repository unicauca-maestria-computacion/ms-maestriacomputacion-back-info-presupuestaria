package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "becas")
@Data
public class BecasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String dedicador;
    
    @Column(name = "entidad_asociada")
    private String entidadAsociada;
    
    private String tipo;
    
    private String titulo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_codigo")
    private EstudianteEntity estudiante;
}
