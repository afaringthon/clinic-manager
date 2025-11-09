package logico;

import java.time.LocalDate;

public class Consulta extends Visita {
	private String sintomas;
	private String diagnostico;
	private EnfermedadBajoVigilancia enfermedadBajoVigilancia;
	private boolean esImportante;
	
	public Consulta(Paciente paciente, Medico medico, LocalDate fecha, String sintomas, String diagnostico, 
			EnfermedadBajoVigilancia enfermedadBajoVigilancia) {
		super(paciente, medico, fecha);
		this.sintomas = sintomas;
		this.diagnostico = diagnostico;
		this.enfermedadBajoVigilancia = enfermedadBajoVigilancia;
		this.esImportante = false;
	}
	
	public String getSintomas() {
		return sintomas;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public EnfermedadBajoVigilancia getEnfermedadBajoVigilancia() {
		return enfermedadBajoVigilancia;
	}

	public boolean isEsImportante() {
		return esImportante;
	}	

}
