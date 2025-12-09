
package com.caminatascolombia.view;

import com.caminatascolombia.controller.UsuarioController;
import com.caminatascolombia.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class RegistroUsuarioDialog extends JDialog {

    private UsuarioController usuarioController;
    private Usuario usuarioCreado;

    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextField txtDocumento;
    private JPasswordField txtPassword;
    private JTextField txtSaldo;

    public RegistroUsuarioDialog(Frame owner, UsuarioController usuarioController) {
        super(owner, "Registrar nuevo usuario", true);
        setIconImage(new ImageIcon("img/logo_caminatas.png").getImage());
        this.usuarioController = usuarioController;
        initComponents();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblEmail = new JLabel("Email:");
        JLabel lblDocumento = new JLabel("Documento:");
        JLabel lblPassword = new JLabel("Contrasena:");
        JLabel lblSaldo = new JLabel("Saldo inicial:");

        txtNombre = new JTextField(20);
        txtEmail = new JTextField(20);
        txtDocumento = new JTextField(15);
        txtPassword = new JPasswordField(20);
        txtSaldo = new JTextField("100000", 10);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnCancelar = new JButton("Cancelar");

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblNombre, gbc);
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblEmail, gbc);
        gbc.gridx = 1;
        panel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblDocumento, gbc);
        gbc.gridx = 1;
        panel.add(txtDocumento, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(lblPassword, gbc);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(lblSaldo, gbc);
        gbc.gridx = 1;
        panel.add(txtSaldo, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnCancelar);
        panelBotones.add(btnRegistrar);

        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> registrar());
        btnCancelar.addActionListener(e -> {
            usuarioCreado = null;
            dispose();
        });
    }

    private void registrar() {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String documento = txtDocumento.getText().trim();
        String password = new String(txtPassword.getPassword());
        String saldoStr = txtSaldo.getText().trim();

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

        Usuario u = usuarioController.registrarPersonaNatural(
                nombre, email, documento, password, saldo);
        if (u != null) {
            usuarioCreado = u;
            dispose();
        }
    }

    public Usuario getUsuarioCreado() {
        return usuarioCreado;
    }
}
