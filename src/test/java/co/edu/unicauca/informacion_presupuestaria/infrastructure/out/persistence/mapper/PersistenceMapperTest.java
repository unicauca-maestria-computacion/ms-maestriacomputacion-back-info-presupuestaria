package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper;

import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.StudentProjectionEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.StudentEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.PersonEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PersistenceMapperTest {

    private StudentProjectionPersistenceMapper proyeccionMapper;
    private AcademicPeriodPersistenceMapper periodoMapper;

    @BeforeEach
    void setUp() {
        periodoMapper = new AcademicPeriodPersistenceMapper();
        proyeccionMapper = new StudentProjectionPersistenceMapper(periodoMapper);
    }

    @Test
    void shouldMapEntityToDomainCorrectly() {
        StudentEntity student = new StudentEntity();
        student.setCodigo("EST001");
        PersonEntity person = new PersonEntity();
        person.setNombre("Juan");
        person.setApellido("Perez");
        person.setIdentificacion(12345L);
        student.setObjPersona(person);

        StudentProjectionEntity entity = new StudentProjectionEntity();
        entity.setId(100L);
        entity.setObjEstudiante(student);
        entity.setEstaPago(true);
        entity.setPorcentajeBeca(BigDecimal.ZERO);
        entity.setAplicaVotacion(false);
        entity.setAplicaEgresado(false);

        StudentProjection domain = proyeccionMapper.toDomain(entity);

        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(100L);
        assertThat(domain.getCodigoEstudiante()).isEqualTo("EST001");
        assertThat(domain.getNombre()).isEqualTo("Juan");
        assertThat(domain.getApellido()).isEqualTo("Perez");
        assertThat(domain.getIdentificacion()).isEqualTo(12345L);
        assertThat(domain.getEstaPago()).isTrue();
    }

    @Test
    void shouldMapDomainToEntityCorrectly() {
        StudentProjection domain = new StudentProjection();
        domain.setId(100L);
        domain.setEstaPago(true);
        domain.setPorcentajeBeca(new BigDecimal("0.50"));
        domain.setAplicaVotacion(true);
        domain.setAplicaEgresado(false);

        StudentProjectionEntity entity = proyeccionMapper.toEntity(domain);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(100L);
        assertThat(entity.getEstaPago()).isTrue();
        assertThat(entity.getPorcentajeBeca()).isEqualByComparingTo(new BigDecimal("0.50"));
        assertThat(entity.getAplicaVotacion()).isTrue();
        assertThat(entity.getAplicaEgresado()).isFalse();
    }
}
