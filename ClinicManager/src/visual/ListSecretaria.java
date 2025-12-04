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
import logico.Control;
import logico.Usuario;
import logico.Vacuna;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ListSecretaria extends JDialog {

	Clinica instancia = Clinica.getInstancia();
	private final JPanel contentPanel = new JPanel();
	private JTable tableVacunas;
	DefaultTableModel modelSecretaria;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			ListSecretaria dialog = new ListSecretaria();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListSecretaria() {
		setResizable(false);
		setModal(true);
		setTitle("Lista de Secretarias");
		setBounds(100, 100, 898, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, "name_106766949920600");
			{
				String[] columnaVacunas = {"Nombre de Usuario"};
				modelSecretaria = new DefaultTableModel(columnaVacunas, 0);
				cargarTablaSecretaria();
				tableVacunas = new JTable(modelSecretaria);
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
							JOptionPane.showMessageDialog(ListSecretaria.this, "No hay nada Seleccionado", "Alerta", JOptionPane.ERROR_MESSAGE);
						}
						
						Object idTexto  = tableVacunas.getModel().getValueAt(seleccionado, idCol);
						String id = String.valueOf(idTexto);
						Control.getInstance().borrarUsuarioPorNombreUsuario(id);
						Control.getInstance().guardarAlDisco();
						cargarTablaSecretaria();
					}
				});
				buttonPane.add(btnEliminar);
			}
			{
				JButton btnAgregar = new JButton("Agregar");
				btnAgregar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AgregarSecretaria pantallaAgregarSecretaria = new AgregarSecretaria();
						pantallaAgregarSecretaria.setLocationRelativeTo(ListSecretaria.this); 
						pantallaAgregarSecretaria.setVisible(true);
						cargarTablaSecretaria();
					}
				});
				btnAgregar.setActionCommand("OK");
				buttonPane.add(btnAgregar);
				getRootPane().setDefaultButton(btnAgregar);
			}
		}
	}
	
	public void cargarTablaSecretaria()
	{
		modelSecretaria.setRowCount(0);
				
		for (Usuario u : Control.getInstance().getMisUsuarios())
		{
			String nombreUsuario = u.getNombreUsuario();

			if(u.getEsActivo() && u.getRol().equals("secretaria"))
			{
				modelSecretaria.addRow(new Object[] {nombreUsuario});
			}	
		}
	}
}
