package logico;

import java.time.LocalDate;

public class Cita extends Visita {
	private String sintomas;
	private boolean estadoCita;


	public Cita(Paciente paciente, Medico medico, LocalDate fecha, String sintomas) {
		super(paciente, medico, fecha);
		this.sintomas = sintomas;
		this.estadoCita = true; 
	}

	public String getSintomas() {
		return sintomas;
	}

	public void setSintomas(String sintomas) {
		this.sintomas = sintomas;
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
