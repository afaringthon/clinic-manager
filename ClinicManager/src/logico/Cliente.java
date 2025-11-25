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
            return true;
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error conectando al servidor: " + e.getMessage());
            return false;
        }
    }
    
    private void iniciarHiloEscucha() {
        hiloEscucha = new Thread(() -> {
            while (conectado && !socket.isClosed()) {
                try {
                    Object mensajeObj = input.readObject();
                    if (mensajeObj instanceof String) {
                        String mensaje = (String) mensajeObj;
                        procesarMensajeServidor(mensaje);
                    }
                } catch (EOFException | SocketException e) {
                    System.out.println("Conexion con servidor perdida");
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
            
            if (contenido.startsWith("ACTUALIZAR_")) {
                System.out.println("Actualizacion requerida: " + contenido);
            }
        } else if (mensaje.equals("SERVIDOR_APAGADO:El servidor se esta apagando")) {
            System.out.println("El servidor se esta apagando. Desconectando...");
            desconectar();
        }
    }

    public void desconectar() {
        conectado = false;
        try {
            if (hiloEscucha != null && hiloEscucha.isAlive()) {
                hiloEscucha.interrupt();
            }
            if (output != null) {
                output.writeObject("DESCONECTAR");
                output.flush();
            }
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null) socket.close();
            
            System.out.println("Desconectado del servidor");
        } catch (IOException e) {
            System.err.println("Error desconectando: " + e.getMessage());
        }
    }


    public boolean agregarMedico(Medico medico) {
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
        try {
            output.writeObject("BROADCAST_MENSAJE");
            output.writeObject(mensaje);
            output.flush();
        } catch (IOException e) {
            System.err.println("Error enviando broadcast: " + e.getMessage());
        }
    }
    
    public boolean estaConectado() {
        return conectado && !socket.isClosed();
    }
}