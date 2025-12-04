package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

import logico.Clinica;
import logico.Paciente;
import logico.Vacuna;

import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;

public class VerDetallesPaciente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	Clinica instancia = Clinica.getInstancia();
    private final Map<JCheckBox, Vacuna> vacunaChecks = new LinkedHashMap<>();
	private JTextField textNombre;
	private JTextField textApellido;
	private JTextField textCedula;
	private JSpinner spinnerEdad;
	private JComboBox comboSexo;
	private JTextField textTelefono;
	private JTextField textDireccion;
	private String createdPacienteId = null;
	static String idPaciente;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			VerDetallesPaciente dialog = new VerDetallesPaciente(idPaciente);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VerDetallesPaciente(String idPaciente) {
		setModal(true);
		setResizable(false);
		setTitle("Detalles Paciente");
		setBounds(100, 100, 508, 654);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		
		Paciente paciente = instancia.buscarPacientePorId(idPaciente);
				
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, "name_9953132040000");
			panel.setLayout(null);
			
			textNombre = new JTextField();
			textNombre.setText(paciente.getNombre());
			textNombre.setEditable(false);
			textNombre.setBounds(119, 50, 270, 22);
			panel.add(textNombre);
			textNombre.setColumns(10);
			
			JLabel lblNewLabel = new JLabel("Nombre");
			lblNewLabel.setBounds(119, 32, 56, 16);
			panel.add(lblNewLabel);
			{
				JLabel lblApellido = new JLabel("Apellido");
				lblApellido.setBounds(119, 85, 56, 16);
				panel.add(lblApellido);
			}
			{
				textApellido = new JTextField();
				textApellido.setText(paciente.getApellido());
				textApellido.setBounds(119, 102, 270, 22);
				panel.add(textApellido);
				textApellido.setColumns(10);
			}
			{
				JLabel lblCedula = new JLabel("Cedula");
				lblCedula.setBounds(119, 137, 56, 16);
				panel.add(lblCedula);
			}
			{
				textCedula = new JTextField();
				textCedula.setEditable(false);
				textCedula.setText(paciente.getCedula());
				textCedula.setEnabled(false);
				textCedula.setBounds(119, 154, 270, 22);
				panel.add(textCedula);
				textCedula.setColumns(10);
			}
			{
				JLabel lblEdad = new JLabel("Edad");
				lblEdad.setBounds(119, 189, 56, 16);
				panel.add(lblEdad);
			}
			
			spinnerEdad = new JSpinner();
			spinnerEdad.setValue(paciente.getEdad());
			spinnerEdad.setEnabled(false);
			spinnerEdad.setBounds(119, 207, 66, 22);
			panel.add(spinnerEdad);
			
			JLabel lblSexo = new JLabel("Sexo");
			lblSexo.setBounds(119, 242, 56, 16);
			panel.add(lblSexo);
			
			String[] opciones = {"Masculino", "Femenino"};
			DefaultComboBoxModel modelo = new DefaultComboBoxModel(opciones);
			this.comboSexo = new JComboBox();
			comboSexo.setEnabled(false);
			this.comboSexo.setModel(modelo);
			this.comboSexo.setBounds(119, 258, 270, 22);
			panel.add(this.comboSexo);
			selectStringItem(comboSexo, paciente.getSexo());

			
			JLabel lbTelefono = new JLabel("Telefono");
			lbTelefono.setBounds(120, 452, 56, 16);
			panel.add(lbTelefono);
			
			textTelefono = new JTextField();
			textTelefono.setText(paciente.getTelefono());
			textTelefono.setEnabled(false);
			textTelefono.setBounds(120, 468, 270, 22);
			panel.add(textTelefono);
			textTelefono.setColumns(10);
			
			JLabel lbDireccion = new JLabel("Direccion");
			lbDireccion.setBounds(120, 503, 56, 16);
			panel.add(lbDireccion);
			
			textDireccion = new JTextField();
			textDireccion.setText(paciente.getDireccion());
			textDireccion.setEnabled(false);
			textDireccion.setBounds(120, 521, 270, 22);
			panel.add(textDireccion);
			textDireccion.setColumns(10);
			
			JLabel lbTipoSangre = new JLabel("Tipo de Sangre");
			lbTipoSangre.setBounds(119, 293, 97, 16);
			panel.add(lbTipoSangre);
			
			String[] opcionesTipoSangre = { "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
			DefaultComboBoxModel modeloComboTipoSangre = new DefaultComboBoxModel(opcionesTipoSangre);
			JComboBox<String> comboBoxTipoSangre = new JComboBox<>(modeloComboTipoSangre);
			comboBoxTipoSangre.setEnabled(false);
			comboBoxTipoSangre.setBounds(119, 310, 270, 22);
			panel.add(comboBoxTipoSangre);
			selectStringItem(comboBoxTipoSangre, paciente.getTipoSangre());
			
			JLabel lbEstatura = new JLabel("Estatura");
			lbEstatura.setBounds(119, 345, 56, 16);
			panel.add(lbEstatura);
			
			JSpinner spinnerEstatura = new JSpinner();
			spinnerEstatura.setEnabled(false);
			spinnerEstatura.setBounds(119, 363, 66, 22);
			panel.add(spinnerEstatura);
			
			JLabel lbPeso = new JLabel("Peso");
			lbPeso.setBounds(119, 398, 56, 16);
			panel.add(lbPeso);
			
			JSpinner spinnerPeso = new JSpinner();
			spinnerPeso.setEnabled(false);
			spinnerPeso.setBounds(118, 414, 67, 22);
			panel.add(spinnerPeso);
			
            	
		}
	}
	
	public static void selectStringItem(JComboBox<String> comboBox, String item) {
        if (item == null || comboBox == null) return;
        comboBox.setSelectedItem(item.trim());
    }
}
