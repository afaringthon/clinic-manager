package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.Clinica;
import logico.Medico;
import logico.Persona;
import logico.Visita;

import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;

public class CrearCita extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
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
		setBounds(100, 100, 451, 342);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		
		JSpinner spinnerFecha;
		JComboBox comboBoxDoctor;
		Clinica instancia = Clinica.getInstancia();
		
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, "name_130227297469400");
			panel.setLayout(null);
			{
				JLabel labelFecha = new JLabel("Fecha");
				labelFecha.setBounds(92, 122, 56, 16);
				panel.add(labelFecha);
			}
			
			SpinnerDateModel dateModel = new SpinnerDateModel();
			spinnerFecha = new JSpinner(dateModel);
			spinnerFecha.setModel(new SpinnerDateModel(new Date(1763352000000L), new Date(1763352000000L), null, Calendar.DAY_OF_YEAR));
			spinnerFecha.setBounds(92, 138, 244, 22);
			panel.add(spinnerFecha);
			{
				JLabel lbDoctor = new JLabel("Doctor");
				lbDoctor.setBounds(92, 70, 56, 16);
				panel.add(lbDoctor);
			}
			
			comboBoxDoctor = new JComboBox();
			comboBoxDoctor.setBounds(92, 87, 244, 22);
			panel.add(comboBoxDoctor);
			
			for (Persona p : instancia.getUsuarios())
			{
				if(p instanceof Medico)
				{
					comboBoxDoctor.addItem(p); //Agregar Doctor
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
	
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
