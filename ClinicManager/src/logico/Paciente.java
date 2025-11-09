package logico;

import java.util.ArrayList;
import java.util.List;

public class Paciente extends Persona {
	private List<Consulta> historial  = new ArrayList<>();
	private List<Vacuna> vacunas = new ArrayList<>();

	public Paciente(String nombre, String apellido, int edad) {
		super(nombre, apellido, edad);
	}

	public List<Consulta> getHistorial() {
		return historial;
	}

	public void setHistorial(List<Consulta> historial) {
		this.historial = historial;
	}

	public List<Vacuna> getVacunas() {
		return vacunas;
	}

	public void setVacunas(List<Vacuna> vacunas) {
		this.vacunas = vacunas;
	}

}
