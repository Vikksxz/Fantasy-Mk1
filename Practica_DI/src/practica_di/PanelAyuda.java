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

public class PanelAyuda extends JPanel{
    private VentanaPrincipal v;
    
    public PanelAyuda(VentanaPrincipal v) {
        this.v = v;
        setLayout(null);
        setBackground(new Color(18, 22, 40));
        
        JLabel titulo = new JLabel("Ayuda");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setBounds(30, 20, 300, 30);
        add(titulo);
        
        JTextArea texto = new JTextArea();
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setText("INFO DE LA TEORIA INSERTABLE PARA AYODAR AL JOGADOR UWU");
        texto.setBackground(new Color(18, 22, 40));
        texto.setForeground(new Color(200, 200, 210));
        texto.setBounds(30, 70, 800, 300);
        add(texto);
        
        BotonPersonalizado volver = new BotonPersonalizado("VOLVER");
        volver.setBounds(30, 400, 140, 40);
        volver.addActionListener(e -> v.mostrar(VentanaPrincipal.MENU));
        add(volver);
    }
}
