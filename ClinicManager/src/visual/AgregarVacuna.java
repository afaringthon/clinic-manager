package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.FlatLightLaf;

import logico.Clinica;
import logico.Vacuna;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AgregarVacuna extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textNombre;
	private JTextField textFabricante;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			AgregarVacuna dialog = new AgregarVacuna();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AgregarVacuna() {
		setResizable(false);
		setModal(true);
		setTitle("Agregar Vacuna");
		setBounds(100, 100, 452, 470);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		
		Clinica instancia = Clinica.getInstancia();
		JSpinner spinnerDosis;
		JTextArea txtrDescripcion;
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, "name_102050322273000");
			panel.setLayout(null);
			{
				JLabel lblNombre = new JLabel("Nombre");
				lblNombre.setBounds(85, 58, 56, 16);
				panel.add(lblNombre);
			}
			{
				textNombre = new JTextField();
				textNombre.setBounds(85, 76, 248, 22);
				panel.add(textNombre);
				textNombre.setColumns(10);
			}
			{
				JLabel lbFabricante = new JLabel("Fabricante");
				lbFabricante.setBounds(85, 111, 88, 16);
				panel.add(lbFabricante);
			}
			{
				textFabricante = new JTextField();
				textFabricante.setBounds(85, 129, 248, 22);
				panel.add(textFabricante);
				textFabricante.setColumns(10);
			}
			{
				JLabel lbDosis = new JLabel("Dosis");
				lbDosis.setBounds(85, 164, 56, 16);
				panel.add(lbDosis);
			}
			{
				SpinnerNumberModel dosisModel = new SpinnerNumberModel(Double.valueOf(0.0), 0.0, null, Double.valueOf(0.1));
				spinnerDosis = new JSpinner(dosisModel);
				spinnerDosis.setBounds(85, 182, 67, 22);
				panel.add(spinnerDosis);
			}
			{
				JLabel lbDescripcion = new JLabel("Descripcion");
				lbDescripcion.setBounds(85, 217, 88, 16);
				panel.add(lbDescripcion);
			}
			
			txtrDescripcion = new JTextArea();
			txtrDescripcion.setText("Descripcion de la Vacuna");
			txtrDescripcion.setLineWrap(true);
			txtrDescripcion.setBounds(85, 246, 248, 79);
			panel.add(txtrDescripcion);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Agregar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String nombre = textNombre.getText().trim();
						String fabricante = textFabricante.getText().trim();
						Number n = (Number) spinnerDosis.getValue();
						double dosisDouble = n.doubleValue();
						float dosis = (float) dosisDouble;
						
						String descripcion = txtrDescripcion.getText().trim();
						
						if(nombre.isEmpty() || fabricante.isEmpty() || dosis < 0.1 || descripcion.isEmpty())
						{
							if(dosis < 0.1)
							{
								JOptionPane.showMessageDialog(AgregarVacuna.this, "Dosis debe ser mayor que 0", "Alerta", JOptionPane.ERROR_MESSAGE);
							}
							else
							{
								JOptionPane.showMessageDialog(AgregarVacuna.this, "Hay Campos Faltantes", "Alerta", JOptionPane.ERROR_MESSAGE);
							}
						}
						else
						{
							JOptionPane.showMessageDialog(AgregarVacuna.this, "Vacuna Fue Creada", "Informacion", JOptionPane.INFORMATION_MESSAGE);
							instancia.agregarVacuna(nombre, fabricante, dosis, descripcion);
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
