package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class Login extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textUsuario;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Login dialog = new Login();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Login() {
		setTitle("Login");
		setBounds(100, 100, 493, 369);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, "name_213032810365600");
			panel.setLayout(null);
			{
				textUsuario = new JTextField();
				textUsuario.setBounds(118, 112, 240, 22);
				panel.add(textUsuario);
				textUsuario.setColumns(10);
			}
			{
				JLabel lblUsuario = new JLabel("Usuario");
				lblUsuario.setBounds(118, 95, 56, 16);
				panel.add(lblUsuario);
			}
			
			passwordField = new JPasswordField();
			passwordField.setBounds(118, 154, 240, 22);
			panel.add(passwordField);
			
			JLabel lblClave = new JLabel("Clave");
			lblClave.setBounds(118, 140, 56, 16);
			panel.add(lblClave);
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
