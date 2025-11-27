package visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.FlatLightLaf;

/**
 * Alternative simple & clean login dialog.
 * - Two-column layout: left colored panel with app name, right form.
 * - Error label below fields, "Mostrar" toggle for password, and "Remember me".
 * - Slightly different visual style than the previous version.
 *
 * Drop into your visual package and run main() to test.
 */
public class LoginAlt extends JDialog {

    private final JTextField txtUser = new JTextField();
    private final JPasswordField txtPass = new JPasswordField();
    private final JLabel lblError = new JLabel(" ");
    private final JCheckBox chkRemember = new JCheckBox("Recordarme");

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(new FlatLightLaf()); } catch (Exception ex) { /* ignore */ }
        SwingUtilities.invokeLater(() -> {
            LoginAlt dlg = new LoginAlt();
            dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dlg.setVisible(true);
        });
    }

    public LoginAlt() {
        setModal(true);
        setTitle("Iniciar sesión");
        setSize(520, 320);
        setResizable(false);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout());
        JPanel root = new JPanel(new BorderLayout());
        root.setBorder(new EmptyBorder(12,12,12,12));
        getContentPane().add(root, BorderLayout.CENTER);

        // Left accent panel
        JPanel left = new JPanel(new GridBagLayout());
        left.setPreferredSize(new Dimension(180, 0));
        left.setBackground(new Color(40, 110, 200));
        JLabel app = new JLabel("<html><center><span style='color:white;font-size:18px;'>Clinic<br>Manager</span></center></html>", SwingConstants.CENTER);
        app.setForeground(Color.WHITE);
        app.setFont(app.getFont().deriveFont(Font.BOLD, 18f));
        left.add(app);
        root.add(left, BorderLayout.WEST);

        // Right form panel
        JPanel form = new JPanel();
        form.setLayout(new GridBagLayout());
        form.setBackground(Color.WHITE);
        root.add(form, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title on form
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel title = new JLabel("Acceso");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        form.add(title, gbc);

        // User label + field
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Usuario"), gbc);

        gbc.gridx = 1;
        txtUser.setColumns(18);
        txtUser.setFont(txtUser.getFont().deriveFont(14f));
        form.add(txtUser, gbc);

        // Password label + field + show button
        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Clave"), gbc);

        JPanel passRow = new JPanel(new BorderLayout(6,0));
        passRow.setBackground(Color.WHITE);
        txtPass.setFont(txtPass.getFont().deriveFont(14f));
        passRow.add(txtPass, BorderLayout.CENTER);
        JButton btnShow = new JButton("Mostrar");
        btnShow.setFocusable(false);
        btnShow.setMargin(new Insets(4,8,4,8));
        btnShow.addActionListener(e -> {
            if (txtPass.getEchoChar() != '\0') {
                txtPass.setEchoChar((char)0);
                btnShow.setText("Ocultar");
            } else {
                txtPass.setEchoChar('•');
                btnShow.setText("Mostrar");
            }
        });
        passRow.add(btnShow, BorderLayout.EAST);

        gbc.gridx = 1;
        form.add(passRow, gbc);

        // Remember + spacer
        gbc.gridy++;
        gbc.gridx = 1;
        chkRemember.setBackground(Color.WHITE);
        form.add(chkRemember, gbc);

        // Error label (initially invisible text)
        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2;
        lblError.setForeground(new Color(180, 50, 50));
        lblError.setFont(lblError.getFont().deriveFont(12f));
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        form.add(lblError, gbc);

        // Buttons
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.setBackground(Color.WHITE);
        JButton btnLogin = new JButton("Ingresar");
        btnLogin.setPreferredSize(new Dimension(110, 30));
        btnLogin.addActionListener(e -> onLogin());
        getRootPane().setDefaultButton(btnLogin);

        JButton btnCancel = new JButton("Cancelar");
        btnCancel.setPreferredSize(new Dimension(110, 30));
        btnCancel.addActionListener(e -> dispose());

        btns.add(btnCancel);
        btns.add(btnLogin);

        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2;
        form.add(btns, gbc);

        // Small polish: focus behavior
        txtUser.addActionListener(e -> txtPass.requestFocusInWindow());
        txtPass.addActionListener(e -> onLogin());

        // set default echo char
        txtPass.setEchoChar('•');
    }

    private void onLogin() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            lblError.setText("Usuario y clave son obligatorios.");
            return;
        }

        // Demo auth (replace with real auth)
        if ("admin".equals(user) && "admin".equals(pass)) {
            // optionally remember: chkRemember.isSelected()
            JOptionPane.showMessageDialog(this, "Bienvenido " + user + "!");
            dispose();
        } else {
            lblError.setText("Usuario o clave incorrectos.");
            txtPass.setText("");
            txtPass.requestFocusInWindow();
        }
    }
}