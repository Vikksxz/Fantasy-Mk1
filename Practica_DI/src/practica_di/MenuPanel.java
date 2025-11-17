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
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private VentanaPrincipal v;
    private PanelJuego juego; // Referencia al panel de juego

    // ** Modificamos el constructor para recibir PanelJuego **
    public MenuPanel(VentanaPrincipal v, PanelJuego juego) {
        this.v = v;
        this.juego = juego; // Guardamos la referencia
        setLayout(null);
        setBackground(new Color(24, 30, 54));
        
        JLabel titulo = new JLabel("Aplicacion DI");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        titulo.setBounds(40, 40, 600, 40);
        add(titulo);
        
        BotonPersonalizado jugar = new BotonPersonalizado("JUGAR");
        jugar.setBounds(40, 120, 160, 40);
        // El botón JUGAR ahora usará la misma lógica de nivel
        jugar.addActionListener(e -> iniciarJuegoConNivel());
        add(jugar);
        
        BotonPersonalizado opciones = new BotonPersonalizado("OPCIONES");
        opciones.setBounds(220, 120, 160, 48);
        opciones.addActionListener(e -> v.mostrar(VentanaPrincipal.OPCIONES));
        add(opciones);
        
        BotonPersonalizado ayuda = new BotonPersonalizado("AYUDA");
        ayuda.setBounds(400, 120, 160, 48);
        ayuda.addActionListener(e -> v.mostrar(VentanaPrincipal.AYUDA));
        add(ayuda);
        
        JLabel texto = new JLabel("<html><body style='width:500px'>Usa las flechas para moverte y evita las colisiones</body></html>");
        texto.setForeground(new Color(200, 200, 210));
        texto.setBounds(40, 190, 700, 80);
        add(texto);
        
        DesplegablePersonalizado<String> niveles = new DesplegablePersonalizado<>(new String[]{"Facil", "Medio", "Dificil"});
        niveles.setBounds(40, 300, 180, 30);
        add(niveles);
        
        // ** LÓGICA DE SELECCIÓN DE NIVEL AÑADIDA **
        niveles.addActionListener(e -> iniciarJuegoConNivel((String) niveles.getSelectedItem()));
        
        // Configuramos la dificultad inicial al cargar el panel
        juego.setDificultad("Facil"); 
    }
    
    // Método auxiliar para manejar la lógica de inicio de juego
    private void iniciarJuegoConNivel() {
        // En este caso, simplemente usa el nivel que ya está cargado en el juego
        v.mostrar(VentanaPrincipal.JUEGO);
    }
    
    // Sobrecarga del método auxiliar para manejar la selección del desplegable
    private void iniciarJuegoConNivel(String nivel) {
        juego.setDificultad(nivel); // Establece la dificultad
        v.mostrar(VentanaPrincipal.JUEGO); // Muestra el panel de juego
    }
}
