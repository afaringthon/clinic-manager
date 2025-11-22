package logico;

import java.time.LocalDate;

public class Cita {
    private String nombre;
    private String cedula;
    private Medico medico;
    private LocalDate fecha;
    private boolean estadoCita;
    
    public Cita(String nombrePaciente, String cedulaPaciente, Medico medico, LocalDate fecha) {
        this.nombre = nombrePaciente;
        this.cedula = cedulaPaciente;
        this.medico = medico;
        this.fecha = fecha;
        this.estadoCita = true;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombrePaciente) {
        this.nombre = nombrePaciente;
    }
    
    public String getCedula() {
        return cedula;
    }
    
    public void setCedula(String cedulaPaciente) {
        this.cedula = cedulaPaciente;
    }
    
    public Medico getMedico() {
        return medico;
    }
    
    public void setMedico(Medico medico) {
        this.medico = medico;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    public boolean estadoCita() {
        return estadoCita;
    }
    
    public void setEstadoCita(boolean estadoCita) {
        this.estadoCita = estadoCita;
    }
    
    public void cancelarCita() {
        this.estadoCita = false;
    }
    
    public void activarCita() {
        this.estadoCita = true;
    }
}