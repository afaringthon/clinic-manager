package logico;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Visita {
	
	protected String id;
	protected Paciente paciente;
	protected Medico medico;
	protected LocalDate fecha;
	
	public Visita(Paciente paciente, Medico medico, LocalDate fecha) {
		super();
		this.id = UUID.randomUUID().toString();
		this.paciente = paciente;
		this.medico = medico;
		this.fecha = fecha;
	}
	
	public String getId() {
		return id;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public Medico getMedico() {
		return medico;
	}

	public LocalDate getFecha() {
		return fecha;
	}
	
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

}
