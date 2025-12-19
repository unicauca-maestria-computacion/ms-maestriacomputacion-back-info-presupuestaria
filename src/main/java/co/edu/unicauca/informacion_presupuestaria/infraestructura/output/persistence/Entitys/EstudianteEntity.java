package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "estudiante")
@Data
public class EstudianteEntity {
    @Id
    @Column(name = "codigo")
    private Integer codigo;
    
    private String cohorte;
    
    @Column(name = "periodo_ingreso")
    private String periodoIngreso;
    
    @Column(name = "semestre_financiero")
    private Integer semestreFinanciero;
    
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MatriculaFinancieraEntity> matriculasFinancieras;
    
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DescuentosEntity> descuentos;
    
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BecasEntity> becas;
}

