package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;

import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

import logico.Clinica;
import logico.Control;
import logico.Medico;
import logico.Usuario;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class AgregarMedico extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textNombre;
	private JTextField textApellido;
	private JSpinner spinnerMaxCitas;
	private JTextField textCedula;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			AgregarMedico dialog = new AgregarMedico();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AgregarMedico() {
		setResizable(false);
		setModal(true);
		setTitle("Agregar Medico");
		setBounds(100, 100, 453, 516);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		
		Clinica instancia = Clinica.getInstancia();
		Control control = Control.getInstance(); //Usuarios
		JSpinner spinnerEdad;
		JComboBox<String> comboBoxSexo;
		JComboBox<String> comboBoxEspecialidad;
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, "name_101204491147100");
			panel.setLayout(null);
			{
				JLabel lbNombre = new JLabel("Nombre");
				lbNombre.setBounds(82, 38, 56, 16);
				panel.add(lbNombre);
			}
			{
				textNombre = new JTextField();
				textNombre.setBounds(82, 56, 264, 22);
				panel.add(textNombre);
				textNombre.setColumns(10);
			}
			{
				JLabel lbApellido = new JLabel("Apellido");
				lbApellido.setBounds(82, 91, 56, 16);
				panel.add(lbApellido);
			}
			{
				textApellido = new JTextField();
				textApellido.setBounds(82, 109, 264, 22);
				panel.add(textApellido);
				textApellido.setColumns(10);
			}
			{
				JLabel lbEdad = new JLabel("Edad");
				lbEdad.setBounds(82, 245, 56, 16);
				panel.add(lbEdad);
			}
			{
				SpinnerNumberModel edadModel = new SpinnerNumberModel(0, 0, null, 1);
				spinnerEdad = new JSpinner(edadModel);
				spinnerEdad.setBounds(82, 263, 56, 22);
				spinnerEdad.setEditor(new JSpinner.NumberEditor(spinnerEdad, "#")); 
				panel.add(spinnerEdad);
			}
			{
				JLabel lbEspecialidad = new JLabel("Especialidad");
				lbEspecialidad.setBounds(82, 298, 100, 16);
				panel.add(lbEspecialidad);
			}
			{
				comboBoxEspecialidad = new JComboBox<>();
				comboBoxEspecialidad.setBounds(82, 315, 264, 22);
				panel.add(comboBoxEspecialidad);

				String[] especialidades = {
				    "Alergología",
				    "Anestesiología",
				    "Cardiología",
				    "Cirugía General",
				    "Cirugía Cardiovascular",
				    "Cirugía Plástica y Reconstructiva",
				    "Cirugía Pediátrica",
				    "Cirugía Vascular",
				    "Dermatología",
				    "Endocrinología",
				    "Ginecología y Obstetricia",
				    "Gastroenterología",
				    "Geriatría",
				    "Hematología",
				    "Infectología",
				    "Inmunología",
				    "Medicina Familiar y Comunitaria",
				    "Medicina Interna",
				    "Medicina Intensiva",
				    "Medicina Nuclear",
				    "Medicina Preventiva y Salud Pública",
				    "Nefrología",
				    "Neumología",
				    "Neurología",
				    "Neurocirugía",
				    "Nutrición Clínica",
				    "Oftalmología",
				    "Oncología Médica",
				    "Oncología Radioterápica",
				    "Otorrinolaringología",
				    "Pediatría",
				    "Psiquiatría",
				    "Rehabilitación y Medicina Física",
				    "Reumatología",
				    "Traumatología y Ortopedia",
				    "Urología",
				    "Coloproctología",
				    "Cirugía Torácica",
				    "Medicina del Trabajo",
				    "Medicina del Deporte",
				    "Genética Médica",
				    "Patología (Anatomía Patológica)",
				    "Radiología",
				    "Radiología Intervencionista",
				    "Urgencias y Medicina de Emergencias",
				    "Medicina Paliativa",
				    "Toxicología",
				    "Medicina Forense",
				    "Salud Sexual y Reproductiva",
				    "Medicina Estética"
				};

				comboBoxEspecialidad.setModel(new DefaultComboBoxModel<>(especialidades));
			}
			{
				JLabel lbMaxCitas = new JLabel("Cantidad de Citas");
				lbMaxCitas.setBounds(82, 350, 107, 16);
				panel.add(lbMaxCitas);
			}
			{
				SpinnerNumberModel maxCitasModel = new SpinnerNumberModel(0, 0, null, 1);
				spinnerMaxCitas = new JSpinner(maxCitasModel);
				spinnerMaxCitas.setBounds(82, 371, 56, 22);
				panel.add(spinnerMaxCitas);
			}
			
			JLabel lbCedula = new JLabel("Cedula");
			lbCedula.setBounds(82, 141, 56, 16);
			panel.add(lbCedula);
			
			textCedula = new JTextField();
			textCedula.setBounds(82, 159, 264, 22);
			textCedula.setColumns(10);
			
			int maxCedulaLength = 12; // cambia a lo que necesites, o pon Integer.MAX_VALUE para sin límite
			textCedula.setDocument(new javax.swing.text.PlainDocument() {
			    @Override
			    public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
			        if (str == null) return;
			        StringBuilder filtered = new StringBuilder();
			        for (int i = 0; i < str.length(); i++) {
			            char ch = str.charAt(i);
			            if (Character.isDigit(ch)) filtered.append(ch);
			        }
			        int currentLength = getLength();
			        String toInsert = filtered.toString();
			        if (toInsert.length() + currentLength > maxCedulaLength) {
			            int allowed = maxCedulaLength - currentLength;
			            if (allowed <= 0) return;
			            toInsert = toInsert.substring(0, allowed);
			        }
			        super.insertString(offs, toInsert, a);
			    }
			});
			
			panel.add(textCedula);
			
			JLabel lbSexo = new JLabel("Sexo");
			lbSexo.setBounds(82, 194, 56, 16);
			panel.add(lbSexo);
		
			String[] sexo  = {"Masculino", "Femenino"};
			comboBoxSexo = new JComboBox<>();
			comboBoxSexo.setBounds(82, 210, 264, 22);
			comboBoxSexo.setModel(new DefaultComboBoxModel<>(sexo));
			panel.add(comboBoxSexo);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Agregar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String nombre = textNombre.getText().trim();
						String apellido = textApellido.getText().trim();
						int edad = (int) spinnerEdad.getValue();
						String cedula = textCedula.getText().trim();
						String sexo = comboBoxSexo.getSelectedItem().toString();
						String especialidad = comboBoxEspecialidad.getSelectedItem().toString();
						int maxCitas = (int) spinnerMaxCitas.getValue();
						boolean repetido = instancia.verificarCedula(cedula);
						
						if (nombre.isEmpty() || apellido.isEmpty() || edad < 25 || cedula.isEmpty() || sexo.isEmpty() ||
								especialidad.isEmpty() || maxCitas < 1 || repetido)
						{
							if(edad < 25)
							{
								JOptionPane.showMessageDialog(AgregarMedico.this, "Tienes que tener almenos 25 anios", "Alerta", JOptionPane.ERROR_MESSAGE);
								
							}
							
							if (maxCitas < 1)
							{
								JOptionPane.showMessageDialog(AgregarMedico.this, "Tienes que tener almenos 1 cita al dia", "Alerta", JOptionPane.ERROR_MESSAGE);
							}
							
							if(repetido)
							{
								JOptionPane.showMessageDialog(AgregarMedico.this, "Ya hay alguien con esa cedula", "Alerta", JOptionPane.ERROR_MESSAGE);
							}
							
							else
							{
								JOptionPane.showMessageDialog(AgregarMedico.this, "Hay Campos Faltantes", "Alerta", JOptionPane.ERROR_MESSAGE);
							}
						}
						
						else
						{
							Medico medico = instancia.agregarMedico(nombre, apellido, edad, cedula, sexo, especialidad, maxCitas);
							Usuario nuevoMedico = new Usuario(nombre+"-"+medico.getId(), Control.md5("Doctor123456!"), "medico", medico.getId());
							control.regUser(nuevoMedico);
							control.guardarAlDisco();
							JOptionPane.showMessageDialog(AgregarMedico.this, "Dr." + nombre + " Fue Creado" + "Usuario: " + 
							nuevoMedico.getNombreUsuario() + " Clave: Doctor123456!", "Exito", JOptionPane.INFORMATION_MESSAGE);
							dispose();
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
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
