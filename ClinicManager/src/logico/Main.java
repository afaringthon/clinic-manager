package logico;

import java.time.LocalDate;
import visual.Login;
import visual.principal;

public class Main {
    
    private static Cliente cliente;
    private static Servidor servidor;
    private static boolean esServidor = false;

    public static void main(String[] args) {
        
        // Verificar si se debe ejecutar como servidor
        if (args.length > 0 && args[0].equalsIgnoreCase("servidor")) {
            ejecutarComoServidor();
        } else {
            ejecutarComoCliente();
        }
    }
    
    private static void ejecutarComoServidor() {
        esServidor = true;
        int puerto = 7000;
        
        System.out.println("=========================================");
        System.out.println("   SISTEMA DE CLINICA MEDICA - SERVIDOR");
        System.out.println("=========================================");
        
        servidor = Servidor.getInstance(puerto);
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Apagando servidor...");
            if (servidor != null) {
                servidor.detener();
            }
        }));
        
        servidor.iniciar();
    }
    
    private static void ejecutarComoCliente() {
        esServidor = false;
        
        // Conectar al servidor
        cliente = new Cliente("localhost", 7000);
        boolean conectado = cliente.conectar();
        
        if (!conectado) {
            System.err.println("No se pudo conectar al servidor.");
            System.err.println("Asegurese de que el servidor este ejecutandose.");
            System.err.println("Puede ejecutar como servidor con: java Main servidor");
            return;
        }
        
        // Verificar conexion
        if (cliente.testConexion()) {
            System.out.println("Conexion con servidor establecida correctamente");
        } else {
            System.err.println("Error en la conexion con el servidor");
            return;
        }
        
        // Mostrar ventana de login
        principal framePrincipal = new principal();
        Login dialog = new Login(framePrincipal, cliente);
        dialog.setVisible(true);//falta crear clase login
        
        // Al cerrar la aplicacion
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (cliente != null) {
                cliente.desconectar();
            }
        }));
    }
    
    public static Cliente getCliente() {
        return cliente;
    }
    
    public static boolean esServidor() {
        return esServidor;
    }
}