/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fantasy_practica2;

import javax.swing.*;

/**
 *
 * @author george
 */
public class VentanaEquipo extends JFrame {
    private DefaultListModel<String> modelo;
    
    public VentanaEquipo(String criatura) {
        setTitle("Mi equipaso");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        modelo = new DefaultListModel<>();
        modelo.addElement(criatura);
        
        JList<String> lista = new JList<>(modelo);
        
        JButton btnVolver = new JButton("Añadir mas");
        btnVolver.addActionListener(e -> {
            new VentanaSeleccion().setVisible(true);
            dispose();
        });
        
        add(new JScrollPane(lista), "Center");
        add(btnVolver, "South");
    }
}
