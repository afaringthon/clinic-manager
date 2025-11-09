package logico;

import java.util.List;

public class Medico extends Persona {
	private String especialidad;
	private List<Cita> citas;
	private int maxCitas;

	public Medico(String nombre, String apellido, int edad, String especialidad, int maxCitas) {
		super(nombre, apellido, edad);
		this.especialidad = especialidad;
		this.maxCitas = maxCitas;
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

}
