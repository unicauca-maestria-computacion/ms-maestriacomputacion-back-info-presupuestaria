package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper;

import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.FinancialReportConfigEntity;
import org.springframework.stereotype.Component;

@Component
public class FinancialReportConfigPersistenceMapper {

    private final AcademicPeriodPersistenceMapper periodoMapper;

    public FinancialReportConfigPersistenceMapper(AcademicPeriodPersistenceMapper periodoMapper) {
        this.periodoMapper = periodoMapper;
    }

    public FinancialReportConfig toDomain(FinancialReportConfigEntity entity) {
        if (entity == null) {
            return null;
        }
        FinancialReportConfig domain = new FinancialReportConfig();
        domain.setId(entity.getId());
        domain.setBiblioteca(entity.getBiblioteca());
        domain.setRecursosComputacionales(entity.getRecursosComputacionales());
        domain.setValorSMLV(entity.getValorSMLV());
        domain.setEsReporteFinal(entity.getEsReporteFinal());
        domain.setPorcentajeVotacionFijo(entity.getPorcentajeVotacionFijo());
        domain.setPorcentajeEgresadoFijo(entity.getPorcentajeEgresadoFijo());
        if (entity.getObjPeriodoAcademico() != null) {
            domain.setAcademicPeriod(periodoMapper.toDomain(entity.getObjPeriodoAcademico()));
        }
        return domain;
    }

    public FinancialReportConfigEntity toEntity(FinancialReportConfig domain) {
        if (domain == null) {
            return null;
        }
        FinancialReportConfigEntity entity = new FinancialReportConfigEntity();
        entity.setId(domain.getId());
        entity.setBiblioteca(domain.getBiblioteca());
        entity.setRecursosComputacionales(domain.getRecursosComputacionales());
        entity.setValorSMLV(domain.getValorSMLV());
        entity.setEsReporteFinal(domain.getEsReporteFinal());
        return entity;
    }
}
