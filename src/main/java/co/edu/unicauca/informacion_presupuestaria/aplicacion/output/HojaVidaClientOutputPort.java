package co.edu.unicauca.informacion_presupuestaria.aplicacion.output;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.Becas;

import java.util.List;

public interface HojaVidaClientOutputPort {
    List<Becas> getBecasByEstudiante(String codigoEstudiante);
    Boolean tieneCertificadoVotacionActivo(String codigoEstudiante, Integer periodo, Integer anio);
    Boolean esEgresado(String codigoEstudiante);
}
