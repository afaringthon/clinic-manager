package logico;

import java.util.UUID;

public class Vacuna {
	private String id;
	private String nombre;
	private boolean vacunado;

	public Vacuna(String nombre) {
		this.nombre = nombre;
		this.id = UUID.randomUUID().toString();
		this.vacunado = false;
	}

	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isVacunado() {
		return vacunado;
	}

	public void setVacunado(boolean vacunado) {
		this.vacunado = vacunado;
	}
}
