package logico;

/**
 * Clase que representa el resultado de una operación
 * Sigue el patrón Result para manejo de éxito/error sin excepciones
 */
public class ResultadoOperacion {
    private final boolean exitoso;
    private final String mensaje;
    private final String detalles;
    
    private ResultadoOperacion(boolean exitoso, String mensaje, String detalles) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.detalles = detalles;
    }
    
    public static ResultadoOperacion exito(String mensaje) {
        return new ResultadoOperacion(true, mensaje, null);
    }
    
    public static ResultadoOperacion exitoConDetalles(String mensaje, String detalles) {
        return new ResultadoOperacion(true, mensaje, detalles);
    }
    
    public static ResultadoOperacion error(String mensaje) {
        return new ResultadoOperacion(false, mensaje, null);
    }
    
    public static ResultadoOperacion errorConDetalles(String mensaje, String detalles) {
        return new ResultadoOperacion(false, mensaje, detalles);
    }
    
    public boolean isExitoso() {
        return exitoso;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public String getDetalles() {
        return detalles;
    }
    
    public String getMensajeCompleto() {
        if (detalles != null && !detalles.isEmpty()) {
            return mensaje + "\n" + detalles;
        }
        return mensaje;
    }
}

