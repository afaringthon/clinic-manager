package logico;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servicio encargado de gestionar operaciones de backup
 * Sigue principios de Single Responsibility y Separation of Concerns
 */
public class BackupService {
    
    private static final String FORMATO_TIMESTAMP = "yyyyMMdd_HHmmss";
    private static final String EXTENSION_BACKUP = ".dat";
    private static final int PUERTO_DEFAULT = 7000;
    
    /**
     * Genera un nombre de archivo de backup con timestamp
     */
    public String generarNombreBackup() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_TIMESTAMP);
        return "backup_clinica" + EXTENSION_BACKUP;
    }
    
    /**
     * Valida que una ruta de archivo tenga la extensión correcta
     */
    public String validarYCompletarExtension(String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) {
            return null;
        }
        
        if (!ruta.toLowerCase().endsWith(EXTENSION_BACKUP)) {
            return ruta + EXTENSION_BACKUP;
        }
        
        return ruta;
    }
    
    /**
     * Realiza un backup local en la ruta especificada
     */
    public ResultadoOperacion hacerBackupLocal(String rutaCompleta) {
        if (rutaCompleta == null || rutaCompleta.trim().isEmpty()) {
            return ResultadoOperacion.error("La ruta del archivo no puede estar vacía");
        }
        
        try {
            // Primero guardar datos actuales
            Datos.guardar();
            
            // Luego hacer el backup
            String rutaGuardada = Datos.hacerBackupLocal(rutaCompleta);
            
            if (rutaGuardada != null) {
                return ResultadoOperacion.exitoConDetalles(
                    "Backup creado exitosamente",
                    rutaGuardada
                );
            } else {
                return ResultadoOperacion.error(
                    "Error al crear el archivo de backup"
                );
            }
            
        } catch (Exception e) {
            return ResultadoOperacion.errorConDetalles(
                "Error inesperado al crear backup",
                e.getMessage()
            );
        }
    }
    
    /**
     * Valida los parámetros de conexión al servidor
     */
    private ResultadoOperacion validarParametrosServidor(String host, String puertoStr) {
        if (host == null || host.trim().isEmpty()) {
            return ResultadoOperacion.error("La dirección del servidor no puede estar vacía");
        }
        
        if (puertoStr == null || puertoStr.trim().isEmpty()) {
            return ResultadoOperacion.error("El puerto no puede estar vacío");
        }
        
        try {
            int puerto = Integer.parseInt(puertoStr);
            if (puerto < 1 || puerto > 65535) {
                return ResultadoOperacion.error("El puerto debe estar entre 1 y 65535");
            }
        } catch (NumberFormatException e) {
            return ResultadoOperacion.error("El puerto debe ser un número válido");
        }
        
        return ResultadoOperacion.exito("Parámetros válidos");
    }
    
    /**
     * Realiza un backup en el servidor remoto usando sockets
     */
    public ResultadoOperacion hacerBackupRemoto(String host, String puertoStr) {
        // Validar parámetros
        ResultadoOperacion validacion = validarParametrosServidor(host, puertoStr);
        if (!validacion.isExitoso()) {
            return validacion;
        }
        
        Cliente cliente = null;
        try {
            int puerto = Integer.parseInt(puertoStr);
            cliente = new Cliente(host, puerto);
            
            // Intentar conectar
            if (!cliente.conectar()) {
                return ResultadoOperacion.errorConDetalles(
                    "No se pudo conectar al servidor",
                    "Verifique que el servidor esté corriendo en " + host + ":" + puerto
                );
            }
            
            // Solicitar backup
            boolean exito = cliente.solicitarBackup();
            
            if (exito) {
                return ResultadoOperacion.exitoConDetalles(
                    "Backup en servidor creado exitosamente",
                    "Servidor: " + host + ":" + puerto
                );
            } else {
                return ResultadoOperacion.error(
                    "El servidor no pudo crear el backup"
                );
            }
            
        } catch (NumberFormatException e) {
            return ResultadoOperacion.error("Puerto inválido");
        } catch (Exception e) {
            return ResultadoOperacion.errorConDetalles(
                "Error al comunicarse con el servidor",
                e.getMessage()
            );
        } finally {
            // Asegurar que el cliente se desconecte
            if (cliente != null) {
                try {
                    cliente.desconectar();
                } catch (Exception e) {
                    System.err.println("Error al desconectar cliente: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Obtiene el puerto por defecto para conexiones
     */
    public int getPuertoDefault() {
        return PUERTO_DEFAULT;
    }
}

