package logico;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;

public class Clinica implements Serializable {
	private static final long serialVersionUID = 1L;
    private static Clinica instancia = null;
    private ArrayList<Paciente> pacientes;
    private ArrayList<Medico> medicos;
    private ArrayList<Cita> citas;
    private ArrayList<Consulta> consultas;
    private ArrayList<Vacuna> catalogoVacunas;
    private ArrayList<EnfermedadBajoVigilancia> enfermedadesVigiladas;
    
    private static int contadorPacientes = 1;
    private static int contadorMedicos = 1;
    private static int contadorConsultas = 1;
    private static int contadorVacunas = 1;
    private static int contadorEnfermedades = 1;
    private static int contadorCitas = 1;
    
    private Clinica() {
        pacientes = new ArrayList<>();
        medicos = new ArrayList<>();
        citas = new ArrayList<>();
        consultas = new ArrayList<>();
        catalogoVacunas = new ArrayList<>();
        enfermedadesVigiladas = new ArrayList<>();
    }
    
    public static Clinica getInstancia()
    {
    	if (instancia == null)
    	{
    		instancia = new Clinica();
    	}
    	return instancia;
    }
    
    public void agregarPaciente(String nombre, String apellido, int edad, String cedula,
    		String sexo, float peso, float estatura, String tipoSangre)
    {
    	Paciente paciente = new Paciente("P" + contadorPacientes, nombre, apellido, edad, cedula, sexo, peso,
    			estatura, tipoSangre);
    	pacientes.add(paciente);
    	contadorPacientes++;
    	
    }
    
    public void agregarMedico(String nombre, String apellido, int edad, String cedula, String sexo,
    		String especialidad, int maxCitas)
    {
    	Medico medico = new Medico ("M" + contadorMedicos, nombre, apellido, edad, cedula, sexo, especialidad, maxCitas);
    	medicos.add(medico);
    	contadorMedicos++;
    	
    }
    
    public void agregarCita (String nombre, String apellido, String cedula, Medico medico, LocalDate fecha)
    {
    	Cita cita = new Cita ("C" + contadorCitas, nombre, apellido, cedula, medico, fecha);
    	citas.add(cita);
    	contadorCitas++;	
    }
    
    public void agregarConsulta (String id, Paciente paciente, Medico medico, String sintomas, String diagnostico,
    		EnfermedadBajoVigilancia enfermedadVigilada, boolean esImportante)
    {
    	Consulta consulta = new Consulta(id, paciente, medico, sintomas, diagnostico, enfermedadVigilada,
    			esImportante);
    	consultas.add(consulta);
    	contadorConsultas++;
    	
    }
    
    public void agregarEnfermedadVigilida(String nombre, String descripcion, String gravedad)
    {
    	EnfermedadBajoVigilancia enfermedadVigilada = new EnfermedadBajoVigilancia("E" + contadorEnfermedades,nombre, descripcion, gravedad);
    	enfermedadesVigiladas.add(enfermedadVigilada);
    	contadorEnfermedades++;
    	
    }
    
    public void agregarVacuna (String nombre, String fabricante, float dosis, String descripcion)
    {
    	Vacuna vacuna = new Vacuna ("V" + contadorVacunas, nombre, fabricante, dosis, descripcion);
    	catalogoVacunas.add(vacuna);
    	contadorVacunas++;
    }
    	
    public ArrayList<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(ArrayList<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	public ArrayList<Medico> getMedicos() {
		return medicos;
	}

	public void setMedicos(ArrayList<Medico> medicos) {
		this.medicos = medicos;
	}

	public ArrayList<Cita> getCitas() {
		return citas;
	}

	public void setCitas(ArrayList<Cita> citas) {
		this.citas = citas;
	}

	public ArrayList<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(ArrayList<Consulta> consultas) {
		this.consultas = consultas;
	}

	public ArrayList<Vacuna> getCatalogoVacunas() {
		return catalogoVacunas;
	}

	public void setCatalogoVacunas(ArrayList<Vacuna> catalogoVacunas) {
		this.catalogoVacunas = catalogoVacunas;
	}

	public ArrayList<EnfermedadBajoVigilancia> getEnfermedadesVigiladas() {
		return enfermedadesVigiladas;
	}

	public void setEnfermedadesVigiladas(ArrayList<EnfermedadBajoVigilancia> enfermedadesVigiladas) {
		this.enfermedadesVigiladas = enfermedadesVigiladas;
	}
	
	public Vacuna buscarVacunaPorId(String id)
	{
		if(id == null || catalogoVacunas == null)
		{
			return null;
		}
		
		for (Vacuna v : catalogoVacunas)
		{
			if(id.equalsIgnoreCase(v.getId()))
			{
				return v;
			}
		}
		
		return null;
		
	}
	
	public EnfermedadBajoVigilancia buscarEnfermedadPorId(String id)
	{
		if(id == null || enfermedadesVigiladas == null)
		{
			return null;
		}
		
		for (EnfermedadBajoVigilancia e : enfermedadesVigiladas)
		{
			if(id.equalsIgnoreCase(e.getId()))
			{
				return e;
			}
		}
		
		return null;
		
	}
	
	public Medico buscarMedicoPorId(String id)
	{
		if(id == null || medicos == null)
		{
			return null;
		}
		
		for (Medico m : medicos)
		{
			if(id.equalsIgnoreCase(m.getId()))
			{
				return m;
			}
		}
		
		return null;
		
	}
	
	public Paciente buscarPacientePorId(String id)
	{
		if(id == null || pacientes == null)
		{
			return null;
		}
		
		for (Paciente p : pacientes)
		{
			if(id.equalsIgnoreCase(p.getId()))
			{
				return p;
			}
		}
		
		return null;
		
	}
	
	public boolean verificarCedula(String cedula)
	{
		for (Medico m : medicos)
		{
			if (cedula.equalsIgnoreCase(m.getCedula()))
			{
				return true;
			}
				
			}
		
		for (Paciente p : pacientes)
		{
			if(cedula.equalsIgnoreCase(p.getCedula()))
			{
				return true;
			}
		}
		
		return false;
		
	}
	
	public ArrayList<Medico> getMedicosDisponibles(LocalDate fecha)
	{
		ArrayList<Medico> medicosDisponibles = new ArrayList<>();
		for (Medico m : medicos) {
			int contador = 0;
			if (m == null || !m.isActivo())
			{
				continue;
			}
			
			for (Cita c : citas)
			{
				if(!c.isEsActivo() || c == null)
				{
					continue;
				}
				
				if(c.getMedico() != null && c.getMedico().getId().equalsIgnoreCase(m.getId()) &&
						c.getFecha().equals(fecha))
				{
					contador++;
				}
			}
			
			if ( contador < m.getMaxCitas())
			{
				medicosDisponibles.add(m);
			}
		}
		
		return medicosDisponibles;
		
	}
	
	public boolean medicoPuedeAceptarCita(String medicoId, LocalDate fecha)
	{
		boolean puedeAceptar = false;
		ArrayList<Medico> medicos = getMedicosDisponibles(fecha);
		
		for (Medico c : medicos)
		{
			if(c.getId().equalsIgnoreCase(medicoId))
			{
				puedeAceptar = true;
				return puedeAceptar;
			}
		}
		
		return puedeAceptar;
	}

    // ==================== PACIENTES ====================
    
    /*public boolean agregarPaciente(Paciente paciente) {
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
    
   
    
    public boolean agregarMedico(Medico medico) {
        if (existeMedico(medico.getCedula())) {
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
    
    public Cita buscarCitaPorCedula(String cedula) {
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getCedulaPaciente().equals(cedula) && citas.get(i).estadoCita()) {
                return citas.get(i);
            }
        }
        return null;
    }
    
    public boolean cancelarCitaPorCedula(String cedula) {
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getCedulaPaciente().equals(cedula) && citas.get(i).estadoCita()) {
                citas.get(i).cancelarCita();
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<Cita> getCitasPorPaciente(String cedulaPaciente) {
        ArrayList<Cita> citasPaciente = new ArrayList<>();
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getCedulaPaciente().equals(cedulaPaciente)) {
                citasPaciente.add(citas.get(i));
            }
        }
        return citasPaciente;
    }
    
    public ArrayList<Cita> getCitasPorMedico(Medico medico) {
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
            if (consultas.get(i).isEsImportante()) {
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
    }*/
}