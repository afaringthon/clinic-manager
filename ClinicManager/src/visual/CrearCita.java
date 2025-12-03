package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.Cita;
import logico.Clinica;
import logico.Medico;
import logico.Persona;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.awt.event.ActionEvent;

import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class CrearCita extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textNombre;
	private JTextField textApellido;
	private JComboBox<Medico> comboBoxDoctor;
	private JSpinner spinnerFecha;
	Clinica instancia = Clinica.getInstancia();
	private JTextField textCedula;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			CrearCita dialog = new CrearCita();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CrearCita() {
		setResizable(false);
		setModal(true);
		setTitle("Crear Cita");
		setBounds(100, 100, 444, 433);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		
		JSpinner spinnerFecha;
		LocalDate hoy = LocalDate.now();
		Date fechaInicial = Date.from(hoy.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, "name_130227297469400");
			panel.setLayout(null);
			{
				JLabel labelFecha = new JLabel("Fecha");
				labelFecha.setBounds(79, 205, 56, 16);
				panel.add(labelFecha);
			}
			
			comboBoxDoctor = new JComboBox();
			comboBoxDoctor.setBounds(79, 277, 274, 22);
			
			comboBoxDoctor.setRenderer(new DefaultListCellRenderer() {
				@Override
				public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
					super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					if (value instanceof Medico) {
						Medico m = (Medico) value;
						setText(m.getNombre() + " " + m.getApellido() + " -" + m.getEspecialidad());
					} else {
						setText("");
					}
					return this;
				}
			});
			
			panel.add(comboBoxDoctor);
			
			
			SpinnerDateModel dateModel = new SpinnerDateModel(fechaInicial, null, null, Calendar.DAY_OF_MONTH);
			spinnerFecha = new JSpinner(dateModel);
			spinnerFecha.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					try {
						 spinnerFecha.commitEdit();
					}catch(java.text.ParseException ex) {
						return;
					}
					
					java.util.Date date = (java.util.Date) spinnerFecha.getValue();
				    java.time.LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
				    
				    actualizarComboMedicos(localDate);
				
				    
				    
				}
			});
			dateModel = new SpinnerDateModel(fechaInicial, null, null, Calendar.DAY_OF_MONTH);
			spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy"));
			spinnerFecha.setBounds(79, 221, 274, 22);
			panel.add(spinnerFecha);
			{
				JLabel lbDoctor = new JLabel("Doctor");
				lbDoctor.setBounds(79, 260, 56, 16);
				panel.add(lbDoctor);
			}
			
			JLabel lbNombre = new JLabel("Nombre");
			lbNombre.setBounds(79, 48, 116, 16);
			panel.add(lbNombre);
			
			textNombre = new JTextField();
			textNombre.setBounds(79, 64, 275, 22);
			panel.add(textNombre);
			textNombre.setColumns(10);
			
			JLabel lbApellido = new JLabel("Apellido");
			lbApellido.setBounds(79, 99, 56, 16);
			panel.add(lbApellido);
			
			textApellido = new JTextField();
			textApellido.setBounds(78, 118, 276, 22);
			panel.add(textApellido);
			textApellido.setColumns(10);
			
			JLabel lbCedula = new JLabel("Cedula");
			lbCedula.setBounds(79, 153, 56, 16);
			panel.add(lbCedula);
			
			textCedula = new JTextField();
			textCedula.setBounds(79, 170, 274, 22);
			panel.add(textCedula);
			textCedula.setColumns(10);
			
			actualizarComboMedicos(hoy);
	
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnAgregar = new JButton("Agregar");
				btnAgregar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String nombre = textNombre.getText().trim();
						String apellido = textApellido.getText().trim();
						Medico medico = (Medico) comboBoxDoctor.getSelectedItem();
						String cedula = textCedula.getText().trim();
						Date tmpfecha = (Date) spinnerFecha.getValue();
						LocalDate fecha = tmpfecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					
						if(nombre.isEmpty() || apellido.isEmpty() || cedula.isEmpty() || medico == null || fecha == null ||
								!fecha.isAfter(hoy.minusDays(1)))
						{
						
							if(!fecha.isAfter(hoy.minusDays(1)))
							{
								JOptionPane.showMessageDialog(CrearCita.this, "Fecha no puede ser antes de hoy", "Alerta", JOptionPane.ERROR_MESSAGE);
								return;

							}
							JOptionPane.showMessageDialog(CrearCita.this, "Hay Campos Vacios", "Alerta", JOptionPane.ERROR_MESSAGE);
							
						}
						
						else
						{
							instancia.agregarCita(nombre, apellido, cedula, medico, fecha);
							JOptionPane.showMessageDialog(CrearCita.this, "Cita creada para: " + fecha.toString(), "Informacion", JOptionPane.INFORMATION_MESSAGE);
							dispose();
						}
						
	
					}
				});
				btnAgregar.setActionCommand("OK");
				buttonPane.add(btnAgregar);
				getRootPane().setDefaultButton(btnAgregar);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private void actualizarComboMedicos(LocalDate fecha)
	{
		ArrayList<Medico> medicosDisponibles = instancia.getMedicosDisponibles(fecha);
		DefaultComboBoxModel<Medico> model = new DefaultComboBoxModel<>();
		if (medicosDisponibles != null) {
			for (Medico m : medicosDisponibles) {
				if (m != null && m.isActivo()) {
					model.addElement(m);
				}
			}
		}
		comboBoxDoctor.setModel(model);
		
	}
}
