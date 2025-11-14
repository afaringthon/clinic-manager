package logico;

import java.time.LocalDate;

public class Consulta extends Visita {
	private String sintomas;
	private String diagnostico;
	private EnfermedadBajoVigilancia enfermedadBajoVigilancia;
	private boolean esImportante;
	private String tratamiento;

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

	public boolean EsImportante() {
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
