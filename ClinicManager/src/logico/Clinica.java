package logico;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;

public class Clinica implements Serializable {
    private static final long serialVersionUID = 8332914665293201379L;
    private static Clinica instancia = null;
    private ArrayList<Paciente> pacientes = new ArrayList<>();
    private ArrayList<Medico> medicos = new ArrayList<>();
    private ArrayList<Cita> citas = new ArrayList<>();
    private ArrayList<Consulta> consultas = new ArrayList<>();
    private ArrayList<Vacuna> catalogoVacunas = new ArrayList<>();
    private ArrayList<EnfermedadBajoVigilancia> enfermedadesVigiladas = new ArrayList<>();
    
    private int contadorPacientes = 1;
    private int contadorMedicos = 1;
    private int contadorConsultas = 1;
    private int contadorVacunas = 1;
    private int contadorEnfermedades = 1;
    private int contadorCitas = 1;
    
    private Clinica() {
    }
    
    public static Clinica getInstancia()
    {
    	if (instancia == null)
    	{
    		instancia = new Clinica();
    	}
    	return instancia;
    }
    
    public static void setInstancia(Clinica c)
    {
    	instancia = c;
    }
    
    
    public Paciente agregarPaciente(String nombre, String apellido, int edad, String cedula,
    		String sexo, float peso, float estatura, String tipoSangre, String direccion, String telefono)
    {
    	Paciente paciente = new Paciente("PAC" + contadorPacientes, nombre, apellido, edad, cedula, sexo, peso,
    			estatura, tipoSangre, direccion, telefono);
    	pacientes.add(paciente);
    	contadorPacientes++;
    	return paciente;
    	
    }
    
    public Medico agregarMedico(String nombre, String apellido, int edad, String cedula, String sexo,
    		String especialidad, int maxCitas)
    {
    	Medico medico = new Medico ("MED" + contadorMedicos, nombre, apellido, edad, cedula, sexo, especialidad, maxCitas);
    	medicos.add(medico);
    	contadorMedicos++;
    	return medico;
    }
    
    public void agregarCita (String nombre, String apellido, String cedula, Medico medico, LocalDate fecha)
    {
    	Cita cita = new Cita ("CIT" + contadorCitas, nombre, apellido, cedula, medico, fecha);
    	citas.add(cita);
    	contadorCitas++;	
    }
    
    public Consulta agregarConsulta (Paciente paciente, Medico medico, String sintomas, String diagnostico,
    		EnfermedadBajoVigilancia enfermedadVigilada, boolean esImportante)
    {
    	Consulta consulta = new Consulta("CON" + contadorConsultas, paciente, medico, sintomas, diagnostico, enfermedadVigilada,
    			esImportante);
    	consultas.add(consulta);
    	contadorConsultas++;
    	return consulta;
    	
    }
    
    public void agregarEnfermedadVigilida(String nombre, String descripcion, String gravedad)
    {
    	EnfermedadBajoVigilancia enfermedadVigilada = new EnfermedadBajoVigilancia("ENF" + contadorEnfermedades,nombre, descripcion, gravedad);
    	enfermedadesVigiladas.add(enfermedadVigilada);
    	contadorEnfermedades++;
    	
    }
    
    public void agregarVacuna (String nombre, String fabricante, float dosis, String descripcion)
    {
    	Vacuna vacuna = new Vacuna ("VAC" + contadorVacunas, nombre, fabricante, dosis, descripcion);
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
	
	public Cita buscarCitaPorId(String CitaId)
	{
		for (Cita c : citas)
		{
			if(c.getId().equalsIgnoreCase(CitaId))
			{
				return c;
			}
		}
		
		return null;
	}
	
	public String buscarPacientePorCedula(String cedula)
	{
		
		for (Paciente p : pacientes)
		{
			if(p.getCedula().equalsIgnoreCase(cedula))
			{
				return p.getId();
			}
		}
		
		return null;
	}
	
	public boolean verificarSiPacienteExiste(String cedula)
	{
		
		for (Paciente p : pacientes)
		{
			if(p.getCedula().equalsIgnoreCase(cedula))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean verificarVacunaRepetida(Paciente paciente, String vacunaId) {
	    if (paciente == null || vacunaId == null) return false;
	    ArrayList<Vacuna> vacunasPaciente = paciente.getVacunas();
	    if (vacunasPaciente == null) return false;
	    for (Vacuna v : vacunasPaciente) {
	        if (v != null && vacunaId.equalsIgnoreCase(v.getId())) {
	            return true;
	        }
	    }
	    return false;
	}
}