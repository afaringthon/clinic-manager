package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.FlatLightLaf;

import logico.Cita;
import logico.Clinica;
import logico.Consulta;
import logico.Control;
import logico.EnfermedadBajoVigilancia;
import logico.Medico;
import logico.Paciente;
import logico.Vacuna;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;

public class AgregarConsulta extends JDialog {

	Clinica instancia = Clinica.getInstancia();
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textSintomas;
	private JTextField textDiagnostico;
	private JTable tableHistorial;
    private final Map<JCheckBox, Vacuna> vacunaChecks = new LinkedHashMap<>();
    JComboBox comboBoxEnfermedades;
    JCheckBox chckbxNewCheckBox_1;
    DefaultTableModel modelHistorial;
    private static String citaId;
    private static String idPaciente;
    Paciente paciente = instancia.buscarPacientePorId(idPaciente);


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			AgregarConsulta dialog = new AgregarConsulta(citaId, idPaciente);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AgregarConsulta(String citaId, String idPaciente) {
		setModal(true);
		setTitle("Consulta");
		setBounds(100, 100, 962, 436);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		
		Paciente paciente = instancia.buscarPacientePorId(idPaciente);
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, "name_112449224634200");
			panel.setLayout(null);
			{
				JLabel lbNombre = new JLabel("Nombre");
				lbNombre.setBounds(23, 28, 56, 16);
				panel.add(lbNombre);
			}
			{
				textField = new JTextField();
				textField.setEditable(false);
				textField.setText(paciente.getNombre()+" " + paciente.getApellido());
				textField.setBounds(23, 45, 285, 22);
				panel.add(textField);
				textField.setColumns(10);
			}
			{
				JLabel lblSintomas = new JLabel("Sintomas");
				lblSintomas.setBounds(23, 80, 56, 16);
				panel.add(lblSintomas);
			}
			{
				textSintomas = new JTextField();
				textSintomas.setText("");
				textSintomas.setBounds(23, 99, 285, 22);
				panel.add(textSintomas);
				textSintomas.setColumns(10);
			}
			{
				JLabel lbDiagnostico = new JLabel("Diagnostico");
				lbDiagnostico.setBounds(23, 134, 96, 16);
				panel.add(lbDiagnostico);
			}
			{
				textDiagnostico = new JTextField();
				textDiagnostico.setText("");
				textDiagnostico.setBounds(23, 152, 285, 22);
				panel.add(textDiagnostico);
				textDiagnostico.setColumns(10);
			}
			{
				JLabel lbEnfermedades = new JLabel("Enfermedades Bajo Vigilancia");
				lbEnfermedades.setBounds(23, 187, 180, 16);
				panel.add(lbEnfermedades);
			}
			{
				comboBoxEnfermedades = new JComboBox();
				cargarComboEnfermedades();
				comboBoxEnfermedades.setBounds(23, 211, 180, 22);
				panel.add(comboBoxEnfermedades);
			}
			{
				JLabel lbVacunas = new JLabel("Vacunas");
				lbVacunas.setBounds(23, 246, 56, 16);
				panel.add(lbVacunas);
				
				
			}
			{
				chckbxNewCheckBox_1 = new JCheckBox("Importante");
				chckbxNewCheckBox_1.setBounds(216, 210, 92, 25);
				panel.add(chckbxNewCheckBox_1);
				
				JPanel vaccinesPanel = new JPanel();
				vaccinesPanel.setLayout(new BoxLayout(vaccinesPanel, BoxLayout.Y_AXIS));
	            vaccinesPanel.setBorder(new EmptyBorder(4,4,4,4));
				vaccinesPanel.setPreferredSize(new Dimension(250, 300));
	            
	            JScrollPane scrollVacunas = new JScrollPane(vaccinesPanel);
	            scrollVacunas.setBounds(23, 270, 285, 61);
	            panel.add(scrollVacunas);
	            
	            ArrayList<Vacuna> catalogoVacunas = instancia.getCatalogoVacunas();
	            int y = 700;
	            int x = 120;
	            int espaciado = 32;
	            
	            if (catalogoVacunas == null || catalogoVacunas.isEmpty())
	            {
	            	JCheckBox checkBox = new JCheckBox("No hay Vacunas");
	                checkBox.setEnabled(false);
	                checkBox.setBounds(x, y, 300, 25);
	                vaccinesPanel.add(checkBox);
	            }
	            else
	            {
	            	for(Vacuna v : catalogoVacunas)
	            	{
	            		if(v == null || !v.isEsActivo()) continue;
	            		
	            		JCheckBox checkBox = new JCheckBox(v.getNombre());
	            		boolean aplicadaPorPaciente = false;
	            		if (paciente != null && paciente.getVacunas() != null) {
	            			
	                        for (Vacuna pv : paciente.getVacunas()) {
	                            if (pv.getId().equalsIgnoreCase(v.getId()) && pv.isAplicada()) {
	                                aplicadaPorPaciente = true;
	                            }
	                        }
	                    }
	                    checkBox.setBounds(x, y, 300, 25);
	                    vacunaChecks.put(checkBox, v);
	                    
	                    if(aplicadaPorPaciente)
	                    {
	    	                checkBox.setEnabled(false);
	    	                checkBox.setSelected(true);
	                    }
	                    
	                    vaccinesPanel.add(checkBox);
	            		
	                    y += espaciado;
	            	}
	            	
	            }

			}
			
			
			panel.setLayout(null);

			String[] historialCols = { "Sintomas", "Diagnostico", "Medico", "Enfermedad" };
			modelHistorial = new DefaultTableModel(historialCols, 0);

			tableHistorial = new JTable(modelHistorial);
			JScrollPane scrollHistorial = new JScrollPane(tableHistorial);
			cargarTablaCitas(idPaciente);
			scrollHistorial.setBounds(332, 45, 583, 248);
			panel.add(scrollHistorial);
			panel.revalidate();
			panel.repaint();
			
			JLabel lbHistorial = new JLabel("Historial");
			lbHistorial.setBounds(332, 28, 56, 16);
			panel.add(lbHistorial);
			
			JButton btnDetalesPacientes = new JButton("Detalles del Paciente");
			btnDetalesPacientes.setBounds(715, 306, 200, 25);
			panel.add(btnDetalesPacientes);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Terminar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String sintomas = textSintomas.getText().trim();
						String diagnostico = textDiagnostico.getText().trim();
						EnfermedadBajoVigilancia enfermedad = (EnfermedadBajoVigilancia) comboBoxEnfermedades.getSelectedItem();
						if (enfermedad != null && (enfermedad.getId() == null || "Ninguno".equalsIgnoreCase(enfermedad.getNombre()))) {
						    enfermedad = null;
						}
						boolean check = chckbxNewCheckBox_1.isEnabled();
						if(enfermedad!=null) check = true;
						Cita cita = instancia.buscarCitaPorId(citaId);
						Medico medico = cita.getMedico();
						
						if(sintomas.isEmpty() || diagnostico.isEmpty() || diagnostico == null)
						{
							JOptionPane.showMessageDialog(AgregarConsulta.this, "No hay Suministradores Creados", "Alerta", JOptionPane.ERROR_MESSAGE);					
						}
						else
						{
							cita.setEsActivo(false); //borrar
							Consulta consulta = instancia.agregarConsulta(paciente, medico, sintomas, diagnostico, enfermedad, check);
							paciente.agregarConsulta(consulta);
							
							for (Map.Entry<JCheckBox, Vacuna> entry : vacunaChecks.entrySet()) {
							    JCheckBox checkbox = entry.getKey();
							    if (checkbox.isSelected()) {
							        Vacuna v = entry.getValue();
							        Vacuna copia = new Vacuna(v.getId(), v.getNombre(), v.getFabricante(), v.getDosis(), v.getDescripcion());
							        copia.setAplicada(true);
							        
							        if(!instancia.verificarVacunaRepetida(paciente, v.getId()))
							        {
							        	paciente.agregarVacuna(copia);
							        }
							        
							    }
							}
							JOptionPane.showMessageDialog(AgregarConsulta.this, "Consulta Terminada", "Informacion", JOptionPane.INFORMATION_MESSAGE);	
							System.out.print(consulta.getSintomas() + consulta.getMedico().getNombre()+ consulta.getPaciente().getNombre() +
									consulta.getEnfermedadVigilada().getNombre());
							System.out.println(consulta.isEsImportante());
							
							for (Consulta c : paciente.getHistorial())
							{
								System.out.println(c.getDiagnostico() + c.isEsImportante());
							}
							cita.setEsActivo(false);
							
							
							dispose();
							
							
							
							

						}
						
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void cargarComboEnfermedades()
	{
		ArrayList<EnfermedadBajoVigilancia> enfermedades = instancia.getEnfermedadesVigiladas();
		DefaultComboBoxModel<EnfermedadBajoVigilancia> model = new DefaultComboBoxModel<>();
	    EnfermedadBajoVigilancia defaultOpt = new EnfermedadBajoVigilancia(null, "Ninguno", null, null);

		
		model.addElement(defaultOpt);
		
		if(enfermedades != null)
		{
			for (EnfermedadBajoVigilancia e : enfermedades)
			{
				model.addElement(e);
			}
		}
		comboBoxEnfermedades.setModel(model);
	}
	
	private void cargarTablaCitas(String idPaciente) {
		modelHistorial.setRowCount(0);
		Paciente paciente = instancia.buscarPacientePorId(idPaciente);
	    ArrayList <Consulta> historial = paciente.getHistorial();


	    if (historial == null) return;

	    for (Consulta c : historial) {
	        String sintomas = c.getSintomas();  
	        String diagnostico = c.getDiagnostico();
	        String medico = c.getMedico().getNombre();
	        String enfermedad = "";
	        if (c.getEnfermedadVigilada() != null && c.getEnfermedadVigilada().getNombre() != null) {
	            enfermedad = c.getEnfermedadVigilada().getNombre().trim();
	        }
	        
	        if(c.getMedico().equals(c.getMedico().equals(Control.getInstance().getLoggedUsuario())) || c.isEsImportante())
	        {
	        	modelHistorial.addRow(new Object[] { sintomas, diagnostico, medico, enfermedad});
	        }	        
	    }
	}
}
