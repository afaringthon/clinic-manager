package logico;

import java.io.Serializable;
import java.time.LocalDate;

public class Cita implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nombrePaciente;
    private String cedulaPaciente;
    private Medico medico;
    private LocalDate fecha;
    private boolean estadoCita;
    
    public Cita(String nombrePaciente, String cedulaPaciente, Medico medico, LocalDate fecha) {
        this.nombrePaciente = nombrePaciente;
        this.cedulaPaciente = cedulaPaciente;
        this.medico = medico;
        this.fecha = fecha;
        this.estadoCita = true;
    }
    
    public String getNombrePaciente() {
        return nombrePaciente;
    }
    
    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }
    
    public String getCedulaPaciente() {
        return cedulaPaciente;
    }
    
    public void setCedulaPaciente(String cedulaPaciente) {
        this.cedulaPaciente = cedulaPaciente;
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