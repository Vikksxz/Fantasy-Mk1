/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica_di;

/**
 *
 * @author george
 */

import javax.swing.*;
import java.awt.*;

public class PanelOpciones extends JPanel {
    private VentanaPrincipal v;
    private PanelJuego juego;
    
    public PanelOpciones(VentanaPrincipal v, PanelJuego juego) {
        this.v = v;
        this.juego = juego;
        setLayout(null);
        setBackground(new Color(20, 26, 46));
        
        JLabel titulo = new JLabel("Opciones");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setBounds(30, 20, 300, 30);
        add(titulo);
        
        JLabel l1 = new JLabel("Velocidad del jugador: ");
        l1.setForeground(Color.WHITE);
        l1.setBounds(30, 80, 200, 24);
        add(l1);
        
        JSlider velocidad = new JSlider(2, 12, 6);
        velocidad.setBounds(30, 110, 350, 40);
        velocidad.addChangeListener(e -> juego.setVelocidadJugador(velocidad.getValue()));
        add(velocidad);
        
        JLabel l2 = new JLabel("Frecuencia de aparicion en ms: ");
        l2.setForeground(Color.WHITE);
        l2.setBounds(30, 170, 300, 24);
        add(l2);
        
        JSpinner aparicion = new JSpinner(new SpinnerNumberModel(2000, 400, 5000, 100));
        aparicion.setBounds(30, 200, 120, 28);
        aparicion.addChangeListener(e -> juego.setRetrasoAparicion((int)aparicion.getValue()));
        add(aparicion);
        
        BotonPersonalizado volver = new BotonPersonalizado("VOLVER");
        volver.setBounds(30, 270, 160, 40);
        volver.addActionListener(e -> v.mostrar(VentanaPrincipal.MENU));
        add(volver);
    }
}
