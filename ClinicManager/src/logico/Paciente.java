package logico;

import java.util.ArrayList;
import java.io.Serializable;

public class Paciente extends Persona implements Serializable {
	private static final long serialVersionUID = 1L;
	private float peso;
	private float estatura;
	private String tipoSangre;
    private ArrayList<Consulta> historial;
    private ArrayList<Vacuna> vacunas;
    private String direccion;
    private String telefono;
    
    public Paciente(String id,String nombre, String apellido, int edad, String cedula, String sexo, float peso, float estatura,
    		String tipoSangre, String direccion, String telefono) {
        super(id, nombre, apellido, edad, cedula, sexo);
        this.peso = peso;
        this.estatura = estatura;
        this.tipoSangre = tipoSangre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.historial = new ArrayList<>();
        this.vacunas = new ArrayList<>();
    }
    
    public ArrayList<Consulta> getHistorial() {
        return historial;
    }
    
    public void setHistorial(ArrayList<Consulta> historial) {
        this.historial = historial;
    }
    
    public ArrayList<Vacuna> getVacunas() {
        return vacunas;
    }
    
    public void setVacunas(ArrayList<Vacuna> vacunas) {
        this.vacunas = vacunas;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public void agregarConsulta(Consulta consulta) {
        historial.add(consulta);
    }
    
    public void agregarVacuna(Vacuna vacuna) {
        vacunas.add(vacuna);
    }

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public float getEstatura() {
		return estatura;
	}

	public void setEstatura(float estatura) {
		this.estatura = estatura;
	}

	public String getTipoSangre() {
		return tipoSangre;
	}

	public void setTipoSangre(String tipoSangre) {
		this.tipoSangre = tipoSangre;
	}
	
	public void agregarConsultaAlHistorial(Consulta consulta)
	{
		historial.add(consulta);
;	}
}