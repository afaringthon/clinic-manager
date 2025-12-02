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
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textUsuario;
	private JTextField textClave;

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
					Control temp = (Control)usuariosLecSerialized.readObject(); //guarda el archivo en temp
					Control.setInstancia(temp); //lo pone en la instancia
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
		setBounds(100, 100, 455, 408);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(101, 114, 60, 14);
		panel.add(lblUsuario);
		
		JLabel lblClave = new JLabel("Contrase\u00F1a");
		lblClave.setBounds(99, 165, 105, 14);
		panel.add(lblClave);
		
		textUsuario = new JTextField();
		textUsuario.setBounds(99, 131, 229, 20);
		panel.add(textUsuario);
		textUsuario.setColumns(10);
		
		textClave = new JTextField();
		textClave.setBounds(99, 181, 229, 20);
		panel.add(textClave);
		textClave.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userInput = textUsuario.getText().trim();
				String claveInput = textClave.getText().trim();
				if (userInput == null) userInput = "";
				if (userInput.isEmpty() || claveInput == null || claveInput.isEmpty()) {
					javax.swing.JOptionPane.showMessageDialog(Login.this, "Introduce usuario y contraseña", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
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
					javax.swing.JOptionPane.showMessageDialog(Login.this, "Usuario o contraseña incorrectos", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});
		btnLogin.setBounds(164, 214, 89, 23);
		panel.add(btnLogin);
	}
}
