package logico;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
=======
public class Paciente {
	
	private String nombre; 
	private String apellido;
	
	
	public Paciente(String nombre, String apellido) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
	}
>>>>>>> branch 'main' of https://github.com/afaringthon/clinic-manager.git

<<<<<<< HEAD
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
=======
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
>>>>>>> branch 'main' of https://github.com/afaringthon/clinic-manager.git
	}

}
