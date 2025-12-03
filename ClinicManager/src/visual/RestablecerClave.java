package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import com.formdev.flatlaf.FlatLightLaf;

import logico.Clinica;
import logico.Control;
import logico.EnfermedadBajoVigilancia;
import logico.Usuario;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;
import javax.swing.JPasswordField;

public class RestablecerClave extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textNombreUsuario;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private boolean password1Visible = false;
	private boolean password2Visible = false;

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
		setTitle("Restablecer Contraseña");
		setBounds(100, 100, 496, 542);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(245, 245, 250));
		contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		// Header
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(new Color(66, 135, 245));
		headerPanel.setBounds(0, 0, 500, 100);
		contentPanel.add(headerPanel);
		headerPanel.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Restablecer Contraseña");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setBounds(0, 25, 500, 35);
		headerPanel.add(lblTitulo);
		
		JLabel lblSubtitulo = new JLabel("Ingresa una nueva contraseña para tu cuenta");
		lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblSubtitulo.setForeground(new Color(220, 230, 255));
		lblSubtitulo.setBounds(0, 65, 500, 20);
		headerPanel.add(lblSubtitulo);
		
		// Label Usuario
		JLabel lblNombre = new JLabel("Nombre de Usuario");
		lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNombre.setForeground(new Color(70, 70, 70));
		lblNombre.setBounds(75, 130, 350, 20);
		contentPanel.add(lblNombre);
		
		// Campo Usuario
		textNombreUsuario = new JTextField();
		textNombreUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textNombreUsuario.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		textNombreUsuario.setBounds(75, 153, 350, 40);
		contentPanel.add(textNombreUsuario);
		textNombreUsuario.setColumns(10);
		
		// Label Nueva Contraseña
		JLabel lbClave = new JLabel("Nueva Contraseña");
		lbClave.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lbClave.setForeground(new Color(70, 70, 70));
		lbClave.setBounds(75, 215, 350, 20);
		contentPanel.add(lbClave);
		
		// Panel para primera contraseña
		JPanel passwordPanel1 = new JPanel();
		passwordPanel1.setLayout(null);
		passwordPanel1.setBackground(Color.WHITE);
		passwordPanel1.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		passwordPanel1.setBounds(75, 238, 350, 40);
		contentPanel.add(passwordPanel1);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		passwordField.setBorder(new EmptyBorder(5, 10, 5, 65));
		passwordField.setBounds(0, 0, 350, 40);
		passwordPanel1.add(passwordField);
		
		// Botón mostrar/ocultar contraseña 1
		JButton btnTogglePassword1 = new JButton("Mostrar");
		btnTogglePassword1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnTogglePassword1.setForeground(new Color(66, 135, 245));
		btnTogglePassword1.setBackground(Color.WHITE);
		btnTogglePassword1.setBorder(new LineBorder(new Color(66, 135, 245), 1));
		btnTogglePassword1.setFocusPainted(false);
		btnTogglePassword1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnTogglePassword1.setBounds(285, 7, 55, 26);
		btnTogglePassword1.setToolTipText("Mostrar/Ocultar contraseña");
		passwordPanel1.add(btnTogglePassword1);
		
		btnTogglePassword1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				password1Visible = !password1Visible;
				if (password1Visible) {
					passwordField.setEchoChar((char) 0);
					btnTogglePassword1.setText("Ocultar");
					btnTogglePassword1.setForeground(new Color(220, 53, 69));
					btnTogglePassword1.setBorder(new LineBorder(new Color(220, 53, 69), 1));
				} else {
					passwordField.setEchoChar('●');
					btnTogglePassword1.setText("Mostrar");
					btnTogglePassword1.setForeground(new Color(66, 135, 245));
					btnTogglePassword1.setBorder(new LineBorder(new Color(66, 135, 245), 1));
				}
			}
		});
		
		// Label Repetir Contraseña
		JLabel lblRepetir = new JLabel("Repetir Contraseña");
		lblRepetir.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblRepetir.setForeground(new Color(70, 70, 70));
		lblRepetir.setBounds(75, 300, 350, 20);
		contentPanel.add(lblRepetir);
		
		// Panel para segunda contraseña
		JPanel passwordPanel2 = new JPanel();
		passwordPanel2.setLayout(null);
		passwordPanel2.setBackground(Color.WHITE);
		passwordPanel2.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		passwordPanel2.setBounds(75, 323, 350, 40);
		contentPanel.add(passwordPanel2);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		passwordField_1.setBorder(new EmptyBorder(5, 10, 5, 65));
		passwordField_1.setBounds(0, 0, 350, 40);
		passwordPanel2.add(passwordField_1);
		
		// Botón mostrar/ocultar contraseña 2
		JButton btnTogglePassword2 = new JButton("Mostrar");
		btnTogglePassword2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnTogglePassword2.setForeground(new Color(66, 135, 245));
		btnTogglePassword2.setBackground(Color.WHITE);
		btnTogglePassword2.setBorder(new LineBorder(new Color(66, 135, 245), 1));
		btnTogglePassword2.setFocusPainted(false);
		btnTogglePassword2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnTogglePassword2.setBounds(285, 7, 55, 26);
		btnTogglePassword2.setToolTipText("Mostrar/Ocultar contraseña");
		passwordPanel2.add(btnTogglePassword2);
		
		btnTogglePassword2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				password2Visible = !password2Visible;
				if (password2Visible) {
					passwordField_1.setEchoChar((char) 0);
					btnTogglePassword2.setText("Ocultar");
					btnTogglePassword2.setForeground(new Color(220, 53, 69));
					btnTogglePassword2.setBorder(new LineBorder(new Color(220, 53, 69), 1));
				} else {
					passwordField_1.setEchoChar('●');
					btnTogglePassword2.setText("Mostrar");
					btnTogglePassword2.setForeground(new Color(66, 135, 245));
					btnTogglePassword2.setBorder(new LineBorder(new Color(66, 135, 245), 1));
				}
			}
		});
		
		// Info adicional
		JLabel lblInfo = new JLabel("<html><center>La contraseña debe tener al menos 6 caracteres</center></html>");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblInfo.setForeground(new Color(120, 120, 120));
		lblInfo.setBounds(75, 380, 350, 30);
		contentPanel.add(lblInfo);
		// Panel de botones
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(new Color(245, 245, 250));
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		// Botón Restablecer
		JButton okButton = new JButton("Restablecer Contraseña");
		okButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
		okButton.setForeground(Color.WHITE);
		okButton.setBackground(new Color(66, 135, 245));
		okButton.setBorder(new LineBorder(new Color(66, 135, 245), 1, true));
		okButton.setFocusPainted(false);
		okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		okButton.setPreferredSize(new java.awt.Dimension(180, 40));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nombreUsuario = (textNombreUsuario.getText() == null) ? "" : textNombreUsuario.getText().trim();
				String clave = new String(passwordField.getPassword()).trim();
				String claveRepetida = new String(passwordField_1.getPassword()).trim();
				
				// Validaciones
				if(nombreUsuario.isEmpty()) {
					JOptionPane.showMessageDialog(RestablecerClave.this, 
						"Por favor ingresa el nombre de usuario", 
						"Campo Vacío", 
						JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if(!Control.getInstance().userNameExists(nombreUsuario)) {
					JOptionPane.showMessageDialog(RestablecerClave.this, 
						"El usuario no existe en el sistema", 
						"Usuario no encontrado", 
						JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(clave.isEmpty() || claveRepetida.isEmpty()) {
					JOptionPane.showMessageDialog(RestablecerClave.this, 
						"Por favor completa ambos campos de contraseña", 
						"Campos Vacíos", 
						JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if(clave.length() < 6) {
					JOptionPane.showMessageDialog(RestablecerClave.this, 
						"La contraseña debe tener al menos 6 caracteres", 
						"Contraseña muy corta", 
						JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if(!clave.equals(claveRepetida)) {
					JOptionPane.showMessageDialog(RestablecerClave.this, 
						"Las contraseñas no coinciden. Por favor verifica", 
						"Contraseñas diferentes", 
						JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Actualizar contraseña
				Usuario usuario = Control.getInstance().buscarUsuario(nombreUsuario);
				usuario.setClave(Control.md5(clave));
				Control.getInstance().guardarAlDisco();
				
				JOptionPane.showMessageDialog(RestablecerClave.this, 
					"¡Contraseña restablecida exitosamente!\nYa puedes iniciar sesión con tu nueva contraseña", 
					"Éxito", 
					JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		// Botón Cancelar
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cancelButton.setForeground(new Color(100, 100, 100));
		cancelButton.setBackground(Color.WHITE);
		cancelButton.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		cancelButton.setFocusPainted(false);
		cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cancelButton.setPreferredSize(new java.awt.Dimension(120, 40));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}
}
