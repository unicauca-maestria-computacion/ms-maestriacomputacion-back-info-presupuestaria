package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories;

import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.DescuentosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentosRepositoryInt extends JpaRepository<DescuentosEntity, Long> {
}
