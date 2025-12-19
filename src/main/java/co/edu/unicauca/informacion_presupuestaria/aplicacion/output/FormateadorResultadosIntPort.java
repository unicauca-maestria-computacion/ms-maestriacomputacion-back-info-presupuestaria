package co.edu.unicauca.informacion_presupuestaria.aplicacion.output;

public interface FormateadorResultadosIntPort {
    void errorEntidadYaExiste(String mensaje);
    void errorEntidadNoExiste(String mensaje);
    void errorReglaNegocioViolada(String mensaje);    
}
