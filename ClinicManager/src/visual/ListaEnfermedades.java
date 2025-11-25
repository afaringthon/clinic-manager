package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.FlatLightLaf;

import logico.Clinica;
import logico.EnfermedadBajoVigilancia;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;

public class ListaEnfermedades extends JDialog {

	Clinica instancia = Clinica.getInstancia();
	private final JPanel contentPanel = new JPanel();
	private JTable tableEnfermedades;
	DefaultTableModel modelVacunas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			ListaEnfermedades dialog = new ListaEnfermedades();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListaEnfermedades() {
		setModal(true);
		setResizable(false);
		setTitle("Enfermedades Bajo Vigilancia");
		setBounds(100, 100, 898, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, "name_106766949920600");
			{
				String[] columnaVacunas = {"ID", "Nombre", "Gravedad", "Descripcion"};
				modelVacunas = new DefaultTableModel(columnaVacunas, 0);
				cargarTablaEnfermedades();
				tableEnfermedades = new JTable(modelVacunas);
				tableEnfermedades.setFillsViewportHeight(true);
				JScrollPane scrollEnfermedades = new JScrollPane(tableEnfermedades);
				scrollEnfermedades.setPreferredSize(new Dimension(860, 260)); // ajusta según tu layout
				panel.add(scrollEnfermedades);
				panel.revalidate();
				panel.repaint();


			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				JButton btnEliminar = new JButton("Eliminar");
				btnEliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int seleccionado = tableEnfermedades.getSelectedRow();
						int idCol = 0;
						if(seleccionado == -1)
						{
							JOptionPane.showMessageDialog(ListaEnfermedades.this, "No hay nada Seleccionado", "Alerta", JOptionPane.ERROR_MESSAGE);
						}
						
						Object idTexto  = tableEnfermedades.getModel().getValueAt(seleccionado, idCol);
						String id = String.valueOf(idTexto);
						EnfermedadBajoVigilancia enfermedad = instancia.buscarEnfermedadPorId(id);
						enfermedad.setEsActivo(false);
						cargarTablaEnfermedades();
					}
				});
				buttonPane.add(btnEliminar);
			}
			{
				JButton btnEditar = new JButton("Editar");
				buttonPane.add(btnEditar);
			}
			{
				JButton btnAgregar = new JButton("Agregar");
				btnAgregar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AgregarEnfermedad pantallaAgregarEnfermedad = new AgregarEnfermedad();
						pantallaAgregarEnfermedad.setLocationRelativeTo(ListaEnfermedades.this); 
						pantallaAgregarEnfermedad.setVisible(true);
						cargarTablaEnfermedades();
					}
				});
				btnAgregar.setActionCommand("OK");
				buttonPane.add(btnAgregar);
				getRootPane().setDefaultButton(btnAgregar);
			}
		}
	}
	
	public void cargarTablaEnfermedades()
	{
		modelVacunas.setRowCount(0);
		
		ArrayList <EnfermedadBajoVigilancia> enfermedadesVigiladas = instancia.getEnfermedadesVigiladas();
		
		for(EnfermedadBajoVigilancia e : enfermedadesVigiladas)
		{
			String id = e.getId();
			String nombre = e.getNombre();
			String gravedad = e.getGravedad();
			String descripcion = e.getDescripcion();
			
			if(e.isEsActivo())
			{
				modelVacunas.addRow(new Object[] { id, nombre, gravedad, descripcion});	
			}
		
		}
		
	}

}
