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
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class RestablecerClave extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textNombreUsuario;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			RestablecerClave dialog = new RestablecerClave();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RestablecerClave() {
		setResizable(false);
		setModal(true);
		setTitle("Restablecer Clave");
		setBounds(100, 100, 448, 348);
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
				lblNombre.setBounds(77, 62, 121, 16);
				panel.add(lblNombre);
			}
			{
				textNombreUsuario = new JTextField();
				textNombreUsuario.setBounds(77, 82, 280, 22);
				panel.add(textNombreUsuario);
				textNombreUsuario.setColumns(10);
			}
			
			JLabel lbClave = new JLabel("Clave");
			lbClave.setBounds(77, 117, 56, 16);
			panel.add(lbClave);
			
			passwordField = new JPasswordField();
			passwordField.setBounds(77, 135, 280, 22);
			panel.add(passwordField);
			{
				JLabel lblNewLabel = new JLabel("Repetir Clave");
				lblNewLabel.setBounds(77, 170, 85, 16);
				panel.add(lblNewLabel);
			}
			{
				passwordField_1 = new JPasswordField();
				passwordField_1.setBounds(77, 190, 280, 22);
				panel.add(passwordField_1);
			}
	
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Restablecer");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String nombreUsuario = (textNombreUsuario.getText() == null) ? "" : textNombreUsuario.getText().trim();
						String clave = passwordField.getText().trim();
						String claveRepetida = passwordField_1.getText().trim();
						System.out.print(nombreUsuario + clave);
						if(nombreUsuario.isEmpty() || clave.isEmpty() || claveRepetida.isEmpty() || !Control.getInstance().userNameExists(nombreUsuario) 
								|| !clave.equals(claveRepetida))
						{
							if(!Control.getInstance().userNameExists(nombreUsuario))
							{
								JOptionPane.showMessageDialog(RestablecerClave.this, "Usuario no encontrado", "Alerta", JOptionPane.ERROR_MESSAGE);	
							}
							else if (!clave.equals(claveRepetida))
							{
								JOptionPane.showMessageDialog(RestablecerClave.this, "Claves no Coinciden", "Alerta", JOptionPane.ERROR_MESSAGE);	
							}
							else
							{
								JOptionPane.showMessageDialog(RestablecerClave.this, "Hay Campos Vacios", "Alerta", JOptionPane.ERROR_MESSAGE);
							}
						}
						else
						{
							Usuario usuario = Control.getInstance().buscarUsuario(nombreUsuario);
							usuario.setClave(Control.getInstance().md5(clave));
							Control.getInstance().guardarAlDisco();
							JOptionPane.showMessageDialog(RestablecerClave.this, "Clave fue Restablecida", "Alerta", JOptionPane.INFORMATION_MESSAGE);
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
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
