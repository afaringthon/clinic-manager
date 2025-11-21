package logico;

import java.io.*;
import java.net.*;
import java.time.LocalDate;

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
            // Crear streams
            output = new ObjectOutputStream(clienteSocket.getOutputStream());
            input = new ObjectInputStream(clienteSocket.getInputStream());
            

            output.writeObject("CONEXION_EXITOSA:Bienvenido al Sistema Clinica");
            output.flush();
            
            while (conectado && !clienteSocket.isClosed()) {
                // Recibir comando del cliente
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
                case "AGREGAR_MEDICO":
                    Medico medico = (Medico) input.readObject();
                    boolean resultadoMedico = clinica.agregarMedico(medico);
                    output.writeObject(resultadoMedico);
                    if (resultadoMedico) {
                        System.out.println("Medico agregado por: " + direccionCliente);
                        servidor.guardarBackup();
                        servidor.broadcast("ACTUALIZAR_MEDICOS", this);
                    }
                    break;
                    
                case "BUSCAR_MEDICO_POR_CEDULA":
                    String cedulaMedico = (String) input.readObject();
                    Medico medicoEncontrado = clinica.buscarMedicoPorCedula(cedulaMedico);
                    output.writeObject(medicoEncontrado);
                    break;
                    
                case "AGREGAR_PACIENTE":
                    Paciente paciente = (Paciente) input.readObject();
                    boolean resultadoPaciente = clinica.agregarPaciente(paciente);
                    output.writeObject(resultadoPaciente);
                    if (resultadoPaciente) {
                        System.out.println("Paciente agregado por: " + direccionCliente);
                        servidor.guardarBackup();
                        servidor.broadcast("ACTUALIZAR_PACIENTES", this);
                    }
                    break;
                    
                case "BUSCAR_PACIENTE_POR_CEDULA":
                    String cedulaPaciente = (String) input.readObject();
                    Paciente pacienteEncontrado = clinica.buscarPacientePorCedula(cedulaPaciente);
                    output.writeObject(pacienteEncontrado);
                    break;
                    
                case "AGENDAR_CITA":
                    Cita cita = (Cita) input.readObject();
                    boolean resultadoCita = clinica.agendarCita(cita);
                    output.writeObject(resultadoCita);
                    if (resultadoCita) {
                        System.out.println("Cita agendada por: " + direccionCliente);
                        servidor.guardarBackup();
                        servidor.broadcast("ACTUALIZAR_CITAS", this);
                    }
                    break;
                    
                case "OBTENER_MEDICOS":
                    output.writeObject(clinica.getMedicos());
                    break;
                    
                case "OBTENER_PACIENTES":
                    output.writeObject(clinica.getPacientes());
                    break;
                    
                case "OBTENER_CITAS_ACTIVAS":
                    output.writeObject(clinica.getCitasActivas());
                    break;
                    
                case "OBTENER_MEDICOS_DISPONIBLES":
                    LocalDate fecha = (LocalDate) input.readObject();
                    output.writeObject(clinica.getMedicosDisponibles(fecha));
                    break;
                    
                case "AGREGAR_CONSULTA":
                    Consulta consulta = (Consulta) input.readObject();
                    clinica.agregarConsulta(consulta);
                    output.writeObject(true);
                    System.out.println("Consulta agregada por: " + direccionCliente);
                    servidor.guardarBackup();
                    servidor.broadcast("ACTUALIZAR_CONSULTAS", this);
                    break;
                    
                case "AGREGAR_ENFERMEDAD_VIGILADA":
                    EnfermedadBajoVigilancia enfermedad = (EnfermedadBajoVigilancia) input.readObject();
                    clinica.agregarEnfermedadVigilada(enfermedad);
                    output.writeObject(true);
                    System.out.println("Enfermedad vigilada agregada por: " + direccionCliente);
                    servidor.guardarBackup();
                    servidor.broadcast("ACTUALIZAR_ENFERMEDADES", this);
                    break;
                    
                case "OBTENER_ENFERMEDADES_VIGILADAS":
                    output.writeObject(clinica.getEnfermedadesVigiladas());
                    break;
                    
                case "OBTENER_CATALOGO_VACUNAS":
                    output.writeObject(clinica.getCatalogoVacunas());
                    break;
                    
                case "EXISTE_PACIENTE":
                    String cedula = (String) input.readObject();
                    boolean existe = clinica.existePaciente(cedula);
                    output.writeObject(existe);
                    break;
                    
                case "BROADCAST_MENSAJE":
                    String mensaje = (String) input.readObject();
                    System.out.println("Broadcast de " + direccionCliente + ": " + mensaje);
                    servidor.broadcast("MENSAJE:" + direccionCliente + ": " + mensaje, this);
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
            
        } catch (ClassNotFoundException e) {
            output.writeObject("ERROR:" + e.getMessage());
        } catch (Exception e) {
            output.writeObject("ERROR:" + e.getMessage());
            e.printStackTrace();
        }
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
            if (input != null) input.close();
            if (output != null) output.close();
            if (clienteSocket != null) clienteSocket.close();
            
            servidor.removerCliente(this);
            System.out.println("Cliente desconectado: " + direccionCliente);
            
        } catch (IOException e) {
            System.err.println("Error cerrando conexion: " + e.getMessage());
        }
    }
    
    public String getDireccionCliente() {
        return direccionCliente;
    }
}