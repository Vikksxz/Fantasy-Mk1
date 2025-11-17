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

public class BarraHerramientas extends JPanel{
    public BarraHerramientas(VentanaPrincipal v) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 12, 6));
        setBackground(new Color(20, 24, 48));
        setPreferredSize(new Dimension(1000, 56));
        
        JButton menu = new JButton("Menu");
        JButton jugar = new JButton("Jugar");
        JButton opciones = new JButton("Opciones");
        JButton ayuda = new JButton("Ayuda");
        
        menu.addActionListener(e -> v.mostrar(VentanaPrincipal.MENU));
        jugar.addActionListener(e -> v.mostrar(VentanaPrincipal.JUEGO));
        opciones.addActionListener(e -> v.mostrar(VentanaPrincipal.OPCIONES));
        ayuda.addActionListener(e -> v.mostrar(VentanaPrincipal.AYUDA));
        
        for (JButton b : new JButton[]{menu, jugar, opciones, ayuda}){
            b.setFocusPainted(false);
            b.setForeground(Color.WHITE);
            b.setBackground(new Color(36, 44, 79));
            b.setBorder(BorderFactory.createEmptyBorder(6,12,6,12));
            add(b);
        }
    }
}
