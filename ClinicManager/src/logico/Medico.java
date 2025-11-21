package logico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.io.Serializable;

public class Medico extends Persona implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private String idMedico;
    private String especialidad;
    private ArrayList<Cita> citas;
    private int maxCitas;
    
    public Medico(String nombre, String apellido, int edad, String cedula, String sexo, String especialidad, int maxCitas) {
        super(nombre, apellido, edad, cedula, sexo);
        this.especialidad = especialidad;
        this.maxCitas = maxCitas;
        this.citas = new ArrayList<>();
    }
    
    public String getIdMedico() {
        return idMedico;
    }
    
    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
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
    
    public boolean puedeAceptarCita(LocalDate fecha) {
        int cantCitasEnDia = contarCitasEnDia(fecha);
        return cantCitasEnDia < maxCitas;
    }
    
    public int contarCitasEnDia(LocalDate fecha) {
        int contador = 0;
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getFecha().equals(fecha) && citas.get(i).estadoCita()) {
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
    
    public ArrayList<Cita> getCitasActivas() {
        ArrayList<Cita> activas = new ArrayList<>();
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).estadoCita()) {
                activas.add(citas.get(i));
            }
        }
        return activas;
    }
    
    public ArrayList<Cita> getCitasPorFecha(LocalDate fecha) {
        ArrayList<Cita> citasFecha = new ArrayList<>();
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getFecha().equals(fecha) && citas.get(i).estadoCita()) {
                citasFecha.add(citas.get(i));
            }
        }
        return citasFecha;
    }
    
    public int capacidadDisponible(LocalDate fecha) {
        return maxCitas - contarCitasEnDia(fecha);
    }
}