package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.external;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.HojaVidaClientOutputPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Becas;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Stub del microservicio de Hoja de Vida.
 * Cuando el microservicio esté implementado, reemplazar este adaptador
 * por uno HTTP sin tocar el dominio ni el use case.
 */
@Component
public class HojaVidaClientStubAdapter implements HojaVidaClientOutputPort {

    @Override
    public List<Becas> getBecasByEstudiante(String codigoEstudiante) {
        return List.of();
    }

    @Override
    public Boolean tieneCertificadoVotacionActivo(String codigoEstudiante, Integer periodo, Integer anio) {
        return false;
    }

    @Override
    public Boolean esEgresado(String codigoEstudiante) {
        return false;
    }
}
