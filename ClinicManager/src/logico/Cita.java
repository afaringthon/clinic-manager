package logico;

import java.io.Serializable;
import java.time.LocalDate;

public class Cita implements Serializable {
    private static final long serialVersionUID = 8332914665293201379L;
	private String id;
	private String nombre;
	private String apellido;
    private String cedula;
    private Medico medico;
    private LocalDate fecha;
    private boolean esActivo;
    
    public Cita (String id, String nombre, String apellido, String cedula, Medico medico, LocalDate fecha)
    {
    	this.id = id;
    	this.nombre = nombre;
    	this.apellido = apellido;
    	this.cedula = cedula;
    	this.medico = medico;
    	this.fecha = fecha;
    	this.esActivo = true;
    }
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
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
	public boolean isEsActivo() {
		return esActivo;
	}
	public void setEsActivo(boolean esActivo) {
		this.esActivo = esActivo;
	}
    
}