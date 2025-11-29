package logico;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.Serializable;


import logico.Clinica;

public class Medico extends Persona implements Serializable {
	private static final long serialVersionUID = 1L;
    private String especialidad;
    private ArrayList<Cita> citas;
    private int maxCitas;
    
    public Medico(String id, String nombre, String apellido, int edad, String cedula, String sexo, String especialidad, int maxCitas) {
        super(id, nombre, apellido, edad, cedula, sexo);
        this.especialidad = especialidad;
        this.maxCitas = maxCitas;
        this.citas = new ArrayList<>();
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getEspecialidad() {
        return especialidad;
    }
    
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    public ArrayList<Cita> getCitas() {
        return citas;
    }
    
    public int getMaxCitas() {
        return maxCitas;
    }
    
    public void setMaxCitas(int maxCitas) {
        this.maxCitas = maxCitas;
    }
    
    
}
