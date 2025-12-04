package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

import logico.Clinica;
import logico.Control;
import logico.Datos;
import logico.Medico;
import logico.Usuario;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;
import javax.swing.border.LineBorder;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textUsuario;
	private JPasswordField textClave;
	private boolean passwordVisible = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				FileInputStream usuariosLec;
				FileOutputStream usuariosEsc;
				ObjectInputStream usuariosLecSerialized;
				ObjectOutputStream usuariosEscSerialized;
				try {
					Datos.cargar();
					usuariosLec = new FileInputStream ("usuarios.dat");
					usuariosLecSerialized = new ObjectInputStream(usuariosLec);
					Control temp = (Control)usuariosLecSerialized.readObject();
					Control.setInstancia(temp);
					usuariosLec.close();
					usuariosLecSerialized.close();
				} catch (FileNotFoundException e) {
					try {
						usuariosEsc = new  FileOutputStream("usuarios.dat");
						usuariosEscSerialized = new ObjectOutputStream(usuariosEsc);
						Usuario aux = new Usuario("Administrador", Control.md5("123456"), "administrador", "0");
						Usuario auxSec = new Usuario("Secretaria", Control.md5("123456"), "secretaria", "1");
						Control.getInstance().regUser(aux); Control.getInstance().regUser(auxSec);
						usuariosEscSerialized.writeObject(Control.getInstance());
						usuariosEsc.close();
						usuariosEscSerialized.close();
					} catch (FileNotFoundException e1) {
					} catch (IOException e1) {
					}
				} catch (IOException e) {
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				try {
					UIManager.setLookAndFeel(new FlatLightLaf());
					Login pantallaLogin = new Login();
					pantallaLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setResizable(false);
		setTitle("Login - Clinic Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 550);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 250));
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// Panel superior con color de acento
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(new Color(66, 135, 245));
		headerPanel.setPreferredSize(new java.awt.Dimension(0, 120));
		contentPane.add(headerPanel, BorderLayout.NORTH);
		headerPanel.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Clinic Manager");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setBounds(0, 35, 480, 50);
		headerPanel.add(lblTitulo);
		
		JLabel lblSubtitulo = new JLabel("Sistema de Gestión Clínica");
		lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblSubtitulo.setForeground(new Color(220, 230, 255));
		lblSubtitulo.setBounds(0, 85, 480, 20);
		headerPanel.add(lblSubtitulo);

		// Panel central con formulario
		JPanel panel = new JPanel();
		panel.setBackground(new Color(245, 245, 250));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		// Título de login
		JLabel lblLoginTitle = new JLabel("Iniciar Sesion");
		lblLoginTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblLoginTitle.setForeground(new Color(50, 50, 50));
		lblLoginTitle.setBounds(0, 30, 480, 30);
		panel.add(lblLoginTitle);
		
		// Label Usuario
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblUsuario.setForeground(new Color(70, 70, 70));
		lblUsuario.setBounds(90, 85, 300, 20);
		panel.add(lblUsuario);
		
		// Campo Usuario
		textUsuario = new JTextField();
		textUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textUsuario.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		textUsuario.setBounds(90, 108, 300, 40);
		panel.add(textUsuario);
		textUsuario.setColumns(10);
		
		// Label Contraseña
		JLabel lblClave = new JLabel("Contraseña");
		lblClave.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblClave.setForeground(new Color(70, 70, 70));
		lblClave.setBounds(90, 165, 300, 20);
		panel.add(lblClave);
		

		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(null);
		passwordPanel.setBackground(Color.WHITE);
		passwordPanel.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		passwordPanel.setBounds(90, 188, 300, 40);
		panel.add(passwordPanel);

		textClave = new JPasswordField();
		textClave.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textClave.setBorder(new EmptyBorder(5, 10, 5, 45));
		textClave.setBounds(0, 0, 300, 40);
		passwordPanel.add(textClave);
		

		JButton btnTogglePassword = new JButton("Mostrar");
		btnTogglePassword.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnTogglePassword.setForeground(new Color(66, 135, 245));
		btnTogglePassword.setBackground(Color.WHITE);
		btnTogglePassword.setBorder(new LineBorder(new Color(66, 135, 245), 1));
		btnTogglePassword.setFocusPainted(false);
		btnTogglePassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnTogglePassword.setBounds(240, 7, 55, 26);
		btnTogglePassword.setToolTipText("Mostrar/Ocultar contrasena");
		passwordPanel.add(btnTogglePassword);
		
		btnTogglePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				passwordVisible = !passwordVisible;
				if (passwordVisible) {
					textClave.setEchoChar((char) 0);
					btnTogglePassword.setText("Ocultar");
					btnTogglePassword.setForeground(new Color(220, 53, 69));
					btnTogglePassword.setBorder(new LineBorder(new Color(220, 53, 69), 1));
				} else {
					textClave.setEchoChar('●');
					btnTogglePassword.setText("Mostrar");
					btnTogglePassword.setForeground(new Color(66, 135, 245));
					btnTogglePassword.setBorder(new LineBorder(new Color(66, 135, 245), 1));
				}
			}
		});
		
		// Botón Login
		JButton btnLogin = new JButton("Iniciar Sesión");
		btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(new Color(66, 135, 245));
		btnLogin.setBorder(new LineBorder(new Color(66, 135, 245), 1, true));
		btnLogin.setFocusPainted(false);
		btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userInput = textUsuario.getText().trim();
				String claveInput = new String(textClave.getPassword()).trim();
				if (userInput == null) userInput = "";
				if (userInput.isEmpty() || claveInput == null || claveInput.isEmpty()) {
					javax.swing.JOptionPane.showMessageDialog(Login.this, "Introduce usuario y contrasena", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
					return;
				}

				boolean ok = Control.getInstance().confirmLogin(userInput, claveInput); // autenticar
				if (ok) {
					Usuario logged = Control.getLoggedUsuario();
					String rol = null;
					String linkId = null;
					if (logged != null) {
						rol = logged.getRol();
						linkId = logged.getLinkId();
					}

					Usuario usuario = Control.getInstance().buscarUsuario(userInput);

					if (rol != null && rol.equalsIgnoreCase("administrador")) {
						DashboardAdmin pantallaDashboardAdmin = new DashboardAdmin();
						pantallaDashboardAdmin.setVisible(true);
						dispose();
						return;
					}
					if ((rol != null && rol.equalsIgnoreCase("medico"))) {
						Medico m = null;
						if (linkId != null && !linkId.trim().isEmpty()) {
							m = Clinica.getInstancia().buscarMedicoPorId(linkId.trim());
						}
						
						DashboardMedico pantallaDashboardMedico = new DashboardMedico();
						pantallaDashboardMedico.setVisible(true);
						dispose();
						return;
					} else if(usuario.getRol().equalsIgnoreCase("administrador")){
						DashboardAdmin pantallaDashboardAdmin = new DashboardAdmin();
						pantallaDashboardAdmin.setVisible(true);
						dispose();
					}
					
					DashboardSecretaria pantallaDashboardSecretaria = new DashboardSecretaria();
					pantallaDashboardSecretaria.setVisible(true);
					dispose();
					
				} else {
					javax.swing.JOptionPane.showMessageDialog(Login.this, "Usuario o contrase�a incorrectos", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});
		btnLogin.setBounds(90, 250, 300, 45);
		panel.add(btnLogin);

		JButton btnOlvidasteClave = new JButton("¿Olvidaste tu contraseña?");
		btnOlvidasteClave.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnOlvidasteClave.setForeground(new Color(66, 135, 245));
		btnOlvidasteClave.setBackground(new Color(245, 245, 250));
		btnOlvidasteClave.setBorder(null);
		btnOlvidasteClave.setFocusPainted(false);
		btnOlvidasteClave.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnOlvidasteClave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RestablecerClave pantallaRestablecerClave = new RestablecerClave();
				pantallaRestablecerClave.setLocationRelativeTo(Login.this); 
				pantallaRestablecerClave.setVisible(true);
			}
		});
		btnOlvidasteClave.setBounds(90, 310, 300, 25);
		panel.add(btnOlvidasteClave);
	
		setLocationRelativeTo(null);
	}
}
