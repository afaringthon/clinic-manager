package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

import logico.Clinica;
import logico.EnfermedadBajoVigilancia;
import logico.Medico;
import logico.Paciente;
import logico.Vacuna;

import javax.swing.table.TableModel;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.Graphics2D;
import javax.swing.Icon;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DashboardAdmin extends JFrame {

	Clinica instancia = Clinica.getInstancia();
	private JPanel contentPane;
	private JTable tablaDoctores;
	private JTable tablaPacientes;
	JLabel lbDoctoresNum;
	JLabel lbVacunaNum;
	JLabel lblEnfermedadesNum;
	DefaultTableModel modelDoctores;
	DefaultTableModel modelPacientes;
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new FlatLightLaf());
					DashboardAdmin frame = new DashboardAdmin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DashboardAdmin() {
		setTitle("Clinic Manager - Admin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1392, 822);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAgregar = new JMenu("Agregar");
		menuBar.add(mnAgregar);
		
		JMenuItem mnItemMedico = new JMenuItem("Medico");
		mnItemMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AgregarMedico pantallaAgregarMedico = new AgregarMedico();
				pantallaAgregarMedico.setLocationRelativeTo(DashboardAdmin.this); 
				pantallaAgregarMedico.setVisible(true);
				int totalDoctores = instancia.getMedicos().size();
				lbDoctoresNum.setText(String.valueOf(totalDoctores));
				cargarTablaDoctores();
				
				
			}
		});
		mnAgregar.add(mnItemMedico);
		
		JMenuItem mnItemVacuna = new JMenuItem("Vacuna");
		mnItemVacuna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AgregarVacuna pantallaAgregarVacuna = new AgregarVacuna();
				pantallaAgregarVacuna.setLocationRelativeTo(DashboardAdmin.this); 
				pantallaAgregarVacuna.setVisible(true);
				int numVacunas = contarNumVacunas();
				lbVacunaNum.setText(String.valueOf(String.valueOf(numVacunas)));
				}
		});
		mnAgregar.add(mnItemVacuna);
		
		JMenuItem mnItemEnfermedad = new JMenuItem("Enfermedad Vigilada");
		mnItemEnfermedad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AgregarEnfermedad pantallaAgregarEnfermedad = new AgregarEnfermedad();
				pantallaAgregarEnfermedad.setLocationRelativeTo(DashboardAdmin.this); 
				pantallaAgregarEnfermedad.setVisible(true);
				int numEnfermedades = instancia.getEnfermedadesVigiladas().size();
				lblEnfermedadesNum.setText(String.valueOf(numEnfermedades));
			}
		});
		mnAgregar.add(mnItemEnfermedad);
		
		JMenu mnMenuVer = new JMenu("Ver");
		menuBar.add(mnMenuVer);
		
		JMenuItem mnMenuVacunas = new JMenuItem("Vacunas");
		mnMenuVacunas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListaVacunas pantallaListaVacunas = new ListaVacunas();
				pantallaListaVacunas.setLocationRelativeTo(DashboardAdmin.this); 
				pantallaListaVacunas.setVisible(true);
				int numVacunas = contarNumVacunas();
				lbVacunaNum.setText(String.valueOf(String.valueOf(numVacunas)));
			}
		});
		mnMenuVer.add(mnMenuVacunas);
		
		JMenuItem mnItemEnfermedades = new JMenuItem("Enfermedades Vigiladas");
		mnItemEnfermedades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListaEnfermedades pantallaListaEnfermedades = new ListaEnfermedades();
				pantallaListaEnfermedades.setLocationRelativeTo(DashboardAdmin.this); 
				pantallaListaEnfermedades.setVisible(true);
				int numEnfermedades = contarNumEnfermedades();
				lblEnfermedadesNum.setText(String.valueOf(numEnfermedades));
				//car
			}
		});
		mnMenuVer.add(mnItemEnfermedades);
		
		JMenu mnNewMenu = new JMenu("Backup");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Hacer Respaldo");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnMenuStats = new JMenu("Stats");
		menuBar.add(mnMenuStats);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Reportes");
		mnMenuStats.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Medico (Testing)");
		mnMenuStats.add(mntmNewMenuItem_2);
		
		JMenuItem mnItemSecretaria = new JMenuItem("Secretaria (Testing)");
		mnItemSecretaria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DashboardSecretaria pantallaDashboardSecretaria = new DashboardSecretaria();
				pantallaDashboardSecretaria.setLocationRelativeTo(DashboardAdmin.this); 
				pantallaDashboardSecretaria.setVisible(true);
			}
		});
		mnMenuStats.add(mnItemSecretaria);
		contentPane = new JPanel();
		// Sin padding extra en el contentPane para que la navbar quede pegada
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel navbar = new JPanel(new BorderLayout());
		navbar.setBackground(SystemColor.textHighlight);
		navbar.setPreferredSize(new Dimension(0, 72));
		navbar.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.add(navbar, BorderLayout.NORTH);
		
		//Logo
		ImageIcon logoIcon = loadAndScaleIcon("/visual/logo.png", 152, 34);
		JLabel logoLabel = new JLabel(logoIcon);
		logoLabel.setBorder(new EmptyBorder(8, 12, 8, 12));
		navbar.add(logoLabel, BorderLayout.WEST);
		
		//Avatar
		ImageIcon avatarIcon = loadAndScaleIcon("/visual/avatar.png", 48, 48);
		JLabel labelAvatar = new JLabel((avatarIcon));
		labelAvatar.setBorder(new EmptyBorder(8, 12, 8, 12));
		navbar.add(labelAvatar, BorderLayout.EAST);

		// Contenedor central
		JPanel centerContainer = new JPanel();
		centerContainer.setBackground(Color.WHITE);
		centerContainer.setLayout(new BoxLayout(centerContainer, BoxLayout.Y_AXIS));
		contentPane.add(centerContainer, BorderLayout.CENTER);

		//Spacer
		centerContainer.add(Box.createVerticalStrut(40));

		//Superior
		JPanel gridPanel = new JPanel();
		gridPanel.setOpaque(false);
		Dimension gridTopSize = new Dimension(900, 120);
		gridPanel.setPreferredSize(gridTopSize);
		gridPanel.setMaximumSize(new Dimension(1200, 120));
		gridPanel.setLayout(new GridLayout(1, 0, 8, 8));
		gridPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		//KPI 1
		JPanel panelPacienteKPI = new JPanel();
		panelPacienteKPI.setBackground(SystemColor.inactiveCaptionBorder);
		panelPacienteKPI.setLayout(new BoxLayout(panelPacienteKPI, BoxLayout.Y_AXIS));
		panelPacienteKPI.setBorder(new EmptyBorder(12, 12, 12, 12)); // padding interno

		int numPacientes = contarNumPacientes();
		JLabel lbPacientesNum = new JLabel(String.valueOf(numPacientes));
		lbPacientesNum.setForeground(SystemColor.textHighlight);
		lbPacientesNum.setFont(new Font("Segoe UI", Font.BOLD, 48));
		lbPacientesNum.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelPacienteKPI.add(lbPacientesNum);

		panelPacienteKPI.add(Box.createVerticalStrut(4));

		JLabel lblTitlePacientes = new JLabel("Pacientes");
		lblTitlePacientes.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelPacienteKPI.add(lblTitlePacientes);

		gridPanel.add(panelPacienteKPI);

		//KPI 2
		JPanel panelDoctoresKPI = new JPanel();
		panelDoctoresKPI.setBackground(SystemColor.inactiveCaptionBorder);
		panelDoctoresKPI.setLayout(new BoxLayout(panelDoctoresKPI, BoxLayout.Y_AXIS));
		panelDoctoresKPI.setBorder(new EmptyBorder(12, 12, 12, 12));

		int numMedicos = contarNumMedicos();
		lbDoctoresNum = new JLabel(String.valueOf(numMedicos));
		lbDoctoresNum.setForeground(SystemColor.textHighlight);
		lbDoctoresNum.setFont(new Font("Segoe UI", Font.BOLD, 48));
		lbDoctoresNum.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelDoctoresKPI.add(lbDoctoresNum);

		panelDoctoresKPI.add(Box.createVerticalStrut(4));

		JLabel lblTitleDoctores = new JLabel("Doctores");
		lblTitleDoctores.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelDoctoresKPI.add(lblTitleDoctores);

		gridPanel.add(panelDoctoresKPI);

		//KPI 3
		JPanel panelEnfermedadesKPI = new JPanel();
		panelEnfermedadesKPI.setBackground(SystemColor.inactiveCaptionBorder);
		panelEnfermedadesKPI.setLayout(new BoxLayout(panelEnfermedadesKPI, BoxLayout.Y_AXIS));
		panelEnfermedadesKPI.setBorder(new EmptyBorder(12, 12, 12, 12));

		int numEnfermedades = contarNumEnfermedades();
		lblEnfermedadesNum = new JLabel(String.valueOf(numEnfermedades));
		lblEnfermedadesNum.setForeground(SystemColor.textHighlight);
		lblEnfermedadesNum.setFont(new Font("Segoe UI", Font.BOLD, 48));
		lblEnfermedadesNum.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelEnfermedadesKPI.add(lblEnfermedadesNum);

		panelEnfermedadesKPI.add(Box.createVerticalStrut(4));

		JLabel lblTitleEnfermedades = new JLabel("Enfermedades");
		lblTitleEnfermedades.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelEnfermedadesKPI.add(lblTitleEnfermedades);

		gridPanel.add(panelEnfermedadesKPI);

		//KPI 4
		JPanel panelVacunasKPI = new JPanel();
		panelVacunasKPI.setBackground(SystemColor.inactiveCaptionBorder);
		panelVacunasKPI.setLayout(new BoxLayout(panelVacunasKPI, BoxLayout.Y_AXIS));
		panelVacunasKPI.setBorder(new EmptyBorder(12, 12, 12, 12));

	    int numVacunas = contarNumVacunas();
		lbVacunaNum = new JLabel(String.valueOf(numVacunas));
		lbVacunaNum.setForeground(SystemColor.textHighlight);
		lbVacunaNum.setFont(new Font("Segoe UI", Font.BOLD, 48));
		lbVacunaNum.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelVacunasKPI.add(lbVacunaNum);

		panelVacunasKPI.add(Box.createVerticalStrut(4));

		JLabel lbTitleVacunas = new JLabel("Vacunas");
		lbTitleVacunas.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelVacunasKPI.add(lbTitleVacunas);

		gridPanel.add(panelVacunasKPI);

		// Agrego el grid superior
		centerContainer.add(gridPanel);

		//Spacer del Medio
		centerContainer.add(Box.createVerticalStrut(24));

		//Grid Inferior
		JPanel gridPanelBottom = new JPanel();
		gridPanelBottom.setOpaque(false);
		Dimension gridBottomSize = new Dimension(900, 420);
		gridPanelBottom.setPreferredSize(new Dimension(1200, 420));
		gridPanelBottom.setMaximumSize(new Dimension(1200, 420));
		gridPanelBottom.setLayout(new GridLayout(1, 0, 8, 8));
		gridPanelBottom.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Panel 1
		JPanel panelInferiorIzquierdo = new JPanel(new BorderLayout());
		panelInferiorIzquierdo.setBackground(Color.WHITE);

		JLabel lbDoctores = new JLabel("Doctores", SwingConstants.CENTER);
		lbDoctores.setBorder(new EmptyBorder(6, 0, 6, 0));
		panelInferiorIzquierdo.add(lbDoctores, BorderLayout.NORTH) ;

		// Modelo y tabla de ejemplo para DOCTORES
		String[] columnasDoctores = { "ID", "Nombre", "Apellido", "Especialidad" };
		modelDoctores = new DefaultTableModel(columnasDoctores, 0);
		//Llenar
		cargarTablaDoctores();
		tablaDoctores = new JTable(modelDoctores);
		tablaDoctores.setDefaultEditor(Object.class, null);
		tablaDoctores.setShowGrid(false);

		JScrollPane scrollDoctores = new JScrollPane(tablaDoctores);
		// ajustar tamaño preferido del scroll para que ocupe bien la tarjeta
		scrollDoctores.setPreferredSize(new Dimension(860, 300));
		panelInferiorIzquierdo.add(scrollDoctores, BorderLayout.CENTER);

		gridPanelBottom.add(panelInferiorIzquierdo);

		// ------------------ Panel con la tabla de PACIENTES (lado derecho del grid inferior) ------------------
		JPanel panelInferiorDerecho = new JPanel(new BorderLayout());
		panelInferiorDerecho.setBackground(Color.WHITE);

		JLabel lbPacientes = new JLabel("Pacientes", SwingConstants.CENTER);
		lbPacientes.setBorder(new EmptyBorder(6, 0, 6, 0));
		panelInferiorDerecho.add(lbPacientes, BorderLayout.NORTH);

		String[] columnasPacientes = { "ID", "Nombre", "Edad", "Telefono", "Direccion" };
		modelPacientes = new DefaultTableModel(columnasPacientes, 0);
		tablaPacientes = new JTable(modelPacientes);
		tablaPacientes.setDefaultEditor(Object.class, null);
		JScrollPane scrollPacientes = new JScrollPane(tablaPacientes);
		scrollPacientes.setPreferredSize(new Dimension(860, 300));
		panelInferiorDerecho.add(scrollPacientes, BorderLayout.CENTER);
		cargarTablaPacientes();


		gridPanelBottom.add(panelInferiorDerecho);

		centerContainer.add(gridPanelBottom);

		//Botones
		JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 12));
		buttonBar.setBackground(Color.WHITE);
		buttonBar.setBorder(new EmptyBorder(8, 0, 12, 0));
		JButton btnEditar = new JButton("Editar");
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int doctorTablaSeleccionado = tablaDoctores.getSelectedRow();
				int pacienteTablaSeleccionado = tablaPacientes.getSelectedRow();
				int idCol = 0;
				
				if(doctorTablaSeleccionado != -1)
				{
					Object idTexto  = tablaDoctores.getModel().getValueAt(doctorTablaSeleccionado, idCol);
					String id = String.valueOf(idTexto);
					Medico medico = instancia.buscarMedicoPorId(id);
					medico.setActivo(false);
					lbDoctoresNum.setText(String.valueOf(contarNumMedicos()));
					cargarTablaDoctores();

				}
				else if (pacienteTablaSeleccionado != -1)
				{
					Object idTexto  = tablaPacientes.getModel().getValueAt(doctorTablaSeleccionado, idCol);
					String id = String.valueOf(idTexto);
					Paciente paciente = instancia.buscarPacientePorId(id);
					paciente.setActivo(false);
					lbPacientesNum.setText(String.valueOf(contarNumPacientes()));
					cargarTablaPacientes();	
				}
				else
				{
					JOptionPane.showMessageDialog(DashboardAdmin.this, "No hay nada Seleccionado", "Alerta", JOptionPane.ERROR_MESSAGE);
				}
			
				
			}
		});
		buttonBar.add(btnEditar);
		buttonBar.add(btnEliminar);

		contentPane.add(buttonBar, BorderLayout.SOUTH);

		setLocationRelativeTo(null);
	}
	
	private void cargarTablaPacientes() {
		ArrayList<Paciente> pacientes = instancia.getPacientes();
		if (pacientes == null) return;
		
		for (Paciente p : pacientes)
		{
			String id = p.getId();
			String nombre = p.getNombre();
			String apellido = p.getApellido();
			String telefono = p.getTelefono();
			String direccion = p.getDireccion();
			
			if(p.isActivo())
			{
				modelPacientes.addRow(new Object[] { id, nombre, apellido, telefono, direccion});
			}
			
		}
	}

	private ImageIcon loadAndScaleIcon(String resourcePath, int width, int height) {
		java.net.URL url = getClass().getResource(resourcePath);
		if (url == null) return null;
		try {
			BufferedImage img = ImageIO.read(url);
			Image scaled = getScaledImage(img, width, height);
			return new ImageIcon(scaled);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Image getScaledImage(BufferedImage src, int w, int h) {
		BufferedImage resized = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resized.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(src, 0, 0, w, h, null);
		g2.dispose();
		return resized;
	}
	
	private void cargarTablaDoctores() {
	    modelDoctores.setRowCount(0);
	    ArrayList <Medico> medicos = instancia.getMedicos();

	    if (medicos == null) return;

	    for (Medico m : medicos) {
	        String id = m.getId();  
	        String nombre = m.getNombre();
	        String apellido = m.getApellido();
	        String especialidad = m.getEspecialidad();
	        
	        if(m.isActivo())
	        {
	        	modelDoctores.addRow(new Object[] { id, nombre, apellido, especialidad });
	        }

	        
	    }
	}
	
	private int contarNumPacientes()
	{
		int contador = 0;
		
		for (Paciente p : instancia.getPacientes())
		{
			if(p.isActivo())
			{
				contador++;
			}
		}
		return contador;
	}
	
	private int contarNumMedicos()
	{
		int contador = 0;
		
		for (Medico m : instancia.getMedicos())
		{
			if(m.isActivo())
			{
				contador++;
			}
		}
		return contador;
	}
	
	private int contarNumVacunas()
	{
		int contador = 0;
		
		for (Vacuna v : instancia.getCatalogoVacunas())
		{
			if(v.isEsActivo())
			{
				contador++;
			}
		}
		return contador;
	}
	
	private int contarNumEnfermedades()
	{
		int contador = 0;
		
		for (EnfermedadBajoVigilancia e : instancia.getEnfermedadesVigiladas())
		{
			if(e.isEsActivo())
			{
				contador++;
			}
		}
		return contador;
	}
	
}