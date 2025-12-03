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
import logico.Vacuna;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ListaVacunas extends JDialog {

	Clinica instancia = Clinica.getInstancia();
	private final JPanel contentPanel = new JPanel();
	private JTable tableVacunas;
	DefaultTableModel modelVacunas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			ListaVacunas dialog = new ListaVacunas();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListaVacunas() {
		setResizable(false);
		setModal(true);
		setTitle("Vacunas");
		setBounds(100, 100, 898, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, "name_106766949920600");
			{
				String[] columnaVacunas = {"ID", "Nombre", "Fabricante", "Descripcion"};
				modelVacunas = new DefaultTableModel(columnaVacunas, 0);
				cargarTablaVacunas();
				tableVacunas = new JTable(modelVacunas);
				tableVacunas.setFillsViewportHeight(true);
				JScrollPane scrollVacunas = new JScrollPane(tableVacunas);
				scrollVacunas.setPreferredSize(new Dimension(860, 260)); // ajusta según tu layout
				panel.add(scrollVacunas);
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
						int seleccionado = tableVacunas.getSelectedRow();
						int idCol = 0;
						if(seleccionado == -1)
						{
							JOptionPane.showMessageDialog(ListaVacunas.this, "No hay nada Seleccionado", "Alerta", JOptionPane.ERROR_MESSAGE);
						}
						
						Object idTexto  = tableVacunas.getModel().getValueAt(seleccionado, idCol);
						String id = String.valueOf(idTexto);
						Vacuna vacuna = instancia.buscarVacunaPorId(id);
						vacuna.setEsActivo(false);
						cargarTablaVacunas();
					}
				});
				buttonPane.add(btnEliminar);
			}
			{
				JButton btnEditar = new JButton("Editar");
				btnEditar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int seleccionado = tableVacunas.getSelectedRow();
						int idCol = 0;
						if(seleccionado == -1)
						{
							JOptionPane.showMessageDialog(ListaVacunas.this, "No hay nada Seleccionado", "Alerta", JOptionPane.ERROR_MESSAGE);
						}
						
						Object idTexto  = tableVacunas.getModel().getValueAt(seleccionado, idCol);
						String id = String.valueOf(idTexto);
						EditarVacuna  pantallaEditarVacuna = new EditarVacuna(id);
					}
				});
				buttonPane.add(btnEditar);
			}
			{
				JButton btnAgregar = new JButton("Agregar");
				btnAgregar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AgregarVacuna pantallaAgregarVacuna = new AgregarVacuna();
						pantallaAgregarVacuna.setLocationRelativeTo(ListaVacunas.this); 
						pantallaAgregarVacuna.setVisible(true);
						cargarTablaVacunas();
					}
				});
				btnAgregar.setActionCommand("OK");
				buttonPane.add(btnAgregar);
				getRootPane().setDefaultButton(btnAgregar);
			}
		}
	}
	
	public void cargarTablaVacunas()
	{
		modelVacunas.setRowCount(0);
		ArrayList<Vacuna> vacunas = instancia.getCatalogoVacunas();
		
		for (Vacuna v : vacunas)
		{
			String id = v.getId();
			String nombre = v.getNombre();
			String fabricante = v.getFabricante();
			String descripcion = v.getDescripcion();
			
			if(v.isEsActivo())
			{
				modelVacunas.addRow(new Object[] { id, nombre, fabricante, descripcion});
			}	
		}
	}
}
