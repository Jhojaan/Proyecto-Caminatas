
package com.caminatascolombia.view;

import javax.swing.*;
import java.awt.*;

/**
 * Panel que dibuja el logo de Caminatas Colombia como fondo difuminado.
 * Se usa como content pane en las ventanas principales y dialogos.
 */
public class FondoPanel extends JPanel {

    private final Image imagen;

    public FondoPanel() {
        ImageIcon icon = new ImageIcon("img/logo_caminatas.png");
        this.imagen = icon.getImage();
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            int w = getWidth();
            int h = getHeight();

            int imgW = imagen.getWidth(this);
            int imgH = imagen.getHeight(this);

            if (imgW > 0 && imgH > 0) {
                // Ubicamos el logo en la esquina inferior izquierda,
                // dejando un margen para no tapar tanto contenido.
                int x = 40;
                int y = h - imgH - 40;
                if (y < 0) y = 0;

                // Dibujamos el logo con un poco mas de opacidad para que se vea,
                // pero sin interferir con el texto de la interfaz.
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f));
                g2.drawImage(imagen, x, y, this);
            }

            g2.dispose();
        }
    }
}
