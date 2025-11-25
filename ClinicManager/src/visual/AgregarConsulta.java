package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JTable;

public class AgregarConsulta extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textSintomas;
	private JTextField textDiagnostico;
	private JTable tableHistorial;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			AgregarConsulta dialog = new AgregarConsulta();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AgregarConsulta() {
		setTitle("Consulta");
		setBounds(100, 100, 963, 411);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, "name_112449224634200");
			panel.setLayout(null);
			{
				JLabel lbNombre = new JLabel("Nombre");
				lbNombre.setBounds(23, 28, 56, 16);
				panel.add(lbNombre);
			}
			{
				textField = new JTextField();
				textField.setBounds(23, 45, 285, 22);
				panel.add(textField);
				textField.setColumns(10);
			}
			{
				JLabel lblSintomas = new JLabel("Sintomas");
				lblSintomas.setBounds(23, 80, 56, 16);
				panel.add(lblSintomas);
			}
			{
				textSintomas = new JTextField();
				textSintomas.setText("");
				textSintomas.setBounds(23, 99, 285, 22);
				panel.add(textSintomas);
				textSintomas.setColumns(10);
			}
			{
				JLabel lbDiagnostico = new JLabel("Diagnostico");
				lbDiagnostico.setBounds(23, 134, 96, 16);
				panel.add(lbDiagnostico);
			}
			{
				textDiagnostico = new JTextField();
				textDiagnostico.setText("");
				textDiagnostico.setBounds(23, 152, 285, 22);
				panel.add(textDiagnostico);
				textDiagnostico.setColumns(10);
			}
			{
				JLabel lbEnfermedades = new JLabel("Enfermedades Bajo Vigilancia");
				lbEnfermedades.setBounds(23, 187, 180, 16);
				panel.add(lbEnfermedades);
			}
			{
				JComboBox comboBoxEnfermedades = new JComboBox();
				comboBoxEnfermedades.setBounds(23, 211, 180, 22);
				panel.add(comboBoxEnfermedades);
			}
			{
				JLabel lbVacunas = new JLabel("Vacunas");
				lbVacunas.setBounds(23, 246, 56, 16);
				panel.add(lbVacunas);
			}
			{
				JCheckBox chckbxNewCheckBox = new JCheckBox("Malaria");
				chckbxNewCheckBox.setBounds(23, 271, 113, 25);
				panel.add(chckbxNewCheckBox);
			}
			{
				JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Importante");
				chckbxNewCheckBox_1.setBounds(216, 210, 92, 25);
				panel.add(chckbxNewCheckBox_1);
			}
			
			
			panel.setLayout(null); // layout absoluto

			String[] historialCols = { "Sintomas", "Diagnostico", "Medico", "Enfermedad" };
			Object[][] ejemplos = {
			    { "Gripe", "Medicamento", "Dr. Gomez", "Si" },
			    { "Resfriado", "Reposo", "Dra. Perez", "No" }
			};
			DefaultTableModel modelHistorial = new DefaultTableModel(ejemplos, historialCols);

			tableHistorial = new JTable(modelHistorial);
			JScrollPane scrollHistorial = new JScrollPane(tableHistorial);
			scrollHistorial.setBounds(332, 45, 583, 246); // x,y,width,height
			panel.add(scrollHistorial);
			panel.revalidate();
			panel.repaint();
			
			JLabel lbHistorial = new JLabel("Historial");
			lbHistorial.setBounds(332, 28, 56, 16);
			panel.add(lbHistorial);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
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
