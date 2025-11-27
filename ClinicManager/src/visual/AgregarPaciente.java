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

public class AgregarPaciente extends JDialog {

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
		setResizable(false);
		setTitle("Agregar Paciente");
		setBounds(100, 100, 514, 800);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		
		
		final ArrayList<JCheckBox> vacunasCheckboxes = new ArrayList<>();
		
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
			
			String[] opciones = {"Masculino", "Femenino"};
			DefaultComboBoxModel modelo = new DefaultComboBoxModel(opciones);
			this.comboSexo = new JComboBox();
			this.comboSexo.setModel(modelo);
			this.comboSexo.setBounds(119, 258, 270, 22);
			panel.add(this.comboSexo);
			
			JLabel lbTelefono = new JLabel("Telefono");
			lbTelefono.setBounds(120, 452, 56, 16);
			panel.add(lbTelefono);
			
			textTelefono = new JTextField();
			textTelefono.setBounds(120, 468, 270, 22);
			panel.add(textTelefono);
			textTelefono.setColumns(10);
			
			JLabel lbDireccion = new JLabel("Direccion");
			lbDireccion.setBounds(120, 503, 56, 16);
			panel.add(lbDireccion);
			
			textDireccion = new JTextField();
			textDireccion.setBounds(120, 521, 270, 22);
			panel.add(textDireccion);
			textDireccion.setColumns(10);
			
			JLabel lbTipoSangre = new JLabel("Tipo de Sangre");
			lbTipoSangre.setBounds(119, 293, 97, 16);
			panel.add(lbTipoSangre);
			
			String[] opcionesTipoSangre = { "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
			JComboBox<String> comboBoxTipoSangre = new JComboBox<>(opcionesTipoSangre);
			comboBoxTipoSangre.setBounds(119, 310, 270, 22);
			panel.add(comboBoxTipoSangre);
			
			JLabel lbEstatura = new JLabel("Estatura");
			lbEstatura.setBounds(119, 345, 56, 16);
			panel.add(lbEstatura);
			
			JSpinner spinnerEstatura = new JSpinner();
			spinnerEstatura.setBounds(119, 363, 66, 22);
			panel.add(spinnerEstatura);
			
			JLabel lbPeso = new JLabel("Peso");
			lbPeso.setBounds(119, 398, 56, 16);
			panel.add(lbPeso);
			
			JSpinner spinnerPeso = new JSpinner();
			spinnerPeso.setBounds(118, 414, 67, 22);
			panel.add(spinnerPeso);
			
			JLabel lbVacunas = new JLabel("Vacunas");
			lbVacunas.setBounds(119, 554, 56, 16);
			panel.add(lbVacunas);
			
			JPanel vaccinesPanel = new JPanel();
            vaccinesPanel.setLayout(new BoxLayout(vaccinesPanel, BoxLayout.Y_AXIS));
            vaccinesPanel.setBorder(new EmptyBorder(4,4,4,4));
            
            JScrollPane scrollVacunas = new JScrollPane(vaccinesPanel);
            scrollVacunas.setBounds(120, 575, 270, 100);
            panel.add(scrollVacunas);
			
            ArrayList<Vacuna> catalogoVacunas = instancia.getCatalogoVacunas();
            int y = 700;
            int x = 120;
            int espaciado = 32;
            
            if(catalogoVacunas == null || catalogoVacunas.isEmpty())
            {
            	JCheckBox checkBox = new JCheckBox("No hay Vacunas");
                checkBox.setEnabled(false);
                checkBox.setBounds(x, y, 300, 25);
                vaccinesPanel.add(checkBox);
            }
            else
            {
            	for(Vacuna v : catalogoVacunas)
            	{
            		if(v == null || !v.isEsActivo())
            		{
            			continue;
            		}
            		
            		JCheckBox checkBox = new JCheckBox(v.getNombre());
                    checkBox.setBounds(x, y, 300, 25);
                    vacunaChecks.put(checkBox, v);
                    vaccinesPanel.add(checkBox);
            		
                    y += espaciado;
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
						int edad = (Integer) spinnerEdad.getValue();
						String nombre = textNombre.getText().trim();
						String apellido = textApellido.getText().trim();
						String cedula = textCedula.getText().trim();
						String sexo = comboSexo.getSelectedItem().toString();
						String telefono = textTelefono.getText().trim();
						String direccion = textDireccion.getText().trim();
						String tipoSangre = comboBoxTipoSangre.getSelectedItem().toString();
						Number p = (Number) spinnerPeso.getValue();
						float peso = p.floatValue();
						Number e = (Number) spinnerEstatura.getValue();
						float estatura = e.floatValue();
						
						Paciente paciente = instancia.agregarPaciente(nombre, apellido, edad, cedula, sexo, peso, estatura, 
								tipoSangre, direccion, telefono);

						for (Map.Entry<JCheckBox, Vacuna> entry : vacunaChecks.entrySet()) {
						    JCheckBox checkbox = entry.getKey();
						    if (checkbox.isSelected()) {
						        Vacuna v = entry.getValue();

						        Vacuna copia = new Vacuna(v.getId(), v.getNombre(), v.getFabricante(), v.getDosis(), v.getDescripcion());
						        copia.setAplicada(true);
						        paciente.agregarVacuna(copia);
						    }
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
}
