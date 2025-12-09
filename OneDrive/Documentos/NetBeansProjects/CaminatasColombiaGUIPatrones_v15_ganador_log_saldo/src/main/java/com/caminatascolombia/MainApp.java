
package com.caminatascolombia;

import com.caminatascolombia.controller.BlogController;
import com.caminatascolombia.controller.CaminataController;
import com.caminatascolombia.controller.InscripcionController;
import com.caminatascolombia.controller.UsuarioController;
import com.caminatascolombia.view.LoginDialog;
import com.caminatascolombia.view.VistaSwing;
import com.caminatascolombia.model.Usuario;

import javax.swing.*;
import java.awt.*;

/**
 * Punto de entrada de la aplicacion de escritorio.
 * Configura el tema verde y lanza la vista principal
 */
public class MainApp {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            // ===== Tema verde global (coherente con el logo) =====
            Color verdeFuerte = new Color(0x2E7D32);
            Color verdeSuave = new Color(0xE8F5E9);
            Color verdeMedio = new Color(0xA5D6A7);
            Color verdeTexto = new Color(0x1B5E20);

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) { }

            // Fondos suaves y letras oscuras para que siempre se vean bien
            UIManager.put("Panel.background", verdeSuave);
            UIManager.put("Button.background", verdeMedio);
            UIManager.put("Button.foreground", verdeTexto);
            UIManager.put("Label.foreground", verdeTexto);
            UIManager.put("TabbedPane.selected", verdeSuave);
            UIManager.put("TextField.background", Color.WHITE);
            UIManager.put("PasswordField.background", Color.WHITE);
            UIManager.put("ComboBox.background", Color.WHITE);
            UIManager.put("TextArea.background", Color.WHITE);
            UIManager.put("TextArea.foreground", Color.DARK_GRAY);

            // ===== Crear vista principal y controladores =====
            VistaSwing vista = new VistaSwing();

            UsuarioController usuarioCtrl = new UsuarioController(vista);
            CaminataController caminataCtrl = new CaminataController(vista);
            InscripcionController inscripcionCtrl = new InscripcionController(vista);
            BlogController blogController = new BlogController(vista);

            vista.setControllers(usuarioCtrl, caminataCtrl, inscripcionCtrl, blogController);

            // Pantalla de inicio de sesion
            LoginDialog login = new LoginDialog(vista, usuarioCtrl);
            login.setVisible(true);
            Usuario u = login.getUsuarioAutenticado();
            if (u == null) {
                // Usuario decidio salir
                System.exit(0);
            }

            vista.setUsuarioActual(u);

            // Aplicar tema a todos los componentes creados
            SwingUtilities.updateComponentTreeUI(vista);

            vista.setVisible(true);
        });
    }
}
