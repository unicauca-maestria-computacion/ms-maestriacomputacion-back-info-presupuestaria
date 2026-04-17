package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "grupo")
public class ResearchGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupParticipationEntity> participaciones;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public List<GroupParticipationEntity> getParticipaciones() { return participaciones; }
    public void setParticipaciones(List<GroupParticipationEntity> participaciones) { this.participaciones = participaciones; }
}
