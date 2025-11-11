package logico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Medico extends Persona {
	private String especialidad;
	private List<Cita> citas;
	private int maxCitas;

	public Medico(String nombre, String apellido, int edad, String cedula, String especialidad, int maxCitas) {
		super(nombre, apellido, edad, cedula);
		this.especialidad = especialidad;
		this.maxCitas = maxCitas;
		this.citas = new ArrayList<>();
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public List<Cita> getCitas() {
		return citas;
	}

	public int getMaxCitas() {
		return maxCitas;
	}

	public void setMaxCitas(int maxCitas) {
		this.maxCitas = maxCitas;
	}

	public boolean puedeAceptarCita(LocalDate fecha) {
		int citasEnFecha = contarCitasEnFecha(fecha);
		return citasEnFecha < maxCitas;
	}

	public int contarCitasEnFecha(LocalDate fecha) {
		int contador = 0;
		for (int i = 0; i < citas.size(); i++) {
			if (citas.get(i).getFecha().equals(fecha) && citas.get(i).isEsActivo()) {
				contador++;
			}
		}
		return contador;
	}

	public boolean agregarCita(Cita cita) {
		if (puedeAceptarCita(cita.getFecha())) {
			citas.add(cita);
			return true;
		}
		return false;
	}

	public List<Cita> getCitasActivas() {
		List<Cita> activas = new ArrayList<>();
		for (int i = 0; i < citas.size(); i++) {
			if (citas.get(i).isEsActivo()) {
				activas.add(citas.get(i));
			}
		}
		return activas;
	}

	public List<Cita> getCitasPorFecha(LocalDate fecha) {
		List<Cita> citasFecha = new ArrayList<>();
		for (int i = 0; i < citas.size(); i++) {
			if (citas.get(i).getFecha().equals(fecha) && citas.get(i).isEsActivo()) {
				citasFecha.add(citas.get(i));
			}
		}
		return citasFecha;
	}

	public int getCitasDisponibles(LocalDate fecha) {
		return maxCitas - contarCitasEnFecha(fecha);
	}
}
