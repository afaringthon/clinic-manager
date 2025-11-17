package logico;

import java.time.LocalDate;

public class Consulta {
    private String id;
    private Paciente paciente;
    private Medico medico;
    private LocalDate fecha;
    private String sintomas;
    private String diagnostico;
    private EnfermedadBajoVigilancia enfermedadBajoVigilancia;
    private boolean esImportante;
    private String tratamiento;
    
    public Consulta(Paciente paciente, Medico medico, LocalDate fecha, String sintomas, String diagnostico, 
            EnfermedadBajoVigilancia enfermedadBajoVigilancia) {
        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.enfermedadBajoVigilancia = enfermedadBajoVigilancia;
        this.esImportante = false;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Paciente getPaciente() {
        return paciente;
    }
    
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
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
    
    public String getSintomas() {
        return sintomas;
    }
    
    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }
    
    public String getDiagnostico() {
        return diagnostico;
    }
    
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
    
    public EnfermedadBajoVigilancia getEnfermedadBajoVigilancia() {
        return enfermedadBajoVigilancia;
    }
    
    public void setEnfermedadBajoVigilancia(EnfermedadBajoVigilancia enfermedadBajoVigilancia) {
        this.enfermedadBajoVigilancia = enfermedadBajoVigilancia;
    }
    
    public boolean isEsImportante() {
        return esImportante;
    }
    
    public void setEsImportante(boolean esImportante) {
        this.esImportante = esImportante;
    }
    
    public String getTratamiento() {
        return tratamiento;
    }
    
    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }
}