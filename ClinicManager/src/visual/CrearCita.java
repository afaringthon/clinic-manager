package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.Cita;
import logico.Clinica;
import logico.Medico;
import logico.Persona;

import java.awt.CardLayout;
import javax.swing.JLabel;
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

public class CrearCita extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textNombre;
	private JTextField textSintomas;
	private JTextField textCedula;
	//private Clinica instancia = Clinica.getInstancia();
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
		setTitle("Crear Cita");
		setBounds(100, 100, 438, 442);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		
		JSpinner spinnerFecha;
		JComboBox comboBoxDoctor;
		//Clinica instancia = Clinica.getInstancia();
		LocalDate hoy = LocalDate.now();
		Date fechaInicial = Date.from(hoy.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, "name_130227297469400");
			panel.setLayout(null);
			{
				JLabel labelFecha = new JLabel("Fecha");
				labelFecha.setBounds(79, 251, 56, 16);
				panel.add(labelFecha);
			}
			
			
			SpinnerDateModel dateModel = new SpinnerDateModel();
			spinnerFecha = new JSpinner(dateModel);
			dateModel = new SpinnerDateModel(fechaInicial, null, null, Calendar.DAY_OF_MONTH);
			spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy"));
			spinnerFecha.setBounds(79, 267, 251, 22);
			panel.add(spinnerFecha);
			{
				JLabel lbDoctor = new JLabel("Doctor");
				lbDoctor.setBounds(79, 199, 56, 16);
				panel.add(lbDoctor);
			}
			
			comboBoxDoctor = new JComboBox();
			comboBoxDoctor.setBounds(79, 216, 251, 22);
			panel.add(comboBoxDoctor);
			
			JLabel lbNombre = new JLabel("Nombre Completo");
			lbNombre.setBounds(79, 48, 116, 16);
			panel.add(lbNombre);
			
			textNombre = new JTextField();
			textNombre.setBounds(79, 64, 251, 22);
			panel.add(textNombre);
			textNombre.setColumns(10);
			
			JLabel lbSintomas = new JLabel("Sintomas");
			lbSintomas.setBounds(79, 145, 56, 16);
			panel.add(lbSintomas);
			
			textSintomas = new JTextField();
			textSintomas.setBounds(79, 167, 251, 22);
			panel.add(textSintomas);
			textSintomas.setColumns(10);
			
			JLabel lbCedula = new JLabel("Cedula");
			lbCedula.setBounds(79, 99, 56, 16);
			panel.add(lbCedula);
			
			textCedula = new JTextField();
			textCedula.setBounds(79, 117, 251, 22);
			panel.add(textCedula);
			textCedula.setColumns(10);
			
			//private ArrayList<Medico> medicos = instancia.getMedicosDisponibles();
			
			/*for (Medico m : instancia.getMedicos())
			{
				comboBoxDoctor.addItem(m);
			}*/
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						/*Medico medico = (Medico) comboBoxDoctor.getSelectedItem();
						Date tmpfecha = (Date) spinnerFecha.getValue();
						LocalDate fecha = tmpfecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						*/
						/*if ()
						{
							JOptionPane.showMessageDialog(CrearCita.this, "Esta Fecha u Hora no estan Disponibles", "Alerta", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							//Cita nuevaCita = new Cita()
						}*/
						
	
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
