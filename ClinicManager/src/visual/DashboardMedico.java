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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

import logico.Cita;
import logico.Clinica;
import logico.Control;
import logico.Medico;
import logico.Paciente;
import logico.Usuario;

import javax.swing.table.TableModel;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.Font;
import java.awt.Graphics2D;
import javax.swing.Icon;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class DashboardMedico extends JFrame {

	Clinica instancia = Clinica.getInstancia();
	Control control = Control.getInstance();
	private JPanel contentPane;
	private JTable tablaCitas;
	DefaultTableModel modelCitas;
	LocalDate hoy = LocalDate.now();
	Date fechaInicial = Date.from(hoy.atStartOfDay(ZoneId.systemDefault()).toInstant());
	Usuario usuario = Control.getLoggedUsuario();
	Medico medicoActual = null;
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
		setTitle("Clinic Manager - Medico");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1392, 822);
		contentPane = new JPanel();

		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setResizable(true);
		
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		if (usuario != null && usuario.getLinkId() != null && !usuario.getLinkId().trim().isEmpty()) {
		    medicoActual = instancia.buscarMedicoPorId(usuario.getLinkId().trim());
		}

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

		String citasHoy = String.valueOf(contarNumCitasHoy());
		JLabel lbCitasNum = new JLabel(citasHoy);
		lbCitasNum.setForeground(SystemColor.textHighlight);
		lbCitasNum.setFont(new Font("Segoe UI", Font.BOLD, 48));
		lbCitasNum.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelPacienteKPI.add(lbCitasNum);

		panelPacienteKPI.add(Box.createVerticalStrut(4));

		JLabel lblTitleCitas = new JLabel("Citas Hoy");
		lblTitleCitas.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelPacienteKPI.add(lblTitleCitas);

		gridPanel.add(panelPacienteKPI);

		//KPI 2
		JPanel panelDoctoresKPI = new JPanel();
		panelDoctoresKPI.setBackground(SystemColor.inactiveCaptionBorder);
		panelDoctoresKPI.setLayout(new BoxLayout(panelDoctoresKPI, BoxLayout.Y_AXIS));
		panelDoctoresKPI.setBorder(new EmptyBorder(12, 12, 12, 12));

		String citasFuturas = String.valueOf(contarNumCitasFuturas());
		JLabel lbCitasGeneralsNum = new JLabel(citasFuturas);
		lbCitasGeneralsNum.setForeground(SystemColor.textHighlight);
		lbCitasGeneralsNum.setFont(new Font("Segoe UI", Font.BOLD, 48));
		lbCitasGeneralsNum.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelDoctoresKPI.add(lbCitasGeneralsNum);

		panelDoctoresKPI.add(Box.createVerticalStrut(4));

		JLabel lblCitasGeneral = new JLabel("Citas Futuras");
		lblCitasGeneral.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelDoctoresKPI.add(lblCitasGeneral);

		gridPanel.add(panelDoctoresKPI);

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
		headerPanel.setBackground(Color.WHITE);
		
		JLabel lbCitas = new JLabel("Citas");
		lbCitas.setBorder(new EmptyBorder(0, 0, 0, 8)); // margen a la derecha
		headerPanel.add(lbCitas);


		String[] columnasCitas = { "ID", "Nombre", "Apellido", "Cedula" };
		modelCitas = new DefaultTableModel(columnasCitas, 0);
		tablaCitas = new JTable(modelCitas);
		tablaCitas.setDefaultEditor(Object.class, null);
		cargarTablaCitas(hoy);
		
		panelInferiorIzquierdo.add(headerPanel, BorderLayout.NORTH);
		
		SpinnerDateModel dateModel = new SpinnerDateModel(fechaInicial, null, null, Calendar.DAY_OF_MONTH);
		JSpinner spinner = new JSpinner(dateModel);
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				java.util.Date date = (java.util.Date) spinner.getValue();
			    java.time.LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
				cargarTablaCitas(localDate);
			}
		});
		spinner.setEditor(new JSpinner.DateEditor(spinner, "dd/MM/yyyy"));
		spinner.setPreferredSize(new Dimension(140, 24));
		headerPanel.add(spinner);
		
		tablaCitas.setShowGrid(false);

		JScrollPane scrollCitas = new JScrollPane(tablaCitas);
		// ajustar tamaño preferido del scroll para que ocupe bien la tarjeta
		scrollCitas.setPreferredSize(new Dimension(860, 300));
		panelInferiorIzquierdo.add(scrollCitas, BorderLayout.CENTER);

		gridPanelBottom.add(panelInferiorIzquierdo);

		// Agrego el grid inferior
		centerContainer.add(gridPanelBottom);

		//Botones
		JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 12));
		buttonBar.setBackground(Color.WHITE);
		buttonBar.setBorder(new EmptyBorder(8, 0, 12, 0)); // padding superior/inferior

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int citaTablaSeleccionado = tablaCitas.getSelectedRow();
				int idCol = 0;
				
				if (citaTablaSeleccionado == -1)
				{
					JOptionPane.showMessageDialog(DashboardMedico.this, "No hay Nada Seleccionado", "Alerta", JOptionPane.ERROR_MESSAGE);					
				}
				else
				{
					Object idTexto  = tablaCitas.getModel().getValueAt(citaTablaSeleccionado, idCol);
		            String id = String.valueOf(idTexto);
		            Cita cita = instancia.buscarCitaPorId(id);
		            if (cita != null) {
		                cita.setEsActivo(false);
		            }

		            java.util.Date date = (java.util.Date) spinner.getValue();
		            java.time.LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

		            lbCitasNum.setText(String.valueOf(contarNumCitasHoy()));
		            cargarTablaCitas(localDate);
		            lbCitasGeneralsNum.setText(String.valueOf(contarNumCitasFuturas()));

		            JOptionPane.showMessageDialog(DashboardMedico.this, "Cita cancelada para " + (cita != null ? cita.getNombre() : id), "Alerta", JOptionPane.INFORMATION_MESSAGE);
				}
				
				
			}
		});
		JButton btnPosponer = new JButton("Posponer");
		btnPosponer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int citaTablaSeleccionado = tablaCitas.getSelectedRow();
				int idCol = 0;
				
				if (citaTablaSeleccionado == -1)
				{
					JOptionPane.showMessageDialog(DashboardMedico.this, "No hay Nada Seleccionado", "Alerta", JOptionPane.ERROR_MESSAGE);					
				}
				else
				{
					Object idTexto  = tablaCitas.getModel().getValueAt(citaTablaSeleccionado, idCol);
					String id = String.valueOf(idTexto);
					PosponerCita pantallaPosponerCita = new PosponerCita(id);
					pantallaPosponerCita.setLocationRelativeTo(DashboardMedico.this); 
					pantallaPosponerCita.setVisible(true);
					
					java.util.Date date = (java.util.Date) spinner.getValue();
				    java.time.LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
					cargarTablaCitas(localDate);
					
					lbCitasNum.setText(String.valueOf(contarNumCitasHoy()));
					lbCitasGeneralsNum.setText(String.valueOf(contarNumCitasFuturas()));
				}
				
				
			}
		});

		buttonBar.add(btnCancelar);
		buttonBar.add(btnPosponer);

		contentPane.add(buttonBar, BorderLayout.SOUTH);
		
		JButton btnAtender = new JButton("Atender");
		btnAtender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int citaTablaSeleccionado = tablaCitas.getSelectedRow();
				int idColCedula = 3;
				int idColId = 0;
				
				if (citaTablaSeleccionado == -1)
				{
					JOptionPane.showMessageDialog(DashboardMedico.this, "No hay Nada Seleccionado", "Alerta", JOptionPane.ERROR_MESSAGE);					
				}
				else
				{
					Object cedulaTexto  = tablaCitas.getModel().getValueAt(citaTablaSeleccionado, idColCedula);
					Object idTexto  = tablaCitas.getModel().getValueAt(citaTablaSeleccionado, idColId);
					String cedula = String.valueOf(cedulaTexto);
					String citaId = String.valueOf(idTexto);
					boolean existe = instancia.verificarSiPacienteExiste(cedula);
					System.out.println(existe);
					System.out.println(instancia.verificarSiPacienteExiste(cedula));
					
					if(!existe)
					{
						AgregarPaciente pantallaAgregarPaciente = new AgregarPaciente(citaId, cedula);
						pantallaAgregarPaciente.setLocationRelativeTo(DashboardMedico.this); 
						pantallaAgregarPaciente.setModal(true);    
						pantallaAgregarPaciente.setVisible(true);
						
						String pacienteId = pantallaAgregarPaciente.getCreatedPacienteId();
						AgregarConsulta pantallaAgregarConsulta = new AgregarConsulta(citaId, pacienteId);
						pantallaAgregarConsulta.setLocationRelativeTo(DashboardMedico.this); 
						pantallaAgregarConsulta.setVisible(true);
						
						java.util.Date date = (java.util.Date) spinner.getValue();
					    java.time.LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
						cargarTablaCitas(localDate);
						
						lbCitasNum.setText(String.valueOf(contarNumCitasHoy()));
						lbCitasGeneralsNum.setText(String.valueOf(contarNumCitasFuturas()));
					}
					else
					{
						String pacienteId = instancia.buscarPacientePorCedula(cedula);
						AgregarConsulta pantallaAgregarConsulta = new AgregarConsulta(citaId, pacienteId);
						pantallaAgregarConsulta.setLocationRelativeTo(DashboardMedico.this); 
						pantallaAgregarConsulta.setVisible(true);
						
						java.util.Date date = (java.util.Date) spinner.getValue();
					    java.time.LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
						cargarTablaCitas(localDate);
						lbCitasGeneralsNum.setText(String.valueOf(contarNumCitasFuturas()));
					}
				}
			}
		});
		buttonBar.add(btnAtender);

		setLocationRelativeTo(null);
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			 logico.Datos.guardar();
			}));
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
	
	private void cargarTablaCitas(LocalDate fecha) {
	    modelCitas.setRowCount(0);
	    ArrayList <Cita> citas = instancia.getCitas();

	    if (citas == null) return;

	    for (Cita c : citas) {
	        String id = c.getId();  
	        String nombre = c.getNombre();
	        String apellido = c.getApellido();
	        String cedula = c.getCedula();
	        
	        boolean mismoMedico = false;
	        if (c.getMedico() != null && medicoActual != null && c.getMedico().getId() != null && medicoActual.getId() != null) {
	            mismoMedico = c.getMedico().getId().equalsIgnoreCase(medicoActual.getId());
	        }

	        if(c.isEsActivo() && c.getFecha().equals(fecha) && mismoMedico)
	        {
	            modelCitas.addRow(new Object[] { id, nombre, apellido, cedula});
	        }
	    }	        
	}
	
	private int contarNumCitasHoy()
	{
	    int contador = 0;

	    for (Cita c : instancia.getCitas())
	    {
	        if(c.isEsActivo() && c.getFecha().equals(hoy) && c.getMedico() != null && medicoActual != null
	                && c.getMedico().getId().equalsIgnoreCase(medicoActual.getId()))
	        {
	            contador++;
	        }
	    }
	    return contador;
	}

	public int contarNumCitasFuturas()
	{
	    int contador = 0;

	    for (Cita c : instancia.getCitas())
	    {
	        if(c.isEsActivo() && c.getFecha().isAfter(hoy) && c.getMedico() != null && medicoActual != null
	                && c.getMedico().getId().equalsIgnoreCase(medicoActual.getId()))
	        {
	            contador++;
	        }
	    }

	    return contador;
	}
}