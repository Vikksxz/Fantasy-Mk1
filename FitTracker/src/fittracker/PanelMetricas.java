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
import java.util.Random;
import java.awt.event.ActionEvent;

public class PanelMetricas extends JPanel{
    private JLabel lblCalorias, lblPasos, lblRitmo;
    private Random random;
    private Timer timer;
    
    public PanelMetricas(){
        setLayout(new GridLayout(3, 2, 10, 10));
        setBorder(BorderFactory.createTitledBorder("Metricas de salut"));
        setBackground(new Color(245, 248, 255));
        
        random = new Random();
        
        lblCalorias = new JLabel();
        lblPasos = new JLabel();
        lblRitmo = new JLabel();
        
        actualizarMetricas();
        
        add(new JLabel("Calorias Quemaditas:"));
        add(lblCalorias);
        add(new JLabel("Pasos dados:"));
        add(lblPasos);
        add(new JLabel("Ritmo de la patata"));
        add(lblRitmo);
        
        timer = new Timer(2000, (ActionEvent e) -> actualizarMetricas());
        timer.start();
    }
    private void actualizarMetricas(){
        lblCalorias.setText(String.format("%d kcal", 200 + random.nextInt(300)));
        lblPasos.setText(String.format("%d", 5000 + random.nextInt(2000)));
        lblRitmo.setText(String.format("%d", 60 + random.nextInt(80)));
    }
}
