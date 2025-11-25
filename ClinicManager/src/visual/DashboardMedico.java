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
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.table.TableModel;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Font;
import java.awt.Graphics2D;
import javax.swing.Icon;
import javax.swing.JComboBox;

public class DashboardMedico extends JFrame {

	private JPanel contentPane;
	private JTable tableDoctores;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new FlatLightLaf());
					DashboardMedico frame = new DashboardMedico();
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
	public DashboardMedico() {
		setTitle("Clinic Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1392, 822);
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

		JLabel lbPacientesNum = new JLabel("20");
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

		JLabel lbDoctoresNum = new JLabel("12");
		lbDoctoresNum.setForeground(SystemColor.textHighlight);
		lbDoctoresNum.setFont(new Font("Segoe UI", Font.BOLD, 48));
		lbDoctoresNum.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelDoctoresKPI.add(lbDoctoresNum);

		panelDoctoresKPI.add(Box.createVerticalStrut(4));

		JLabel lblTitleDoctores = new JLabel("Doctores");
		lblTitleDoctores.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelDoctoresKPI.add(lblTitleDoctores);

		gridPanel.add(panelDoctoresKPI);

		//KPI 4
		JPanel panelVacunasKPI = new JPanel();
		panelVacunasKPI.setBackground(SystemColor.inactiveCaptionBorder);
		panelVacunasKPI.setLayout(new BoxLayout(panelVacunasKPI, BoxLayout.Y_AXIS));
		panelVacunasKPI.setBorder(new EmptyBorder(12, 12, 12, 12));

		JLabel lbVacunaNum = new JLabel("3");
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

		JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
		headerPanel.setBackground(Color.WHITE); // opcional: mismo fondo
		
		JLabel lbCitas = new JLabel("Citas");
		lbCitas.setBorder(new EmptyBorder(0, 0, 0, 8)); // margen a la derecha
		headerPanel.add(lbCitas);

		// Modelo y tabla de ejemplo para DOCTORES
		String[] columnasDoctores = { "ID", "Nombre", "Especialidad", "Teléfono" };
		Object[][] datosDoctores = {
				{ "1", "Juan Pérez", "Cardiología", "555-1234" },
				{ "2", "María Gómez", "Pediatría", "555-5678" },
				{ "3", "Luís Ruiz", "Dermatología", "555-9012" },
				{ "4", "Ana López", "Neurología", "555-3456" }
		};
		DefaultTableModel modelDoctores = new DefaultTableModel(datosDoctores, columnasDoctores);
		tableDoctores = new JTable(modelDoctores);
		tableDoctores.setDefaultEditor(Object.class, null);
		
		JComboBox<String> comboBoxFecha = new JComboBox<>();
		comboBoxFecha.setPreferredSize(new Dimension(140, 24)); // ancho controlado aquí
		comboBoxFecha.setMaximumSize(new Dimension(140, 24));   // opcional para reforzar
		String[] gravedad = {"Hoy", "Ayer", "Manana"};
		comboBoxFecha.setModel(new DefaultComboBoxModel<>(gravedad));
		headerPanel.add(comboBoxFecha);

		// añade la cabecera compuesta al NORTH
		panelInferiorIzquierdo.add(headerPanel, BorderLayout.NORTH);
		
		tableDoctores.setShowGrid(false);

		JScrollPane scrollDoctores = new JScrollPane(tableDoctores);
		// ajustar tamaño preferido del scroll para que ocupe bien la tarjeta
		scrollDoctores.setPreferredSize(new Dimension(860, 300));
		panelInferiorIzquierdo.add(scrollDoctores, BorderLayout.CENTER);

		gridPanelBottom.add(panelInferiorIzquierdo);

		// Modelo y tabla de ejemplo para PACIENTES
		String[] columnasPacientes = { "ID", "Nombre", "Edad", "Teléfono", "Última cita" };
		Object[][] datosPacientes = {
				{ "101", "Pedro Castillo", 45, "555-2222", "2025-10-01" },
				{ "102", "Lucía Morales", 32, "555-3333", "2025-09-15" },
				{ "103", "Carlos Vega", 28, "555-4444", "2025-11-05" }
		};
		DefaultTableModel modelPacientes = new DefaultTableModel(datosPacientes, columnasPacientes);

		// Agrego el grid inferior
		centerContainer.add(gridPanelBottom);

		//Botones
		JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 12));
		buttonBar.setBackground(Color.WHITE);
		buttonBar.setBorder(new EmptyBorder(8, 0, 12, 0)); // padding superior/inferior

		JButton btnCancelar = new JButton("Cancelar");
		JButton btnPosponer = new JButton("Posponer");
		JButton btnAtender = new JButton("Atender");

		buttonBar.add(btnCancelar);
		buttonBar.add(btnPosponer);
		buttonBar.add(btnAtender);

		contentPane.add(buttonBar, BorderLayout.SOUTH);

		setLocationRelativeTo(null);
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
}