package logico;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Servidor {
    private ServerSocket serverSocket;
    private Clinica clinica;
    private boolean servidorActivo;
    private ScheduledExecutorService scheduler;
    private CopyOnWriteArrayList<ManejadorCliente> clientesConectados;
    private static Servidor instancia;
    private final String BACKUP_FILE = "backup_clinica.dat";
    private final int INTERVALO_BACKUP = 5;

    public Servidor(int puerto) {
        try {
            serverSocket = new ServerSocket(puerto);
            clinica = Clinica.getInstancia();
            servidorActivo = true;
            scheduler = Executors.newScheduledThreadPool(2);
            clientesConectados = new CopyOnWriteArrayList<>();
            
            System.out.println("Servidor de Clinica Medica iniciado en puerto: " + puerto);
            System.out.println("IP del servidor: " + InetAddress.getLocalHost().getHostAddress());
            
            cargarBackup();
            
            programarBackupAutomatico();
            
            System.out.println("Esperando conexiones de clientes...");
            
        } catch (IOException e) {
            System.err.println("Error al iniciar servidor: " + e.getMessage());
        }
    }

    public static Servidor getInstance(int puerto) {
        if (instancia == null) {
            instancia = new Servidor(puerto);
        }
        return instancia;
    }

    public void iniciar() {
        System.out.println("Servidor listo para aceptar conexiones...");
        
        while (servidorActivo) {
            try {
                Socket clienteSocket = serverSocket.accept();
                String direccionCliente = clienteSocket.getInetAddress().getHostAddress();
                int puertoCliente = clienteSocket.getPort();
                
                System.out.println("Nuevo cliente conectado: " + direccionCliente + ":" + puertoCliente);
                System.out.println("Total de clientes conectados: " + (clientesConectados.size() + 1));
                
                ManejadorCliente manejador = new ManejadorCliente(clienteSocket, clinica, this);
                clientesConectados.add(manejador);
                
                Executors.newSingleThreadExecutor().execute(manejador);
                
            } catch (IOException e) {
                if (servidorActivo) {
                    System.err.println("Error aceptando cliente: " + e.getMessage());
                }
            }
        }
    }
    
    private void programarBackupAutomatico() {
        scheduler.scheduleAtFixedRate(() -> {
            guardarBackup();
        }, INTERVALO_BACKUP, INTERVALO_BACKUP, TimeUnit.MINUTES);
        
        System.out.println("Backup automatico programado cada " + INTERVALO_BACKUP + " minutos");
    }
    
    public synchronized boolean guardarBackup() {
        try {
            FileOutputStream fileOut = new FileOutputStream(BACKUP_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(clinica);
            out.close();
            fileOut.close();
            
            System.out.println("Backup guardado exitosamente: " + BACKUP_FILE);
            return true;
            
        } catch (IOException e) {
            System.err.println("Error guardando backup: " + e.getMessage());
            return false;
        }
    }
    
    public synchronized boolean cargarBackup() {
        try {
            File file = new File(BACKUP_FILE);
            if (!file.exists()) {
                System.out.println("No existe archivo de backup, iniciando con datos nuevos");
                return false;
            }
            
            FileInputStream fileIn = new FileInputStream(BACKUP_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Clinica clinicaCargada = (Clinica) in.readObject();
            in.close();
            fileIn.close();
            
            if (clinicaCargada != null) {
                Clinica.setInstancia(clinicaCargada);
                this.clinica = clinicaCargada;
                
                System.out.println("Backup cargado exitosamente: " + BACKUP_FILE);
                System.out.println("Datos cargados:");
                System.out.println("- " + clinica.getMedicos().size() + " medicos");
                System.out.println("- " + clinica.getPacientes().size() + " pacientes");
                System.out.println("- " + clinica.getCitas().size() + " citas");
                System.out.println("- " + clinica.getConsultas().size() + " consultas");
                System.out.println("- " + clinica.getEnfermedadesVigiladas().size() + " enfermedades bajo vigilancia");
                System.out.println("- " + clinica.getCatalogoVacunas().size() + " vacunas en catalogo");
                
                return true;
            } else {
                System.out.println("Archivo de backup corrupto, iniciando con datos nuevos");
                return false;
            }
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error cargando backup: " + e.getMessage());
            return false;
        }
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
        servidorActivo = false;
        
        guardarBackup();
        
        if (clientesConectados != null && !clientesConectados.isEmpty()) {
            broadcastATodos("SERVIDOR_APAGADO:El servidor se esta apagando");
        }
        
        if (scheduler != null) {
            scheduler.shutdown();
        }
        
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error cerrando servidor: " + e.getMessage());
        }
        
        System.out.println("Servidor detenido correctamente");
    }
    
    public Clinica getClinica() {
        return clinica;
    }
    
    public int getClientesConectados() {
        return clientesConectados != null ? clientesConectados.size() : 0;
    }
}