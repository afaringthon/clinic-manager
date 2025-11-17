package logico;

import java.util.ArrayList;

public class Paciente extends Persona {
    private String idPaciente;
    private ArrayList<Consulta> historial;
    private ArrayList<Vacuna> vacunas;
    private String direccion;
    private String telefono;
    
    public Paciente(String nombre, String apellido, int edad, String cedula, String sexo) {
        super(nombre, apellido, edad, cedula, sexo);
        this.historial = new ArrayList<>();
        this.vacunas = new ArrayList<>();
    }
    
    public String getIdPaciente() {
        return idPaciente;
    }
    
    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
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
    
    public boolean tieneVacuna(String nombreVacuna) {
        for (int i = 0; i < vacunas.size(); i++) {
            if (vacunas.get(i).getNombre().equals(nombreVacuna) && vacunas.get(i).estaVacunado()) {
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<Consulta> getConsultasImportantes() {
        ArrayList<Consulta> importantes = new ArrayList<>();
        for (int i = 0; i < historial.size(); i++) {
            if (historial.get(i).isEsImportante()) {
                importantes.add(historial.get(i));
            }
        }
        return importantes;
    }
    
    public ArrayList<Consulta> getConsultasConEnfermedadesVigiladas() {
        ArrayList<Consulta> vigiladas = new ArrayList<>();
        for (int i = 0; i < historial.size(); i++) {
            if (historial.get(i).getEnfermedadBajoVigilancia() != null) {
                vigiladas.add(historial.get(i));
            }
        }
        return vigiladas;
    }
}