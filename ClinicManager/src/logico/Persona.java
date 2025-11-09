package logico;
import java.util.UUID;

public abstract class Persona {
	protected String id;
	protected String nombre;
	protected String apellido;
	protected int edad;
	
	public Persona(String nombre, String apellido, int edad) {
		super();
		this.id = UUID.randomUUID().toString();
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}


}
