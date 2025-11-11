package logico;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    
    public static void main(String[] args) {
        
        Clinica clinica = new Clinica();
        
        // Registrar médicos
        Medico medico1 = new Medico("Carlos", "Rodríguez", 45, "001-1234567-8", "Cardiología", 10);
        Medico medico2 = new Medico("Ana", "Martínez", 38, "001-2345678-9", "Pediatría", 15);
        Medico medico3 = new Medico("Luis", "González", 50, "001-3456789-0", "Medicina General", 20);
        
        clinica.agregarMedico(medico1);
        clinica.agregarMedico(medico2);
        clinica.agregarMedico(medico3);
        
        System.out.println(clinica.getMedicos().size());
        
        // Definir vacunas
        Vacuna vacunaCovid = new Vacuna("COVID-19");
        Vacuna vacunaInfluenza = new Vacuna("Influenza");
        Vacuna vacunaHepatitisB = new Vacuna("Hepatitis B");
        
        clinica.agregarVacunaCatalogo(vacunaCovid);
        clinica.agregarVacunaCatalogo(vacunaInfluenza);
        clinica.agregarVacunaCatalogo(vacunaHepatitisB);
        
        System.out.println(clinica.getCatalogoVacunas().size());
        
        // Definir enfermedades vigiladas
        EnfermedadBajoVigilancia dengue = new EnfermedadBajoVigilancia("Dengue");
        EnfermedadBajoVigilancia covid = new EnfermedadBajoVigilancia("COVID-19");
        
        clinica.agregarEnfermedadVigilada(dengue);
        clinica.agregarEnfermedadVigilada(covid);
        
        System.out.println(clinica.getEnfermedadesVigiladas().size());
        
        // Registrar paciente
        Paciente paciente1 = new Paciente("María", "López", 30, "001-9876543-2");
        clinica.agregarPaciente(paciente1);
        
        System.out.println(paciente1.getNombre() + " " + paciente1.getApellido());
        
        // Verificar disponibilidad
        LocalDate fechaCita = LocalDate.now();
        ArrayList<Medico> medicosDisponibles = clinica.getMedicosDisponibles(fechaCita);
        System.out.println(medicosDisponibles.size());
        
        // Agendar cita
        Cita cita1 = new Cita(paciente1, medico1, fechaCita, "Dolor de pecho");
        boolean citaAgendada = clinica.agendarCita(cita1);
        
        System.out.println(citaAgendada);
        System.out.println(medico1.getCitasDisponibles(fechaCita));
        
        // Completar datos del paciente
        paciente1.setDireccion("Calle Principal #123");
        paciente1.setTelefono("809-555-1234");
        
        System.out.println(paciente1.getDireccion());
        System.out.println(paciente1.getTelefono());
        
        // Realizar consulta
        Consulta consulta1 = new Consulta(paciente1, medico1, fechaCita, 
            "Dolor de pecho", "Angina de pecho", null);
        consulta1.setTratamiento("Nitroglicerina");
        consulta1.setEsImportante(true);
        clinica.agregarConsulta(consulta1);
        
        System.out.println(consulta1.getDiagnostico());
        System.out.println(consulta1.isEsImportante());
        
        // Registrar vacuna
        Vacuna vacunaPaciente = new Vacuna("COVID-19");
        vacunaPaciente.setVacunado(true);
        paciente1.agregarVacuna(vacunaPaciente);
        
        System.out.println(paciente1.getVacunas().size());
        System.out.println(paciente1.tieneVacuna("COVID-19"));
        
        // Paciente con enfermedad vigilada
        Paciente paciente2 = new Paciente("Rosa", "Fernández", 28, "001-2222222-2");
        clinica.agregarPaciente(paciente2);
        
        Cita cita2 = new Cita(paciente2, medico2, fechaCita, "Fiebre alta");
        clinica.agendarCita(cita2);
        
        Consulta consulta2 = new Consulta(paciente2, medico2, fechaCita,
            "Fiebre alta", "Dengue", dengue);
        consulta2.setTratamiento("Acetaminofén");
        clinica.agregarConsulta(consulta2);
        
        System.out.println(consulta2.getEnfermedadBajoVigilancia().getNombre());
        
        // Consultas vigiladas
        ArrayList<Consulta> consultasVigiladas = clinica.getConsultasConEnfermedadesVigiladas();
        System.out.println(consultasVigiladas.size());
        
        // Consultas importantes
        ArrayList<Consulta> consultasImportantes = clinica.getConsultasImportantes();
        System.out.println(consultasImportantes.size());
        
        // Historial de paciente
        System.out.println(paciente1.getHistorial().size());
        
        // Buscar paciente
        Paciente encontrado = clinica.buscarPacientePorCedula("001-9876543-2");
        System.out.println(encontrado.getNombre());
        
        // Citas del médico
        ArrayList<Cita> citasMedico = (ArrayList<Cita>) medico1.getCitasPorFecha(fechaCita);
        System.out.println(citasMedico.size());
        
        for (int i = 0; i < citasMedico.size(); i++) {
            System.out.println(citasMedico.get(i).getPaciente().getNombre());
        }
    }
}