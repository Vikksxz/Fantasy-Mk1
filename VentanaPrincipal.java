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

public class VentanaPrincipal extends JFrame {
    CardLayout cartas = new CardLayout();
    JPanel contenedor = new JPanel(cartas);

    public static final String MENU = "MENU";
    public static final String JUEGO = "JUEGO";
    public static final String OPCIONES = "OPCIONES";
    public static final String AYUDA = "AYUDA";

    public VentanaPrincipal() {
        setTitle("Aplicación DI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        PanelJuego juego = new PanelJuego(this);
        MenuPanel menu = new MenuPanel(this, juego);
        PanelOpciones opciones = new PanelOpciones(this, juego);
        PanelAyuda ayuda = new PanelAyuda(this);

        contenedor.add(menu, MENU);
        contenedor.add(juego, JUEGO);
        contenedor.add(opciones, OPCIONES);
        contenedor.add(ayuda, AYUDA);

        add(contenedor, BorderLayout.CENTER);

        BarraHerramientas barra = new BarraHerramientas(this);
        add(barra, BorderLayout.NORTH);

        mostrar(MENU);
    }

    public void mostrar(String nombre) {
        cartas.show(contenedor, nombre);
        if (JUEGO.equals(nombre)) {
            Component c = contenedor.getComponent(1);
            if (c instanceof PanelJuego) ((PanelJuego) c).requestFocusInWindow();
        }
    }
}
