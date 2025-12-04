package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.FlatLightLaf;

import logico.Clinica;
import logico.Control;
import logico.EnfermedadBajoVigilancia;
import logico.Usuario;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class AgregarSecretaria extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textNombreUsuario;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			AgregarSecretaria dialog = new AgregarSecretaria();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AgregarSecretaria() {
		setResizable(false);
		setModal(true);
		setTitle("Agregar Secretaria");
		setBounds(100, 100, 445, 254);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		
		Clinica instancia = Clinica.getInstancia();
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, "name_102872891702400");
			panel.setLayout(null);
			{
				JLabel lblNombre = new JLabel("Nombre de Ususario");
				lblNombre.setBounds(77, 38, 121, 16);
				panel.add(lblNombre);
			}
			{
				textNombreUsuario = new JTextField();
				textNombreUsuario.setBounds(77, 58, 280, 22);
				panel.add(textNombreUsuario);
				textNombreUsuario.setColumns(10);
			}
			
			JLabel lbClave = new JLabel("Clave");
			lbClave.setBounds(77, 93, 56, 16);
			panel.add(lbClave);
			
			passwordField = new JPasswordField();
			passwordField.setBounds(77, 111, 280, 22);
			panel.add(passwordField);
	
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Agregar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String nombreUsuario = textNombreUsuario.getText().trim();
						String clave = passwordField.getText().trim();
						
						if(nombreUsuario.isEmpty() || clave.isEmpty() || Control.getInstance().userNameExists(nombreUsuario))
						{
							if(Control.getInstance().userNameExists(nombreUsuario))
							{
								JOptionPane.showMessageDialog(AgregarSecretaria.this, "Hay alguien con ese Nombre de Usuario", "Alerta", JOptionPane.ERROR_MESSAGE);	
							}
							else
							{
								JOptionPane.showMessageDialog(AgregarSecretaria.this, "Hay Campos Vacios", "Alerta", JOptionPane.ERROR_MESSAGE);
							}
						}
						else
						{
							Usuario secretaria = new Usuario(nombreUsuario, Control.md5(clave), "secretaria", "1");
							Control.getInstance().regUser(secretaria);
							Control.getInstance().guardarAlDisco();
							JOptionPane.showMessageDialog(AgregarSecretaria.this, "Secretaria fue Creada", "Alerta", JOptionPane.INFORMATION_MESSAGE);
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
