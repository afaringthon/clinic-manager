package logico;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String host;
    private int puerto;
    private boolean conectado;
    private Thread hiloEscucha;

    public Cliente(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
        this.conectado = false;
    }

    public boolean conectar() {
        try {
            socket = new Socket(host, puerto);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            conectado = true;
            
            String respuesta = (String) input.readObject();
            System.out.println("Servidor: " + respuesta);
            
            iniciarHiloEscucha();
            
            System.out.println("Conectado al servidor: " + host + ":" + puerto);
            
            sincronizarDatosIniciales();
            
            return true;
            
        } catch (ConnectException e) {
            System.err.println("No se pudo conectar al servidor " + host + ":" + puerto);
            return false;
        } catch (UnknownHostException e) {
            System.err.println("Host desconocido: " + host);
            return false;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error conectando al servidor: " + e.getMessage());
            return false;
        }
    }
    
    private void sincronizarDatosIniciales() {
        try {
            System.out.println("Sincronizando datos con el servidor...");
            
            ArrayList<Medico> medicosServidor = obtenerMedicos();
            ArrayList<Paciente> pacientesServidor = obtenerPacientes();
            ArrayList<EnfermedadBajoVigilancia> enfermedadesServidor = obtenerEnfermedadesVigiladas();
            ArrayList<Vacuna> vacunasServidor = obtenerCatalogoVacunas();
            
            Clinica clinicaLocal = Clinica.getInstancia();
            
            if (medicosServidor != null && !medicosServidor.isEmpty()) {
                clinicaLocal.getMedicos().clear();
                clinicaLocal.getMedicos().addAll(medicosServidor);
                System.out.println("Médicos sincronizados: " + medicosServidor.size());
            }
            
            if (pacientesServidor != null && !pacientesServidor.isEmpty()) {
                clinicaLocal.getPacientes().clear();
                clinicaLocal.getPacientes().addAll(pacientesServidor);
                System.out.println("Pacientes sincronizados: " + pacientesServidor.size());
            }
            
            if (enfermedadesServidor != null && !enfermedadesServidor.isEmpty()) {
                clinicaLocal.getEnfermedadesVigiladas().clear();
                clinicaLocal.getEnfermedadesVigiladas().addAll(enfermedadesServidor);
                System.out.println("Enfermedades sincronizadas: " + enfermedadesServidor.size());
            }
            
            if (vacunasServidor != null && !vacunasServidor.isEmpty()) {
                clinicaLocal.getCatalogoVacunas().clear();
                clinicaLocal.getCatalogoVacunas().addAll(vacunasServidor);
                System.out.println("Vacunas sincronizadas: " + vacunasServidor.size());
            }
            
            System.out.println("Datos sincronizados exitosamente con el servidor");
            
        } catch (Exception e) {
            System.err.println("Error sincronizando datos: " + e.getMessage());
        }
    }
    
    private void iniciarHiloEscucha() {
        hiloEscucha = new Thread(() -> {
            Thread.currentThread().setName("Hilo-Escucha-Cliente");
            System.out.println("Hilo de escucha iniciado");
            
            while (conectado && !socket.isClosed()) {
                try {
                    Object mensajeObj = input.readObject();
                    if (mensajeObj instanceof String) {
                        String mensaje = (String) mensajeObj;
                        procesarMensajeServidor(mensaje);
                    }
                } catch (EOFException e) {
                    System.out.println("Conexión con servidor finalizada (EOF)");
                    break;
                } catch (SocketException e) {
                    System.out.println("Conexión con servidor perdida: " + e.getMessage());
                    break;
                } catch (IOException | ClassNotFoundException e) {
                    if (conectado) {
                        System.err.println("Error escuchando servidor: " + e.getMessage());
                    }
                    break;
                }
            }
            desconectar();
        });
        hiloEscucha.start();
    }
    
    private void procesarMensajeServidor(String mensaje) {
        System.out.println("Mensaje del servidor: " + mensaje);
        
        if (mensaje.startsWith("BROADCAST:")) {
            String contenido = mensaje.substring(10);
            System.out.println("Broadcast recibido: " + contenido);
        } else if (mensaje.equals("SERVIDOR_APAGADO:El servidor se esta apagando")) {
            System.out.println("El servidor se está apagando. Desconectando...");
            desconectar();
        } else if (mensaje.startsWith("ACTUALIZAR_")) {
            System.out.println("Actualización recibida del servidor: " + mensaje);
        }
    }

    public void desconectar() {
        conectado = false;
        try {
            if (hiloEscucha != null && hiloEscucha.isAlive()) {
                hiloEscucha.interrupt();
            }
            
            if (output != null && socket != null && !socket.isClosed()) {
                try {
                    output.writeObject("DESCONECTAR");
                    output.flush();
                } catch (IOException e) {
                }
            }
            
            if (input != null) {
                try { input.close(); } catch (IOException e) { /* Ignorar */ }
            }
            if (output != null) {
                try { output.close(); } catch (IOException e) { /* Ignorar */ }
            }
            if (socket != null && !socket.isClosed()) {
                try { socket.close(); } catch (IOException e) { /* Ignorar */ }
            }
            
            System.out.println("Desconectado del servidor");
            
        } catch (Exception e) {
            System.err.println("Error durante desconexión: " + e.getMessage());
        }
    }

    public boolean agregarMedico(Medico medico) {
        if (!estaConectado()) return false;
        
        try {
            output.writeObject("AGREGAR_MEDICO");
            output.writeObject(medico);
            output.flush();
            return (boolean) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error agregando medico: " + e.getMessage());
            return false;
        }
    }
    
    public Medico buscarMedicoPorCedula(String cedula) {
        if (!estaConectado()) return null;
        
        try {
            output.writeObject("BUSCAR_MEDICO_POR_CEDULA");
            output.writeObject(cedula);
            output.flush();
            return (Medico) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error buscando medico: " + e.getMessage());
            return null;
        }
    }
    
    public boolean agregarPaciente(Paciente paciente) {
        if (!estaConectado()) return false;
        
        try {
            output.writeObject("AGREGAR_PACIENTE");
            output.writeObject(paciente);
            output.flush();
            return (boolean) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error agregando paciente: " + e.getMessage());
            return false;
        }
    }
    
    public Paciente buscarPacientePorCedula(String cedula) {
        if (!estaConectado()) return null;
        
        try {
            output.writeObject("BUSCAR_PACIENTE_POR_CEDULA");
            output.writeObject(cedula);
            output.flush();
            return (Paciente) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error buscando paciente: " + e.getMessage());
            return null;
        }
    }
    
    public boolean agendarCita(Cita cita) {
        if (!estaConectado()) return false;
        
        try {
            output.writeObject("AGENDAR_CITA");
            output.writeObject(cita);
            output.flush();
            return (boolean) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error agendando cita: " + e.getMessage());
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<Medico> obtenerMedicos() {
        if (!estaConectado()) return new ArrayList<>();
        
        try {
            output.writeObject("OBTENER_MEDICOS");
            output.flush();
            return (ArrayList<Medico>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error obteniendo medicos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<Paciente> obtenerPacientes() {
        if (!estaConectado()) return new ArrayList<>();
        
        try {
            output.writeObject("OBTENER_PACIENTES");
            output.flush();
            return (ArrayList<Paciente>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error obteniendo pacientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<Medico> obtenerMedicosDisponibles(LocalDate fecha) {
        if (!estaConectado()) return new ArrayList<>();
        
        try {
            output.writeObject("OBTENER_MEDICOS_DISPONIBLES");
            output.writeObject(fecha);
            output.flush();
            return (ArrayList<Medico>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error obteniendo medicos disponibles: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public boolean agregarConsulta(Consulta consulta) {
        if (!estaConectado()) return false;
        
        try {
            output.writeObject("AGREGAR_CONSULTA");
            output.writeObject(consulta);
            output.flush();
            return (boolean) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error agregando consulta: " + e.getMessage());
            return false;
        }
    }
    
    public boolean agregarEnfermedadVigilada(EnfermedadBajoVigilancia enfermedad) {
        if (!estaConectado()) return false;
        
        try {
            output.writeObject("AGREGAR_ENFERMEDAD_VIGILADA");
            output.writeObject(enfermedad);
            output.flush();
            return (boolean) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error agregando enfermedad: " + e.getMessage());
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<EnfermedadBajoVigilancia> obtenerEnfermedadesVigiladas() {
        if (!estaConectado()) return new ArrayList<>();
        
        try {
            output.writeObject("OBTENER_ENFERMEDADES_VIGILADAS");
            output.flush();
            return (ArrayList<EnfermedadBajoVigilancia>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error obteniendo enfermedades: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<Vacuna> obtenerCatalogoVacunas() {
        if (!estaConectado()) return new ArrayList<>();
        
        try {
            output.writeObject("OBTENER_CATALOGO_VACUNAS");
            output.flush();
            return (ArrayList<Vacuna>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error obteniendo vacunas: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public boolean existePaciente(String cedula) {
        if (!estaConectado()) return false;
        
        try {
            output.writeObject("EXISTE_PACIENTE");
            output.writeObject(cedula);
            output.flush();
            return (boolean) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error verificando paciente: " + e.getMessage());
            return false;
        }
    }
    
    public boolean testConexion() {
        if (!estaConectado()) return false;
        
        try {
            output.writeObject("PING");
            output.flush();
            String respuesta = (String) input.readObject();
            return "PONG".equals(respuesta);
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }
    
    public void enviarBroadcast(String mensaje) {
        if (!estaConectado()) return;
        
        try {
            output.writeObject("BROADCAST_MENSAJE");
            output.writeObject(mensaje);
            output.flush();
        } catch (IOException e) {
            System.err.println("Error enviando broadcast: " + e.getMessage());
        }
    }
    
    public boolean estaConectado() {
        return conectado && socket != null && !socket.isClosed() && socket.isConnected();
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<Cita> obtenerCitas() {
        if (!estaConectado()) return new ArrayList<>();
        
        try {
            output.writeObject("OBTENER_CITAS_ACTIVAS");
            output.flush();
            return (ArrayList<Cita>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error obteniendo citas: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    private String obtenerRutaRelativa(String nombreArchivo) {
        String directorioActual = System.getProperty("user.dir");
        File carpetaArchivos = new File(directorioActual + File.separator + "archivos");
        if (!carpetaArchivos.exists()) {
            carpetaArchivos.mkdirs();
        }
        return carpetaArchivos.getAbsolutePath() + File.separator + nombreArchivo;
    }
    
    private String obtenerRutaTemporal(String nombreOriginal) {
        String directorioActual = System.getProperty("user.dir");
        File carpetaTemp = new File(directorioActual + File.separator + "temp");
        if (!carpetaTemp.exists()) {
            carpetaTemp.mkdirs();
        }
        
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nombreArchivo = "temp_" + timestamp + "_" + nombreOriginal;
        
        return carpetaTemp.getAbsolutePath() + File.separator + nombreArchivo;
    }
    
    public int enviarArchivoEnfermedades(String rutaArchivo) {
        if (!estaConectado()) return 0;
        
        try {
            File archivo = new File(rutaArchivo);
            
            if (!archivo.exists() && !archivo.isAbsolute()) {
                File archivoRelativo = new File(obtenerRutaRelativa(archivo.getName()));
                if (archivoRelativo.exists()) {
                    archivo = archivoRelativo;
                } else {
                    archivoRelativo = new File(System.getProperty("user.dir") + File.separator + archivo.getName());
                    if (archivoRelativo.exists()) {
                        archivo = archivoRelativo;
                    }
                }
            }
            
            if (!archivo.exists()) {
                System.err.println("Archivo no encontrado: " + rutaArchivo);
                return 0;
            }
            
            StringBuilder contenido = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }
            }
            
            output.writeObject("ENVIAR_ARCHIVO_ENFERMEDADES");
            output.writeObject(contenido.toString());
            output.writeObject(archivo.getName());
            output.flush();
            
            return (int) input.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error enviando archivo: " + e.getMessage());
            return 0;
        }
    }
    
    public boolean descargarEnfermedadesTxt(String rutaDestino) {
        if (!estaConectado()) return false;
        
        try {
            output.writeObject("DESCARGAR_ENFERMEDADES_TXT");
            output.flush();
            
            String contenido = (String) input.readObject();
            
            File archivoDestino = new File(rutaDestino);
            if (!archivoDestino.isAbsolute()) {
                rutaDestino = obtenerRutaRelativa(archivoDestino.getName());
            }
            
            try (PrintWriter writer = new PrintWriter(new FileWriter(rutaDestino))) {
                writer.print(contenido);
            }
            
            System.out.println("Enfermedades descargadas a: " + rutaDestino);
            return true;
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error descargando enfermedades: " + e.getMessage());
            return false;
        }
    }
    
    public boolean exportarEnfermedadesEnServidor(String rutaServidor) {
        if (!estaConectado()) return false;
        
        try {
            output.writeObject("EXPORTAR_ENFERMEDADES_TXT");
            output.writeObject(rutaServidor);
            output.flush();
            return (boolean) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error exportando enfermedades: " + e.getMessage());
            return false;
        }
    }
    
    public int importarEnfermedadesEnServidor(String rutaServidor) {
        if (!estaConectado()) return 0;
        
        try {
            output.writeObject("IMPORTAR_ENFERMEDADES_TXT");
            output.writeObject(rutaServidor);
            output.flush();
            return (int) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error importando enfermedades: " + e.getMessage());
            return 0;
        }
    }
    
    public boolean exportarEnfermedades(String nombreArchivo) {
        return exportarEnfermedadesEnServidor(nombreArchivo);
    }
    
    public int importarEnfermedades(String nombreArchivo) {
        return importarEnfermedadesEnServidor(nombreArchivo);
    }
    
    public static String getRutaArchivos() {
        String directorioActual = System.getProperty("user.dir");
        File carpetaArchivos = new File(directorioActual + File.separator + "archivos");
        if (!carpetaArchivos.exists()) {
            carpetaArchivos.mkdirs();
        }
        return carpetaArchivos.getAbsolutePath();
    }
    
    public static String getRutaTemp() {
        String directorioActual = System.getProperty("user.dir");
        File carpetaTemp = new File(directorioActual + File.separator + "temp");
        if (!carpetaTemp.exists()) {
            carpetaTemp.mkdirs();
        }
        return carpetaTemp.getAbsolutePath();
    }
}