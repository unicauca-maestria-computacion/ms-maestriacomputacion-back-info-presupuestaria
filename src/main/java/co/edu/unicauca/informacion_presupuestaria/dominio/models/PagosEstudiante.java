package co.edu.unicauca.informacion_presupuestaria.dominio.models;

import java.util.List;

public class PagosEstudiante {
    private Long codigoEstudiante;
    private List<Pago> pagos;

    public PagosEstudiante() {
    }

    public PagosEstudiante(Long codigoEstudiante, List<Pago> pagos) {
        this.codigoEstudiante = codigoEstudiante;
        this.pagos = pagos;
    }

    public Long getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(Long codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }
}
