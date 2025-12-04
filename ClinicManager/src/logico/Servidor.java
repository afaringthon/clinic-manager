package logico;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

public class Servidor {
    private ServerSocket serverSocket;
    private Clinica clinica;
    private boolean servidorActivo;
    private CopyOnWriteArrayList<ManejadorCliente> clientesConectados;
    private static Servidor instancia;
    private Thread hiloServidor;

    private Servidor(int puerto) {
        try {
            serverSocket = new ServerSocket(puerto);
            clinica = Clinica.getInstancia();
            servidorActivo = false;
            clientesConectados = new CopyOnWriteArrayList<>();
            
            System.out.println("Servidor de Clinica Medica configurado en puerto: " + puerto);
            System.out.println("IP del servidor: " + InetAddress.getLocalHost().getHostAddress());
            
        } catch (IOException e) {
            System.err.println("Error al configurar servidor: " + e.getMessage());
        }
    }

    public static Servidor getInstance(int puerto) {
        if (instancia == null) {
            instancia = new Servidor(puerto);
        }
        return instancia;
    }

    public void iniciar() {
        if (servidorActivo) {
            System.out.println("El servidor ya está activo");
            return;
        }
        
        servidorActivo = true;
        
        hiloServidor = new Thread(() -> {
            System.out.println("Servidor iniciado y listo para aceptar conexiones...");
            
            while (servidorActivo) {
                try {
                    Socket clienteSocket = serverSocket.accept();
                    String direccionCliente = clienteSocket.getInetAddress().getHostAddress();
                    int puertoCliente = clienteSocket.getPort();
                    
                    System.out.println("Nuevo cliente conectado: " + direccionCliente + ":" + puertoCliente);
                    System.out.println("Total de clientes conectados: " + (clientesConectados.size() + 1));
                    
                    ManejadorCliente manejador = new ManejadorCliente(clienteSocket, clinica, this);
                    clientesConectados.add(manejador);
                    
                    new Thread(manejador).start();
                    
                } catch (SocketException e) {
                    if (servidorActivo) {
                        System.err.println("Error de socket: " + e.getMessage());
                    }
                } catch (IOException e) {
                    if (servidorActivo) {
                        System.err.println("Error aceptando cliente: " + e.getMessage());
                    }
                }
            }
        });
        
        hiloServidor.setName("Hilo-Servidor");
        hiloServidor.start();
    }
    
    public synchronized void removerCliente(ManejadorCliente cliente) {
        if (clientesConectados != null && cliente != null) {
            clientesConectados.remove(cliente);
            System.out.println("Clientes conectados: " + clientesConectados.size());
        }
    }
    
    public void broadcast(String mensaje, ManejadorCliente emisor) {
        if (clientesConectados == null) return;
        
        int enviados = 0;
        for (ManejadorCliente cliente : clientesConectados) {
            if (cliente != null && cliente != emisor) {
                cliente.enviarMensajeBroadcast(mensaje);
                enviados++;
            }
        }
        if (enviados > 0) {
            System.out.println("Broadcast enviado a " + enviados + " clientes: " + mensaje);
        }
    }
    
    public void broadcastATodos(String mensaje) {
        if (clientesConectados == null) return;
        
        for (ManejadorCliente cliente : clientesConectados) {
            if (cliente != null) {
                cliente.enviarMensajeBroadcast(mensaje);
            }
        }
        System.out.println("Broadcast general enviado a " + clientesConectados.size() + " clientes");
    }
    
    public void detener() {
        if (!servidorActivo) {
            System.out.println("El servidor ya está detenido");
            return;
        }
        
        servidorActivo = false;
        
        System.out.println("Deteniendo servidor...");
        
        if (clientesConectados != null && !clientesConectados.isEmpty()) {
            broadcastATodos("SERVIDOR_APAGADO:El servidor se esta apagando");
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error cerrando servidor: " + e.getMessage());
        }
        
        if (hiloServidor != null && hiloServidor.isAlive()) {
            hiloServidor.interrupt();
        }
        
        System.out.println("Servidor detenido correctamente");
    }
    
  
    private String obtenerRutaRelativa(String nombreArchivo) {

        String directorioActual = System.getProperty("user.dir");
        
        File carpetaArchivos = new File(directorioActual + File.separator + "archivos");
        if (!carpetaArchivos.exists()) {
            carpetaArchivos.mkdirs();
            System.out.println("Carpeta 'archivos' creada en: " + carpetaArchivos.getAbsolutePath());
        }
        
        return carpetaArchivos.getAbsolutePath() + File.separator + nombreArchivo;
    }

    public boolean exportarEnfermedadesATxt(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            if (!archivo.isAbsolute()) {
                String nombreArchivo = archivo.getName();
                rutaArchivo = obtenerRutaRelativa(nombreArchivo);
                System.out.println("Usando ruta relativa: " + rutaArchivo);
            }
            
            try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo, false), true)) {
                writer.println("=== ENFERMEDADES BAJO VIGILANCIA ===");
                writer.println("Generado: " + LocalDateTime.now());
                writer.println("Total: " + clinica.getEnfermedadesVigiladas().size());
                writer.println("=====================================");
                writer.println();
                
                for (EnfermedadBajoVigilancia e : clinica.getEnfermedadesVigiladas()) {
                    if (e != null && e.isEsActivo()) {
                        writer.println("Nombre: " + e.getNombre());
                        writer.println("Gravedad: " + e.getGravedad());
                        writer.println("Descripción: " + e.getDescripcion());
                        writer.println("-------------------------------------");
                    }
                }
                
                System.out.println("Enfermedades exportadas a: " + rutaArchivo);
                return true;
            }
            
        } catch (IOException e) {
            System.err.println("Error exportando enfermedades: " + e.getMessage());
            return false;
        }
    }
    
    public int importarEnfermedadesDesdeTxt(String rutaArchivo) {
        int importadas = 0;
        
        try {
            File archivo = new File(rutaArchivo);
            
            if (!archivo.exists()) {
                File archivoRelativo = new File(obtenerRutaRelativa(archivo.getName()));
                if (archivoRelativo.exists()) {
                    archivo = archivoRelativo;
                    System.out.println("Archivo encontrado en ruta relativa: " + archivo.getAbsolutePath());
                } else {
                    archivoRelativo = new File(System.getProperty("user.dir") + File.separator + archivo.getName());
                    if (archivoRelativo.exists()) {
                        archivo = archivoRelativo;
                        System.out.println("Archivo encontrado en directorio actual: " + archivo.getAbsolutePath());
                    }
                }
            }
            
            if (!archivo.exists()) {
                System.err.println("Archivo no encontrado: " + rutaArchivo);
                return 0;
            }
            
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;
                String nombre = null;
                String gravedad = null;
                StringBuilder descripcionBuilder = null;
                boolean enSeccion = false;
                
                while ((linea = reader.readLine()) != null) {
                    linea = linea.trim();
                    
                    
                    if (linea.isEmpty() || linea.startsWith("===") || linea.startsWith("Generado:") 
                        || linea.startsWith("Total:") || linea.startsWith("ID:") 
                        || linea.startsWith("Nota:") || linea.startsWith("Formato:")) {
                        continue;
                    }
                    

                    if (linea.startsWith("Nombre:")) {
                        if (enSeccion && nombre != null && !nombre.isEmpty()) {
                            String descripcion = (descripcionBuilder != null) ? descripcionBuilder.toString() : "Sin descripción";
                            if (gravedad == null) gravedad = "Media";
                            
                            clinica.agregarEnfermedadVigilida(nombre, descripcion, gravedad);
                            importadas++;
                            System.out.println("Enfermedad importada: " + nombre);
                        }
                        
                        nombre = linea.substring(linea.indexOf(':') + 1).trim();
                        gravedad = null;
                        descripcionBuilder = new StringBuilder();
                        enSeccion = true;
                    } 
                    else if (linea.startsWith("Gravedad:")) {
                        gravedad = linea.substring(linea.indexOf(':') + 1).trim();
                    } 
                    else if (linea.startsWith("Descripción:") || linea.startsWith("Descripcion:")) {
                        if (descripcionBuilder == null) {
                            descripcionBuilder = new StringBuilder();
                        }
                        descripcionBuilder.append(linea.substring(linea.indexOf(':') + 1).trim());
                    } 
                    else if (linea.startsWith("---") || linea.equals("-------------------------------------")) {
                        if (enSeccion && nombre != null && !nombre.isEmpty()) {
                            String descripcion = (descripcionBuilder != null && descripcionBuilder.length() > 0) 
                                ? descripcionBuilder.toString() : "Sin descripción";
                            if (gravedad == null) gravedad = "Media";
                            
                            clinica.agregarEnfermedadVigilida(nombre, descripcion, gravedad);
                            importadas++;
                            System.out.println("Enfermedad importada: " + nombre);
                            
                            nombre = null;
                            gravedad = null;
                            descripcionBuilder = null;
                            enSeccion = false;
                        }
                    } 
                    else if (enSeccion && !linea.isEmpty()) {
                        if (descripcionBuilder == null) {
                            descripcionBuilder = new StringBuilder();
                        }
                        if (descripcionBuilder.length() > 0) {
                            descripcionBuilder.append(" ");
                        }
                        descripcionBuilder.append(linea);
                    }
                }
                
                if (enSeccion && nombre != null && !nombre.isEmpty()) {
                    String descripcion = (descripcionBuilder != null && descripcionBuilder.length() > 0) 
                        ? descripcionBuilder.toString() : "Sin descripción";
                    if (gravedad == null) gravedad = "Media";
                    
                    clinica.agregarEnfermedadVigilida(nombre, descripcion, gravedad);
                    importadas++;
                    System.out.println("Enfermedad importada: " + nombre);
                }
                
                System.out.println("Total de enfermedades importadas: " + importadas);
                return importadas;
            }
            
        } catch (IOException e) {
            System.err.println("Error importando enfermedades: " + e.getMessage());
            return 0;
        }
    }
    
    public Clinica getClinica() {
        return clinica;
    }
    
    public int getClientesConectados() {
        return clientesConectados != null ? clientesConectados.size() : 0;
    }
    
    public boolean isServidorActivo() {
        return servidorActivo;
    }


    public String getRutaArchivos() {
        return obtenerRutaRelativa("");
    }
}