package logico;

import java.time.LocalDate;
import java.util.ArrayList;

public class Clinica {
    
    private ArrayList<Paciente> pacientes;
    private ArrayList<Medico> medicos;
    private ArrayList<Cita> citas;
    private ArrayList<Consulta> consultas;
    private ArrayList<Vacuna> catalogoVacunas;
    private ArrayList<EnfermedadBajoVigilancia> enfermedadesVigiladas;
    
    public Clinica() {
        pacientes = new ArrayList<>();
        medicos = new ArrayList<>();
        citas = new ArrayList<>();
        consultas = new ArrayList<>();
        catalogoVacunas = new ArrayList<>();
        enfermedadesVigiladas = new ArrayList<>();
    }
    
    // Métodos para Pacientes
    
    public void agregarPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }
    
    public Paciente buscarPacientePorCedula(String cedula) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getCedula().equals(cedula)) {
                return pacientes.get(i);
            }
        }
        return null;
    }
    
    public boolean existePaciente(String cedula) {
        return buscarPacientePorCedula(cedula) != null;
    }
    
    public ArrayList<Paciente> getPacientes() {
        return pacientes;
    }
    
    // Métodos para Médicos
    
    public void agregarMedico(Medico medico) {
        medicos.add(medico);
    }
    
    public Medico buscarMedicoPorCedula(String cedula) {
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getCedula().equals(cedula)) {
                return medicos.get(i);
            }
        }
        return null;
    }
    
    public boolean existeMedico(String cedula) {
        return buscarMedicoPorCedula(cedula) != null;
    }
    
    public ArrayList<Medico> getMedicos() {
        return medicos;
    }
    
    public ArrayList<Medico> getMedicosDisponibles(LocalDate fecha) {
        ArrayList<Medico> disponibles = new ArrayList<>();
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).puedeAceptarCita(fecha)) {
                disponibles.add(medicos.get(i));
            }
        }
        return disponibles;
    }
    
    // Métodos para Citas
    
    public boolean agendarCita(Cita cita) {
        Medico medico = cita.getMedico();
        if (medico.puedeAceptarCita(cita.getFecha())) {
            citas.add(cita);
            medico.agregarCita(cita);
            return true;
        }
        return false;
    }
    
    public ArrayList<Cita> getCitas() {
        return citas;
    }
    
    public ArrayList<Cita> getCitasActivas() {
        ArrayList<Cita> activas = new ArrayList<>();
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).isEsActivo()) {
                activas.add(citas.get(i));
            }
        }
        return activas;
    }
    
    public ArrayList<Cita> getCitasPorMedico(Medico medico) {
        ArrayList<Cita> citasMedico = new ArrayList<>();
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getMedico().getCedula().equals(medico.getCedula()) && 
                citas.get(i).isEsActivo()) {
                citasMedico.add(citas.get(i));
            }
        }
        return citasMedico;
    }
    
    // Métodos para Consultas
    
    public void agregarConsulta(Consulta consulta) {
        consultas.add(consulta);
        consulta.getPaciente().agregarConsulta(consulta);
    }
    
    public ArrayList<Consulta> getConsultas() {
        return consultas;
    }
    
    public ArrayList<Consulta> getConsultasConEnfermedadesVigiladas() {
        ArrayList<Consulta> vigiladas = new ArrayList<>();
        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getEnfermedadBajoVigilancia() != null) {
                vigiladas.add(consultas.get(i));
            }
        }
        return vigiladas;
    }
    
    public ArrayList<Consulta> getConsultasImportantes() {
        ArrayList<Consulta> importantes = new ArrayList<>();
        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).isEsImportante()) {
                importantes.add(consultas.get(i));
            }
        }
        return importantes;
    }
    
    // Métodos para Vacunas (catálogo)
    
    public void agregarVacunaCatalogo(Vacuna vacuna) {
        catalogoVacunas.add(vacuna);
    }
    
    public Vacuna buscarVacunaPorNombre(String nombre) {
        for (int i = 0; i < catalogoVacunas.size(); i++) {
            if (catalogoVacunas.get(i).getNombre().equalsIgnoreCase(nombre)) {
                return catalogoVacunas.get(i);
            }
        }
        return null;
    }
    
    public ArrayList<Vacuna> getCatalogoVacunas() {
        return catalogoVacunas;
    }
    
    // Métodos para Enfermedades Vigiladas
    
    public void agregarEnfermedadVigilada(EnfermedadBajoVigilancia enfermedad) {
        enfermedadesVigiladas.add(enfermedad);
    }
    
    public EnfermedadBajoVigilancia buscarEnfermedadPorNombre(String nombre) {
        for (int i = 0; i < enfermedadesVigiladas.size(); i++) {
            if (enfermedadesVigiladas.get(i).getNombre().equalsIgnoreCase(nombre)) {
                return enfermedadesVigiladas.get(i);
            }
        }
        return null;
    }
    
    public boolean esEnfermedadVigilada(String nombre) {
        return buscarEnfermedadPorNombre(nombre) != null;
    }
    
    public ArrayList<EnfermedadBajoVigilancia> getEnfermedadesVigiladas() {
        return enfermedadesVigiladas;
    }
    
    public void eliminarEnfermedadVigilada(EnfermedadBajoVigilancia enfermedad) {
        enfermedadesVigiladas.remove(enfermedad);
    }
}