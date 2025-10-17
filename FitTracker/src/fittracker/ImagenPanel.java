/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fittracker;

/**
 *
 * @author george
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class ImagenPanel extends JPanel {
    private BufferedImage imagenOriginal;
    private final String ruta;

    public ImagenPanel(String ruta) {
        this.ruta = ruta;
        cargarImagen(ruta);
    }

    private void cargarImagen(String ruta) {
        try {
            if (ruta.startsWith("http")) {
                imagenOriginal = ImageIO.read(new URL(ruta));
            } else {
                URL url = getClass().getResource("/" + ruta);
                if (url != null) {
                    imagenOriginal = ImageIO.read(url);
                } else {
                    System.err.println("Imagen local no encontrada en classpath: " + ruta);
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo cargar la imagen: " + ruta);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imagenOriginal != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imgWidth = imagenOriginal.getWidth(this);
            int imgHeight = imagenOriginal.getHeight(this);

            double escalaX = (double) panelWidth / imgWidth;
            double escalaY = (double) panelHeight / imgHeight;
            double escala = Math.min(escalaX, escalaY);

            int nuevoAncho = (int) (imgWidth * escala);
            int nuevoAlto = (int) (imgHeight * escala);

            int x = (panelWidth - nuevoAncho) / 2;
            int y = (panelHeight - nuevoAlto) / 2;

            g.drawImage(imagenOriginal, x, y, nuevoAncho, nuevoAlto, this);
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.RED);
            g.drawString("Imagen no disponible: " + ruta, 10, getHeight() / 2);
        }
    }
}
