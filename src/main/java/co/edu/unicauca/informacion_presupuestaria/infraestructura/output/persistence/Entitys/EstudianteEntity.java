package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table(name = "estudiante")
@PrimaryKeyJoinColumn(name = "persona_id", referencedColumnName = "id")
@Data
@EqualsAndHashCode(callSuper = true)
public class EstudianteEntity extends PersonasEntity {
    
    @Column(name = "codigo", unique = true, nullable = false, length = 20)
    private String codigo;
    
    private String cohorte;
    
    @Column(name = "periodo_ingreso")
    private String periodoIngreso;
    
    @Column(name = "semestre_financiero")
    private Integer semestreFinanciero;
    
    @OneToMany(mappedBy = "objEstudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MatriculaFinancieraEntity> matriculasFinancieras;
    
    @OneToMany(mappedBy = "objEstudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DescuentosEntity> descuentos;
    
    @OneToMany(mappedBy = "objEstudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BecasEntity> becas;
}

