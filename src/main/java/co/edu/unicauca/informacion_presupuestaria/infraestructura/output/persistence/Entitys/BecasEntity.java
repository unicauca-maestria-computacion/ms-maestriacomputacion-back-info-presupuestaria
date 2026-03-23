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

    @Column(name = "dedicacion")
    private String dedicacion;

    @Column(name = "entidad_asociada")
    private String entidadAsociada;

    @Column(name = "es_ofrecida_por_unicauca")
    private String esOfrecidaPorUnicauca;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "titulo")
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estudiante", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private EstudianteEntity objEstudiante;
}
