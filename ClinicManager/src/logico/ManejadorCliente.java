package logico;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ManejadorCliente implements Runnable {
    private Socket clienteSocket;
    private Clinica clinica;
    private Servidor servidor;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String direccionCliente;
    private boolean conectado;

    public ManejadorCliente(Socket socket, Clinica clinica, Servidor servidor) {
        this.clienteSocket = socket;
        this.clinica = clinica;
        this.servidor = servidor;
        this.direccionCliente = socket.getInetAddress().getHostAddress();
        this.conectado = true;
    }

    @Override
    public void run() {
        System.out.println("Hilo iniciado para cliente: " + direccionCliente);
        
        try {
            output = new ObjectOutputStream(clienteSocket.getOutputStream());
            input = new ObjectInputStream(clienteSocket.getInputStream());
            
            output.writeObject("CONEXION_EXITOSA:Bienvenido a Clinic Manager");
            output.flush();
            
            while (conectado && !clienteSocket.isClosed()) {
                Object comandoObj = input.readObject();
                
                if (comandoObj instanceof String) {
                    String comando = (String) comandoObj;
                    System.out.println("Comando de " + direccionCliente + ": " + comando);
                    procesarComando(comando);
                }
            }
            
        } catch (EOFException e) {
            System.out.println("Cliente desconectado (EOF): " + direccionCliente);
        } catch (SocketException e) {
            System.out.println("Cliente desconectado: " + direccionCliente);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error con cliente " + direccionCliente + ": " + e.getMessage());
        } finally {
            desconectar();
        }
    }
    
    private void procesarComando(String comando) throws IOException {
        try {
            switch (comando) {
                case "OBTENER_DATOS_SERVIDOR":
                    byte[] datosServidor = servidor.obtenerDatosSerializados();
                    output.writeObject(datosServidor);
                    output.flush();
                    break;
                    
                case "ENVIAR_DATOS_SERVIDOR":
                    byte[] datosRecibidos = (byte[]) input.readObject();
                    boolean resultado = servidor.restaurarDatosSerializados(datosRecibidos);
                    output.writeObject(resultado);
                    output.flush();
                    if (resultado) {
                        System.out.println("Datos recibidos y restaurados desde cliente: " + direccionCliente);
                        servidor.broadcast("ACTUALIZAR_TODOS", this);
                    }
                    break;
                    
                case "OBTENER_USUARIOS_SERVIDOR":
                    byte[] usuariosServidor = servidor.obtenerUsuariosSerializados();
                    output.writeObject(usuariosServidor);
                    output.flush();
                    break;
                    
                case "ENVIAR_USUARIOS_SERVIDOR":
                    byte[] usuariosRecibidos = (byte[]) input.readObject();
                    boolean resultadoUsuarios = servidor.restaurarUsuariosSerializados(usuariosRecibidos);
                    output.writeObject(resultadoUsuarios);
                    output.flush();
                    if (resultadoUsuarios) {
                        System.out.println("Usuarios recibidos y restaurados desde cliente: " + direccionCliente);
                    }
                    break;
                    
                case "HACER_RESPALDO_SERVIDOR":
                    boolean respaldoExitoso = clinica.guardarRespaldo();
                    output.writeObject(respaldoExitoso);
                    output.flush();
                    break;
                
                case "LISTAR_RESPALDOS_SERVIDOR":
                    ArrayList<String> listaRespaldos = clinica.listarRespaldos();
                    output.writeObject(listaRespaldos);
                    output.flush();
                    break;
                    
                case "RESTAURAR_RESPALDO_SERVIDOR":
                    String nombreRespaldo = (String) input.readObject();
                    boolean restauracionExitosa = clinica.restaurarRespaldo(nombreRespaldo);
                    output.writeObject(restauracionExitosa);
                    output.flush();
                    if (restauracionExitosa) {
                        servidor.broadcast("ACTUALIZAR_TODOS", this);
                    }
                    break;
                    
                case "AGREGAR_MEDICO":
                    Medico medico = (Medico) input.readObject();
                    clinica.getMedicos().add(medico);
                    output.writeObject(true);
                    if (medico != null) {
                        System.out.println("Medico agregado por: " + direccionCliente);
                        servidor.broadcast("ACTUALIZAR_MEDICOS", this);
                    }
                    break;
                    
                case "BUSCAR_MEDICO_POR_CEDULA":
                    String cedulaMedico = (String) input.readObject();
                    Medico medicoEncontrado = null;
                    for (Medico m : clinica.getMedicos()) {
                        if (m.getCedula().equals(cedulaMedico) && m.isActivo()) {
                            medicoEncontrado = m;
                            break;
                        }
                    }
                    output.writeObject(medicoEncontrado);
                    break;
                    
                case "AGREGAR_PACIENTE":
                    Paciente paciente = (Paciente) input.readObject();
                    clinica.getPacientes().add(paciente);
                    output.writeObject(true);
                    if (paciente != null) {
                        System.out.println("Paciente agregado por: " + direccionCliente);
                        servidor.broadcast("ACTUALIZAR_PACIENTES", this);
                    }
                    break;
                    
                case "BUSCAR_PACIENTE_POR_CEDULA":
                    String cedulaPaciente = (String) input.readObject();
                    Paciente pacienteEncontrado = null;
                    for (Paciente p : clinica.getPacientes()) {
                        if (p.getCedula().equals(cedulaPaciente) && p.isActivo()) {
                            pacienteEncontrado = p;
                            break;
                        }
                    }
                    output.writeObject(pacienteEncontrado);
                    break;
                    
                case "AGENDAR_CITA":
                    Cita cita = (Cita) input.readObject();
                    clinica.getCitas().add(cita);
                    output.writeObject(true);
                    if (cita != null) {
                        System.out.println("Cita agendada por: " + direccionCliente);
                        servidor.broadcast("ACTUALIZAR_CITAS", this);
                    }
                    break;
                    
                case "OBTENER_MEDICOS":
                    output.writeObject(clinica.getMedicos());
                    output.flush();
                    break;
                    
                case "OBTENER_PACIENTES":
                    output.writeObject(clinica.getPacientes());
                    output.flush();
                    break;
                    
                case "OBTENER_CITAS_ACTIVAS":
                    ArrayList<Cita> citasActivas = new ArrayList<>();
                    for (Cita c : clinica.getCitas()) {
                        if (c != null && c.isEsActivo()) {
                            citasActivas.add(c);
                        }
                    }
                    output.writeObject(citasActivas);
                    break;
                    
                case "OBTENER_MEDICOS_DISPONIBLES":
                    LocalDate fecha = (LocalDate) input.readObject();
                    output.writeObject(clinica.getMedicosDisponibles(fecha));
                    break;
                    
                case "AGREGAR_CONSULTA":
                    Consulta consulta = (Consulta) input.readObject();
                    clinica.getConsultas().add(consulta);
                    if (consulta.getPaciente() != null) {
                        consulta.getPaciente().agregarConsulta(consulta);
                    }
                    output.writeObject(true);
                    System.out.println("Consulta agregada por: " + direccionCliente);
                    servidor.broadcast("ACTUALIZAR_CONSULTAS", this);
                    break;
                    
                case "AGREGAR_ENFERMEDAD_VIGILADA":
                    EnfermedadBajoVigilancia enfermedad = (EnfermedadBajoVigilancia) input.readObject();
                    clinica.getEnfermedadesVigiladas().add(enfermedad);
                    output.writeObject(true);
                    System.out.println("Enfermedad vigilada agregada por: " + direccionCliente);
                    servidor.broadcast("ACTUALIZAR_ENFERMEDADES", this);
                    break;
                    
                case "OBTENER_ENFERMEDADES_VIGILADAS":
                    output.writeObject(clinica.getEnfermedadesVigiladas());
                    output.flush();
                    break;
                    
                case "OBTENER_CATALOGO_VACUNAS":
                    output.writeObject(clinica.getCatalogoVacunas());
                    output.flush();
                    break;
                    
                case "EXISTE_PACIENTE":
                    String cedula = (String) input.readObject();
                    boolean existe = clinica.verificarSiPacienteExiste(cedula);
                    output.writeObject(existe);
                    break;
                    
                case "BROADCAST_MENSAJE":
                    String mensaje = (String) input.readObject();
                    System.out.println("Broadcast de " + direccionCliente + ": " + mensaje);
                    servidor.broadcast("MENSAJE:" + direccionCliente + ": " + mensaje, this);
                    break;
                    
                case "EXPORTAR_ENFERMEDADES_TXT":
                    String rutaExportar = (String) input.readObject();
                    boolean exportado = servidor.exportarEnfermedadesATxt(rutaExportar);
                    output.writeObject(exportado);
                    if (exportado) {
                        System.out.println("Enfermedades exportadas por: " + direccionCliente);
                    }
                    break;
                    
                case "IMPORTAR_ENFERMEDADES_TXT":
                    String rutaImportar = (String) input.readObject();
                    int importadas = servidor.importarEnfermedadesDesdeTxt(rutaImportar);
                    output.writeObject(importadas);
                    if (importadas > 0) {
                        System.out.println("Enfermedades importadas por: " + direccionCliente + " - Total: " + importadas);
                        servidor.broadcast("ACTUALIZAR_ENFERMEDADES", this);
                    }
                    break;
                    
                case "ENVIAR_ARCHIVO_ENFERMEDADES":
                    String contenidoArchivo = (String) input.readObject();
                    String nombreArchivo = (String) input.readObject();
                    
                    String rutaServidor = obtenerRutaArchivoTemporal(nombreArchivo);
                    try (PrintWriter writer = new PrintWriter(new FileWriter(rutaServidor))) {
                        writer.print(contenidoArchivo);
                    }
                    
                    int importadasDesdeCliente = servidor.importarEnfermedadesDesdeTxt(rutaServidor);
                    output.writeObject(importadasDesdeCliente);
                    
                    System.out.println("Archivo recibido de " + direccionCliente + ": " + nombreArchivo);
                    System.out.println("Enfermedades importadas: " + importadasDesdeCliente);
                    
                    if (importadasDesdeCliente > 0) {
                        servidor.broadcast("ACTUALIZAR_ENFERMEDADES", this);
                    }
                    
                    limpiarArchivoTemporal(rutaServidor);
                    break;
                    
                case "DESCARGAR_ENFERMEDADES_TXT":
                    StringBuilder sb = new StringBuilder();
                    sb.append("=== ENFERMEDADES BAJO VIGILANCIA ===\n");
                    sb.append("Generado: ").append(LocalDateTime.now()).append("\n");
                    sb.append("Total: ").append(clinica.getEnfermedadesVigiladas().size()).append("\n");
                    sb.append("=====================================\n\n");
                    
                    for (EnfermedadBajoVigilancia e : clinica.getEnfermedadesVigiladas()) {
                        if (e != null && e.isEsActivo()) {
                            sb.append("Nombre: ").append(e.getNombre()).append("\n");
                            sb.append("Gravedad: ").append(e.getGravedad()).append("\n");
                            sb.append("Descripción: ").append(e.getDescripcion()).append("\n");
                            sb.append("-------------------------------------\n");
                        }
                    }
                    
                    output.writeObject(sb.toString());
                    System.out.println("Archivo de enfermedades enviado a: " + direccionCliente);
                    break;
                    
                case "PING":
                    output.writeObject("PONG");
                    break;
                    
                case "DESCONECTAR":
                    output.writeObject("DESCONEXION_EXITOSA");
                    desconectar();
                    break;
                    
                default:
                    output.writeObject("ERROR:COMANDO_DESCONOCIDO");
                    break;
            }
            output.flush();
        } catch (Exception e) {
            try {
                output.writeObject("ERROR:" + e.getMessage());
                output.flush();
            } catch (IOException ex) {
                System.err.println("Error enviando mensaje de error al cliente: " + ex.getMessage());
            }
            e.printStackTrace();
        }
    }
    
    private String obtenerRutaArchivoTemporal(String nombreOriginal) {
        String directorioActual = System.getProperty("user.dir");
        File carpetaTemp = new File(directorioActual + File.separator + "temp");
        if (!carpetaTemp.exists()) {
            carpetaTemp.mkdirs();
        }
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nombreArchivo = "temp_" + timestamp + "_" + nombreOriginal;
        return carpetaTemp.getAbsolutePath() + File.separator + nombreArchivo;
    }
    
    private void limpiarArchivoTemporal(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            if (archivo.exists()) {
                archivo.delete();
            }
        } catch (SecurityException e) {
        }
    }
    
    public static String getRutaArchivosRelativa() {
        String directorioActual = System.getProperty("user.dir");
        File carpetaArchivos = new File(directorioActual + File.separator + "archivos");
        if (!carpetaArchivos.exists()) {
            carpetaArchivos.mkdirs();
        }
        return carpetaArchivos.getAbsolutePath();
    }
    
    public void enviarMensajeBroadcast(String mensaje) {
        try {
            if (output != null && conectado) {
                output.writeObject("BROADCAST:" + mensaje);
                output.flush();
            }
        } catch (IOException e) {
            System.err.println("Error enviando broadcast a " + direccionCliente);
        }
    }
    
    private void desconectar() {
        conectado = false;
        try {
            if (input != null) {
                try { input.close(); } catch (IOException e) { }
            }
            if (output != null) {
                try { output.close(); } catch (IOException e) { }
            }
            if (clienteSocket != null && !clienteSocket.isClosed()) {
                try { clienteSocket.close(); } catch (IOException e) { }
            }
            servidor.removerCliente(this);
            System.out.println("Cliente desconectado: " + direccionCliente);
        } catch (Exception e) {
            System.err.println("Error cerrando conexion: " + e.getMessage());
        }
    }
    
    public String getDireccionCliente() {
        return direccionCliente;
    }
}