package logico;

import java.time.LocalDate;

public class Cita extends Visita {
	private String sintomas;
	private boolean esActivo;

	public Cita(Paciente paciente, Medico medico, LocalDate fecha, String sintomas, boolean esActivo) {
		super(paciente,medico,fecha);
		this.sintomas = sintomas;
		this.esActivo = esActivo;
	}

	public String getSintomas() {
		return sintomas;
	}

	public boolean isEsActivo() {
		return esActivo;
	}

	public void setEsActivo(boolean esActivo) {
		this.esActivo = esActivo;
	}

}
