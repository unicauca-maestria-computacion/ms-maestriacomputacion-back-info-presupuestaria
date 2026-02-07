package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ConfiguracionReporteFinancieroEntity;

import java.util.Optional;

@Repository
public interface ConfiguracionReporteFinancieroRepositoryInt extends JpaRepository<ConfiguracionReporteFinancieroEntity, Long> {
    Optional<ConfiguracionReporteFinancieroEntity> findByObjPeriodoAcademicoId(Long periodoAcademicoId);
    
    @Modifying
    @Query(value = "UPDATE configuracion_reporte_financiero SET " +
           "biblioteca = :biblioteca, " +
           "recursos_computacionales = :recursosComputacionales, " +
           "valor_matricula = :valorMatricula, " +
           "valor_smlv = :valorSmlv, " +
           "total_neto = :totalNeto, " +
           "total_descuentos = :totalDescuentos, " +
           "total_ingresos = :totalIngresos " +
           "WHERE id = :id", nativeQuery = true)
    int actualizarConfiguracionPorId(
        @Param("id") Long id,
        @Param("biblioteca") Float biblioteca,
        @Param("recursosComputacionales") Float recursosComputacionales,
        @Param("valorMatricula") Float valorMatricula,
        @Param("valorSmlv") Float valorSmlv,
        @Param("totalNeto") Float totalNeto,
        @Param("totalDescuentos") Float totalDescuentos,
        @Param("totalIngresos") Float totalIngresos
    );
    
    @Query(value = "SELECT periodo_academico_id FROM configuracion_reporte_financiero WHERE id = :id", nativeQuery = true)
    Long obtenerPeriodoAcademicoId(@Param("id") Long id);
    
    @Query(value = "SELECT es_reporte_final FROM configuracion_reporte_financiero WHERE id = :id", nativeQuery = true)
    Boolean obtenerEsReporteFinal(@Param("id") Long id);
}

