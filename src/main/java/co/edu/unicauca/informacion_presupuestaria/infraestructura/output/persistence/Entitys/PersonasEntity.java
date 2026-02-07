package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Clase base para entidades de Personas
 * Utiliza estrategia JOINED para tener tablas separadas
 */
@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class PersonasEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "identificacion")
    private Integer identificacion;
    
    @Column(name = "apellido")
    private String apellido;
    
    @Column(name = "nombre")
    private String nombre;
}
