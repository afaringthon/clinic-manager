package logico;

import java.time.LocalDate;

public class Cita extends Visita {
	private String sintomas;
	private boolean esActivo;

	public Cita(Paciente paciente, Medico medico, LocalDate fecha, String sintomas, boolean esActivo) {
		super(paciente, medico, fecha);
		this.sintomas = sintomas;
		this.esActivo = esActivo;
	}

	public Cita(Paciente paciente, Medico medico, LocalDate fecha, String sintomas) {
		super(paciente, medico, fecha);
		this.sintomas = sintomas;
		this.esActivo = true; // Por defecto las citas nuevas son activas
	}

	public String getSintomas() {
		return sintomas;
	}

	public void setSintomas(String sintomas) {
		this.sintomas = sintomas;
	}

	public boolean isEsActivo() {
		return esActivo;
	}

	public void setEsActivo(boolean esActivo) {
		this.esActivo = esActivo;
	}

	// Método para cancelar una cita
	public void cancelar() {
		this.esActivo = false;
	}

	// Método para activar una cita
	public void activar() {
		this.esActivo = true;
	}
}
