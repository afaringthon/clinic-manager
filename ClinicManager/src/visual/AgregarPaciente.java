package visual;

import java.awt.BorderLayout;

import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

import logico.Paciente;

import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AgregarPaciente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textNombre;
	private JTextField textApellido;
	private JTextField textCedula;
	private JSpinner spinnerEdad;
	private JComboBox comboSexo;
	private JTextField textTelefono;
	private JTextField textDireccion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			AgregarPaciente dialog = new AgregarPaciente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AgregarPaciente() {
		setTitle("Agregar Paciente");
		setBounds(100, 100, 538, 517);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, "name_9953132040000");
			panel.setLayout(null);
			
			textNombre = new JTextField();
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
			spinnerEdad.setBounds(119, 207, 66, 22);
			panel.add(spinnerEdad);
			
			JLabel lblSexo = new JLabel("Sexo");
			lblSexo.setBounds(119, 242, 56, 16);
			panel.add(lblSexo);
			
			String[] opciones = {"Masculino", "Femenino", "Otro"};
			DefaultComboBoxModel modelo = new DefaultComboBoxModel(opciones);
			this.comboSexo = new JComboBox();
			this.comboSexo.setModel(modelo);
			this.comboSexo.setBounds(119, 258, 270, 22);
			panel.add(this.comboSexo);
			
			JLabel lbTelefono = new JLabel("Telefono");
			lbTelefono.setBounds(119, 293, 56, 16);
			panel.add(lbTelefono);
			
			textTelefono = new JTextField();
			textTelefono.setBounds(119, 309, 270, 22);
			panel.add(textTelefono);
			textTelefono.setColumns(10);
			
			JLabel lbDireccion = new JLabel("Direccion");
			lbDireccion.setBounds(119, 344, 56, 16);
			panel.add(lbDireccion);
			
			textDireccion = new JTextField();
			textDireccion.setBounds(119, 362, 270, 22);
			panel.add(textDireccion);
			textDireccion.setColumns(10);
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int edad = (Integer) spinnerEdad.getValue();
						String nombre = textNombre.getText().trim();
						String apellido = textApellido.getText().trim();
						String cedula = textCedula.getText().trim();
						String sexo = comboSexo.getSelectedItem().toString();
						String telefono = textTelefono.getText().trim();
						String direccion = textDireccion.getText().trim();
						
						if (nombre.isEmpty() || apellido.isEmpty() || cedula.isEmpty() || sexo.isEmpty() || edad < 1 || 
								telefono.isEmpty() || direccion.isEmpty())
						{
							JOptionPane.showMessageDialog(AgregarPaciente.this, "Hay Campos Faltantes", "Alerta", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							//Paciente paciente = new Paciente(nombre, apellido, edad, cedula, sexo, direccion, telefono);
							//TODO hay que guardarlo en la lista
						}
						 
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
 }
