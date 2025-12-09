
package com.caminatascolombia.view;

import com.caminatascolombia.controller.UsuarioController;
import com.caminatascolombia.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {

    private UsuarioController usuarioController;
    private Usuario usuarioAutenticado;

    private JTextField txtEmailLogin;
    private JPasswordField txtPasswordLogin;

    private JTextField txtNombreReg;
    private JTextField txtEmailReg;
    private JTextField txtDocumentoReg;
    private JPasswordField txtPasswordReg;
    private JTextField txtSaldoReg;

    public LoginDialog(Frame owner, UsuarioController usuarioController) {
        super(owner, "Inicio de sesion - Caminatas Colombia", true);
        setIconImage(new ImageIcon("img/logo_caminatas.png").getImage());
        this.usuarioController = usuarioController;
        initComponents();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Iniciar sesion", crearPanelLogin());
        tabs.addTab("Registrar usuario", crearPanelRegistro());

        add(tabs, BorderLayout.CENTER);

        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> {
            usuarioAutenticado = null;
            dispose();
        });

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.add(btnSalir);
        add(panelSur, BorderLayout.SOUTH);
    }

    private JPanel crearPanelLogin() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblEmail = new JLabel("Email:");
        txtEmailLogin = new JTextField(20);
        JLabel lblPassword = new JLabel("Contrasena:");
        txtPasswordLogin = new JPasswordField(20);
        JButton btnLogin = new JButton("Entrar");

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblEmail, gbc);
        gbc.gridx = 1;
        panel.add(txtEmailLogin, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblPassword, gbc);
        gbc.gridx = 1;
        panel.add(txtPasswordLogin, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> {
            String email = txtEmailLogin.getText().trim();
            String password = new String(txtPasswordLogin.getPassword());
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Ingrese email y contrasena.",
                        "Datos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            Usuario u = usuarioController.iniciarSesion(email, password);
            if (u != null) {
                usuarioAutenticado = u;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Credenciales incorrectas.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblEmail = new JLabel("Email:");
        JLabel lblDocumento = new JLabel("Documento:");
        JLabel lblPassword = new JLabel("Contrasena:");
        JLabel lblSaldo = new JLabel("Saldo inicial:");

        txtNombreReg = new JTextField(20);
        txtEmailReg = new JTextField(20);
        txtDocumentoReg = new JTextField(15);
        txtPasswordReg = new JPasswordField(20);
        txtSaldoReg = new JTextField("100000", 10);

        JButton btnRegistrar = new JButton("Registrar y entrar");

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblNombre, gbc);
        gbc.gridx = 1;
        panel.add(txtNombreReg, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblEmail, gbc);
        gbc.gridx = 1;
        panel.add(txtEmailReg, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblDocumento, gbc);
        gbc.gridx = 1;
        panel.add(txtDocumentoReg, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(lblPassword, gbc);
        gbc.gridx = 1;
        panel.add(txtPasswordReg, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(lblSaldo, gbc);
        gbc.gridx = 1;
        panel.add(txtSaldoReg, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        panel.add(btnRegistrar, gbc);

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombreReg.getText().trim();
            String email = txtEmailReg.getText().trim();
            String documento = txtDocumentoReg.getText().trim();
            String password = new String(txtPasswordReg.getPassword());
            String saldoStr = txtSaldoReg.getText().trim();

            if (nombre.isEmpty() || email.isEmpty() || documento.isEmpty() ||
                    password.isEmpty() || saldoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Complete todos los campos.",
                        "Datos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            double saldo;
            try {
                saldo = Double.parseDouble(saldoStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "El saldo debe ser numerico.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario u = usuarioController.registrarPersonaNatural(nombre, email, documento, password, saldo);
            if (u != null) {
                usuarioAutenticado = u;
                dispose();
            }
        });

        return panel;
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }
}
