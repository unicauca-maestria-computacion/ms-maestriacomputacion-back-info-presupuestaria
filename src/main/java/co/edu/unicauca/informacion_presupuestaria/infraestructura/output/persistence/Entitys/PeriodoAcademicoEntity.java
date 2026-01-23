package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "periodo_academico")
public class PeriodoAcademicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Integer periodo;
    
    @Column(name = "anio", nullable = false)
    private Integer año;
    
    @OneToMany(mappedBy = "objPeriodoAcademico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MatriculaFinancieraEntity> matriculasFinancieras;
    
    @OneToOne(mappedBy = "objPeriodoAcademico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ConfiguracionReporteFinancieroEntity objConfiguracionReporteFinanciero;
    
    @OneToOne(mappedBy = "objPeriodoAcademico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ConfiguracionReporteGruposEntity objConfiguracionReporteGrupo;
    
    @OneToOne(mappedBy = "objPeriodoAcademico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ProyeccionEstudianteEntity objProyeccionEstudiante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public List<MatriculaFinancieraEntity> getMatriculasFinancieras() {
        return matriculasFinancieras;
    }

    public void setMatriculasFinancieras(List<MatriculaFinancieraEntity> matriculasFinancieras) {
        this.matriculasFinancieras = matriculasFinancieras;
    }

    public ConfiguracionReporteFinancieroEntity getObjConfiguracionReporteFinanciero() {
        return objConfiguracionReporteFinanciero;
    }

    public void setObjConfiguracionReporteFinanciero(ConfiguracionReporteFinancieroEntity objConfiguracionReporteFinanciero) {
        this.objConfiguracionReporteFinanciero = objConfiguracionReporteFinanciero;
    }

    public ConfiguracionReporteGruposEntity getObjConfiguracionReporteGrupo() {
        return objConfiguracionReporteGrupo;
    }

    public void setObjConfiguracionReporteGrupo(ConfiguracionReporteGruposEntity objConfiguracionReporteGrupo) {
        this.objConfiguracionReporteGrupo = objConfiguracionReporteGrupo;
    }

    public ProyeccionEstudianteEntity getObjProyeccionEstudiante() {
        return objProyeccionEstudiante;
    }

    public void setObjProyeccionEstudiante(ProyeccionEstudianteEntity objProyeccionEstudiante) {
        this.objProyeccionEstudiante = objProyeccionEstudiante;
    }
}

