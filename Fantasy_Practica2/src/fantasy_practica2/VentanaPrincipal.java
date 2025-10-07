/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package fantasy_practica2;

import java.awt.Dimension;
import javax.swing.*;

/**
 *
 * @author george
 */
public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal() {
        setTitle("FanTasY Bicharracos");
        setSize(600, 500);
        setMinimumSize(new Dimension(300, 300));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
        //Creamos el menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem salir = new JMenuItem("Salir");
        salir.addActionListener(e -> System.exit(0));
        menuArchivo.add(salir);
        
        JMenu menuAyuda = new JMenu("Ayoda");
        JMenuItem acerca = new JMenuItem("acerca de mi");
        acerca.addActionListener(e -> JOptionPane.showMessageDialog(this, "FanTasy Criaturas Mk1"));
        menuAyuda.add(acerca);
        
        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);
        setJMenuBar(menuBar);
        
        JButton btnJugar = new JButton("Crear Equipo");
        btnJugar.addActionListener(e -> {
            new VentanaSeleccion().setVisible(true);
            dispose();
        });
        
        add(btnJugar);
    }
    
    public static void main(String[] args) {
            new VentanaPrincipal().setVisible(true);
        }
    
}