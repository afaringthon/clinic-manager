package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.CardLayout;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logico.Clinica;
import logico.Cita;
import logico.Consulta;
import logico.EnfermedadBajoVigilancia;
import logico.Medico;
import logico.Paciente;

/**
 * Clean and simple Reportes dialog showing 4 charts.
 *
 * Notes:
 * - Simple for-each loops; no debug prints.
 * - "Citas por Médico" counts all citas (no date filter) so all doctors with citas are shown.
 * - If you want to re-enable date filtering, see the comments in buildCitasPorMedico.
 */
public class Reportes extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public static void main(String[] args) {
		try {
			Reportes dialog = new Reportes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Reportes() {
		setTitle("Reportes");
		setBounds(100, 100, 1280, 820);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));

		// default date range (used by some charts if desired)
		LocalDate hasta = LocalDate.now();
		LocalDate desde = hasta.minusDays(30);

		Clinica instancia = Clinica.getInstancia();

		JPanel panel = new JPanel();
		contentPanel.add(panel, "chartsPanel");
		panel.setLayout(new GridLayout(2, 2, 12, 12));

		// Chart 1: Pie - Citas por estado (Activa / Inactiva) - uses date range
		DefaultPieDataset pieData = buildCitasPorEstado(desde, hasta, instancia);
		JFreeChart pieChart = ChartFactory.createPieChart("Citas por Estado (últimos 30 días)", pieData, true, true,
				false);
		ChartPanel piePanel = new ChartPanel(pieChart);
		piePanel.setPreferredSize(new Dimension(600, 360));
		panel.add(piePanel);

		// Chart 2: Bar - Citas por Médico (counts all citas, no date filter)
		DefaultCategoryDataset barData = buildCitasPorMedico(instancia);
		JFreeChart barChart = ChartFactory.createBarChart("Citas por Médico", "Médico", "Citas", barData);
		ChartPanel barPanel = new ChartPanel(barChart);
		barPanel.setPreferredSize(new Dimension(600, 360));
		panel.add(barPanel);

		// Chart 3: Bar - Doctores por Especialidad
		DefaultCategoryDataset medEspData = buildMedicosPorEspecialidad(instancia);
		JFreeChart medEspChart = ChartFactory.createBarChart("Doctores por Especialidad", "Especialidad",
				"Cantidad de Doctores", medEspData);
		ChartPanel medEspPanel = new ChartPanel(medEspChart);
		medEspPanel.setPreferredSize(new Dimension(600, 360));
		panel.add(medEspPanel);

		// Chart 4: Bar - Consultas por Enfermedad Vigilada (no date filter)
		DefaultCategoryDataset enfData = buildConsultasPorEnfermedad(instancia);
		JFreeChart enfChart = ChartFactory.createBarChart("Consultas por Enfermedad Vigilada", "Enfermedad",
				"Consultas", enfData);
		ChartPanel enfPanel = new ChartPanel(enfChart);
		enfPanel.setPreferredSize(new Dimension(600, 360));
		panel.add(enfPanel);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton cancelButton = new JButton("Salir");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(ev -> dispose());
		buttonPane.add(cancelButton);
	}

	/* -----------------------
	 * Simple dataset builders
	 * ----------------------- */

	// 1) Pie dataset: count citas by Activa/Inactiva using isEsActivo() (date filtered last 30 days)
	private DefaultPieDataset buildCitasPorEstado(LocalDate desde, LocalDate hasta, Clinica instancia) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		if (instancia == null) return dataset;
		List<Cita> citas = instancia.getCitas();
		if (citas == null) return dataset;

		Map<String, Integer> counts = new HashMap<>();
		for (Cita c : citas) {
			if (c == null) continue;
			LocalDate f = c.getFecha();
			if (f == null) continue;
			if (f.isBefore(desde) || f.isAfter(hasta)) continue;
			String key = c.isEsActivo() ? "Activa" : "Inactiva";
			counts.put(key, counts.getOrDefault(key, 0) + 1);
		}
		for (Map.Entry<String, Integer> e : counts.entrySet()) {
			dataset.setValue(e.getKey(), e.getValue());
		}
		return dataset;
	}

	// 2) Bar dataset: count citas by medico (counts all citas; no date filter here)
	private DefaultCategoryDataset buildCitasPorMedico(Clinica instancia) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if (instancia == null) return dataset;
		List<Cita> citas = instancia.getCitas();
		if (citas == null) return dataset;

		Map<String, Integer> countsById = new HashMap<>();
		Map<String, String> labelById = new HashMap<>();

		for (Cita c : citas) {
			if (c == null) continue;
			Medico m = c.getMedico();
			String id = (m != null) ? m.getId() : null;
			String name = (m != null) ? m.getNombre() : null;

			String key;
			String label;
			if (id != null && !id.trim().isEmpty()) {
				key = id.trim();
				label = (name != null && !name.trim().isEmpty()) ? name.trim() + " (" + key + ")" : key;
			} else if (name != null && !name.trim().isEmpty()) {
				key = "name:" + name.trim();
				label = name.trim();
			} else {
				key = "sin_medico";
				label = "Sin Médico";
			}

			labelById.putIfAbsent(key, label);
			countsById.put(key, countsById.getOrDefault(key, 0) + 1);
		}

		for (Map.Entry<String, Integer> e : countsById.entrySet()) {
			String label = labelById.get(e.getKey());
			dataset.addValue(e.getValue(), "Citas", label);
		}
		return dataset;
	}

	// 3) Bar dataset: count doctors by specialty
	private DefaultCategoryDataset buildMedicosPorEspecialidad(Clinica instancia) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if (instancia == null) return dataset;
		List<Medico> medicos = instancia.getMedicos();
		if (medicos == null) return dataset;

		Map<String, Integer> counts = new HashMap<>();
		for (Medico m : medicos) {
			if (m == null) continue;
			String esp = m.getEspecialidad();
			if (esp == null) esp = "SIN ESPECIALIDAD";
			esp = esp.trim();
			counts.put(esp, counts.getOrDefault(esp, 0) + 1);
		}

		for (Map.Entry<String, Integer> e : counts.entrySet()) {
			dataset.addValue(e.getValue(), "Doctores", e.getKey());
		}
		return dataset;
	}

	// 4) Bar dataset: count consultas by enfermedad vigilada (no date filter)
	private DefaultCategoryDataset buildConsultasPorEnfermedad(Clinica instancia) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if (instancia == null) return dataset;
		List<Consulta> consultas = instancia.getConsultas();
		if (consultas != null) {
			Map<String, Integer> counts = new HashMap<>();
			for (Consulta q : consultas) {
				if (q == null) continue;
				EnfermedadBajoVigilancia ev = q.getEnfermedadVigilada();
				if (ev == null) continue;
				String nombre = ev.getNombre();
				if (nombre == null) nombre = "SIN NOMBRE";
				nombre = nombre.trim();
				counts.put(nombre, counts.getOrDefault(nombre, 0) + 1);
			}
			for (Map.Entry<String, Integer> e : counts.entrySet()) {
				dataset.addValue(e.getValue(), "Consultas", e.getKey());
			}
			return dataset;
		}

		List<Paciente> pacientes = instancia.getPacientes();
		if (pacientes == null) return dataset;

		Map<String, Integer> counts = new HashMap<>();
		for (Paciente p : pacientes) {
			if (p == null) continue;
			List<Consulta> historial = p.getHistorial();
			if (historial == null) continue;
			for (Consulta q : historial) {
				if (q == null) continue;
				EnfermedadBajoVigilancia ev = q.getEnfermedadVigilada();
				if (ev == null) continue;
				String nombre = ev.getNombre();
				if (nombre == null) nombre = "SIN NOMBRE";
				nombre = nombre.trim();
				counts.put(nombre, counts.getOrDefault(nombre, 0) + 1);
			}
		}
		for (Map.Entry<String, Integer> e : counts.entrySet()) {
			dataset.addValue(e.getValue(), "Consultas", e.getKey());
		}
		return dataset;
	}
}