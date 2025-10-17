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

public class PanelGridBag extends JPanel {
    public PanelGridBag(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        
        JLabel titulo = new JLabel("Seguimiento del entrenamiento");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        
        ImagenPanel imagenLocal = new ImagenPanel("imagenes/entrenamiento.jpg");
        ImagenPanel imagenWeb = new ImagenPanel("https://images.pexels.com/photos/34003939/pexels-photo-34003939.jpeg");
        ComboDinamico comboPanel = new ComboDinamico();
        PanelMetricas metricasPanel = new PanelMetricas();
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = GridBagConstraints.REMAINDER; gbc.weightx = 1; gbc.weighty = 0.1;
        add(titulo, gbc);
        
        gbc.gridy = 1; gbc.gridwidth = 1; gbc.weighty = 0.45;
        add(imagenLocal, gbc);
        
        gbc.gridx = 1;
        add(imagenWeb, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.weighty = 0.15;
        add(comboPanel, gbc);
        
        gbc.gridy = 3; gbc.weighty = 0.3;
        add(metricasPanel, gbc);
    }
}
