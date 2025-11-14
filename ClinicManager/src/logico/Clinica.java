package logico;

import java.time.LocalDate;
import java.util.ArrayList;

public class Clinica {
    
    private static int contadorPacientes = 1;
    private static int contadorMedicos = 1;
    private static int contadorCitas = 1;
    private static int contadorConsultas = 1;
    private static int contadorVacunas = 1;
    private static int contadorEnfermedades = 1;
    
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
    
 
    
    public boolean agregarPaciente(Paciente paciente) {
    	if (existePaciente(paciente.getCedula())) {
    		return false; 
    	}
        paciente.setIdPaciente("P-" + contadorPacientes);
        contadorPacientes++;
        pacientes.add(paciente);
        return true; 
    }
    
    public Paciente buscarPacientePorCedula(String cedula) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getCedula().equals(cedula)) {
                return pacientes.get(i);
            }
        }
        return null;
    }
    
    public ArrayList<Paciente> buscarPacientesPorNombre(String nombre) {
        ArrayList<Paciente> encontrados = new ArrayList<>();
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                encontrados.add(pacientes.get(i));
            }
        }
        return encontrados;
    }

    
    public boolean existePaciente(String cedula) {
        return buscarPacientePorCedula(cedula) != null;
    }
    
    public ArrayList<Paciente> getPacientes() {
        return pacientes;
    }
    
    public boolean modificarPaciente(String cedula, String nuevaDireccion, String nuevoTelefono) {
        Paciente p = buscarPacientePorCedula(cedula);
        if (p != null) {
            p.setDireccion(nuevaDireccion);
            p.setTelefono(nuevoTelefono);
            return true;
        }
        return false;
    }
    
    public ArrayList<Cita> getCitasPorPaciente(Paciente paciente) {
        ArrayList<Cita> citasPaciente = new ArrayList<>();
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getPaciente().getCedula().equals(paciente.getCedula())) {
                citasPaciente.add(citas.get(i));
            }
        }
        return citasPaciente;
    }

    
    
    
    public boolean agregarMedico(Medico medico) {
        if(existeMedico(medico.getCedula())) {
        	return false;
        }
    	
    	medico.setIdMedico("M-" + contadorMedicos);
        contadorMedicos++;
        medicos.add(medico);
        
        return true; 
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
    
    public ArrayList<Medico> buscarMedicosPorEspecialidad(String especialidad) {
        ArrayList<Medico> encontrados = new ArrayList<>();
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getEspecialidad().equalsIgnoreCase(especialidad)) {
                encontrados.add(medicos.get(i));
            }
        }
        return encontrados;
    }
    
    public boolean modificarMedico(String cedula, String nuevaEspecialidad, int nuevoMaxCitas) {
        Medico m = buscarMedicoPorCedula(cedula);
        if (m != null) {
            m.setEspecialidad(nuevaEspecialidad);
            m.setMaxCitas(nuevoMaxCitas);
            return true;
        }
        return false;
    }
    
    
    
    public boolean agendarCita(Cita cita) {
        Medico medico = cita.getMedico();
        if (medico.puedeAceptarCita(cita.getFecha())) {
            cita.setId("CIT-" + contadorCitas);
            contadorCitas++;
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
            if (citas.get(i).estadoCita()) {
                activas.add(citas.get(i));
            }
        }
        return activas;
    }
    
    public boolean cancelarCita(String idCita) {
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getId().equals(idCita)) {
                citas.get(i).cancelarCita();  
                return true;
            }
        }
        return false;
    }

    
    public boolean reactivarCita(String idCita) {
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getId().equals(idCita)) {
                citas.get(i).activarCita();  
                return true;
            }
        }
        return false;
    }
    
    public Cita buscarCitaPorId(String id) {
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getId().equals(id)) {
                return citas.get(i);
            }
        }
        return null;
    }
    
    

   

 ArrayList<Cita> getCitasPorMedico(Medico medico) {
        ArrayList<Cita> citasMedico = new ArrayList<>();
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getMedico().getCedula().equals(medico.getCedula()) && 
                citas.get(i).estadoCita()) {
                citasMedico.add(citas.get(i));
            }
        }
        return citasMedico;
    }
 
 public int getTotalCitasMedico(String idMedico) {
	    int total = 0;
	    for (int i = 0; i < citas.size(); i++) {
	        if (citas.get(i).getMedico().getIdMedico().equals(idMedico)) {
	            total++;
	        }
	    }
	    return total;
	}
    
  
    
    public void agregarConsulta(Consulta consulta) {
        consulta.setId("CON-" + contadorConsultas);
        contadorConsultas++;
        consultas.add(consulta);
        consulta.getPaciente().agregarConsulta(consulta);
    }
    
    public ArrayList<Consulta> getConsultas() {
        return consultas;
    }
    
    public Consulta buscarConsultaPorId(String id) {
        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getId().equals(id)) {
                return consultas.get(i);
            }
        }
        return null;
    }
    
    public int getTotalConsultasPorFecha(LocalDate fecha) {
        int total = 0;
        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getFecha().equals(fecha)) {
                total++;
            }
        }
        return total;
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
            if (consultas.get(i).EsImportante()) {
                importantes.add(consultas.get(i));
            }
        }
        return importantes;
    }
    
   
    
    public void agregarVacunaCatalogo(Vacuna vacuna) {
        vacuna.setId("VAC-" + contadorVacunas);
        contadorVacunas++;
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
 
    
    public void agregarEnfermedadVigilada(EnfermedadBajoVigilancia enfermedad) {
        enfermedad.setId("ENF-" + contadorEnfermedades);
        contadorEnfermedades++;
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
    
  
    public boolean eliminarEnfermedadVigilada(String idEnfermedad) {
       
        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getEnfermedadBajoVigilancia() != null &&
                consultas.get(i).getEnfermedadBajoVigilancia().getId().equals(idEnfermedad)) {
                return false;  
            }
        }
        
       
        for (int i = 0; i < enfermedadesVigiladas.size(); i++) {
            if (enfermedadesVigiladas.get(i).getId().equals(idEnfermedad)) {
                enfermedadesVigiladas.remove(i);
                return true;
            }
        }
        return false;
    }
}