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

import logico.Control;
import logico.Usuario;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	static Control control = Control.getInstance();
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
					usuariosLec = new FileInputStream ("usuarios.dat");
					usuariosLecSerialized = new ObjectInputStream(usuariosLec);
					Control temp = (Control)usuariosLecSerialized.readObject(); //guarda el archivo en temp
					control.setInstancia(temp); //lo pone en la instancia
					usuariosLec.close();
					usuariosLecSerialized.close();
				} catch (FileNotFoundException e) {
					try {
						usuariosEsc = new  FileOutputStream("usuarios.dat");
						usuariosEscSerialized = new ObjectOutputStream(usuariosEsc);
						Usuario aux = new Usuario("Administrador", Control.md5("123456"), "admin", "0");
						control.regUser(aux);
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
				if(Control.getInstance().confirmLogin(textUsuario.getText(),textClave.getText())){
					DashboardAdmin frame = new DashboardAdmin();
					frame.setVisible(true);
					dispose();
					
				};
				
			}
		});
		btnLogin.setBounds(164, 214, 89, 23);
		panel.add(btnLogin);
	}
}
