
package com.caminatascolombia.view;

import com.caminatascolombia.model.Caminata;

import javax.swing.*;
import java.awt.*;

public class DetalleCaminataDialog extends JDialog {

    private Caminata caminata;

    public DetalleCaminataDialog(Frame owner, Caminata caminata) {
        super(owner, "Detalle de caminata", true);
        setIconImage(new ImageIcon("img/logo_caminatas.png").getImage());
        this.caminata = caminata;
        initComponents();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JTextArea txtInfo = new JTextArea(15, 40);
        txtInfo.setEditable(false);

        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(caminata.getNombre()).append("\n");
        sb.append("Lugar: ").append(caminata.getLugar()).append("\n");
        sb.append("Fecha: ").append(caminata.getFecha()).append("\n");
        sb.append("Tipo: ").append(caminata.getTipo())
          .append(" | Dificultad: ").append(caminata.getDificultad()).append("\n\n");
        sb.append("Descripcion general:\n").append(caminata.getDescripcion()).append("\n\n");
        sb.append("Recomendaciones:\n").append(caminata.getRecomendaciones()).append("\n\n");

        if (caminata.getItinerario() != null) {
            sb.append("Itinerario (estructura tipo composite):\n");
            sb.append(caminata.getItinerario().getDescripcion()).append("\n");
        } else {
            sb.append("Itinerario: pendiente por definir.\n");
        }

        txtInfo.setText(sb.toString());

        JScrollPane scrollInfo = new JScrollPane(txtInfo);
        scrollInfo.setBorder(BorderFactory.createTitledBorder("Informacion detallada"));

        // Panel para la imagen
        JPanel panelImagen = new JPanel(new BorderLayout());
        panelImagen.setBorder(BorderFactory.createTitledBorder("Espacio para imagen de la caminata"));

        JLabel lblRuta = new JLabel();
        lblRuta.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblFoto = new JLabel();
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);

        String ruta = caminata.getFotoPrincipal();
        if (ruta != null && !ruta.isBlank() && !ruta.startsWith("FOTO_")) {
            lblRuta.setText("Ruta de la foto: " + ruta);
            ImageIcon icon = new ImageIcon(ruta);
            if (icon.getIconWidth() > 0) {
                Image img = icon.getImage().getScaledInstance(260, 180, Image.SCALE_SMOOTH);
                lblFoto.setIcon(new ImageIcon(img));
            } else {
                lblFoto.setText("No se pudo cargar la imagen desde la ruta dada.");
            }
        } else {
            lblRuta.setText("No hay imagen configurada. Ruta actual: " + ruta);
            lblFoto.setText("Aqui podras mostrar la imagen real en futuras versiones.");
        }

        panelImagen.add(lblRuta, BorderLayout.NORTH);
        panelImagen.add(lblFoto, BorderLayout.CENTER);

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(scrollInfo, BorderLayout.CENTER);
        centro.add(panelImagen, BorderLayout.EAST);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.add(btnCerrar);

        add(centro, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
    }
}
