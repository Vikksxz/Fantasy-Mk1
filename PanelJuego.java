/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica_di;

/**
 *
 * @author george
 */


import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JPanel;

public class PanelJuego extends JPanel implements ActionListener {
    private VentanaPrincipal v;
    private Jugador jugador;
    private java.util.List<Enemigo> enemigos = new ArrayList<>();
    private Timer bucle;
    private Timer temporizadorAparicion;
    private Timer temporizadorAnimacion; // Se mantiene si quieres la vibración del enemigo
    private Random r = new Random();
    private int puntuacion = 0;
    private boolean pausa = false;
    private int retrasoAparicion = 2000;
    private int velocidadJugador = 6;
    private int parpadeo = 0;

    public PanelJuego(VentanaPrincipal v) {
        this.v = v;
        setBackground(new Color(12, 14, 28));
        setFocusable(true);
        setLayout(null);
        iniciar();
        controles();
    }
    public void setDificultad(String nivel) {
        switch (nivel) {
            case "Facil":
                setVelocidadJugador(4);
                setRetrasoAparicion(2500);
                break;
            case "Medio":
                setVelocidadJugador(6);
                setRetrasoAparicion(2000);
                break;
            case "Dificil":
                setVelocidadJugador(8);
                setRetrasoAparicion(1500);
                break;
        }
    }

    private void iniciar() {
        // Aseguramos que el Jugador se inicialice con la velocidad correcta
        jugador = new Jugador(100, 300, 40, 40, velocidadJugador); 
        bucle = new Timer(16, this);
        temporizadorAparicion = new Timer(retrasoAparicion, e -> crearEnemigo());
        
        // Mantenemos el timer de animacion (para la vibración del enemigo, no para el sprite del jugador)
        temporizadorAnimacion = new Timer(150, e -> animar()); 
        
        bucle.start();
        temporizadorAparicion.start();
        temporizadorAnimacion.start();
    }

    public void setRetrasoAparicion(int ms) {
        this.retrasoAparicion = ms;
        temporizadorAparicion.setDelay(ms);
    }

    public void setVelocidadJugador(int v) {
        this.velocidadJugador = v;
        jugador.setVelocidad(v);
    }

    private void controles() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> jugador.setDx(-jugador.getVelocidad());
                    case KeyEvent.VK_RIGHT -> jugador.setDx(jugador.getVelocidad());
                    case KeyEvent.VK_UP -> jugador.setDy(-jugador.getVelocidad());
                    case KeyEvent.VK_DOWN -> jugador.setDy(jugador.getVelocidad());
                    case KeyEvent.VK_SPACE -> pausa = !pausa;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int c = e.getKeyCode();
                if (c == KeyEvent.VK_LEFT || c == KeyEvent.VK_RIGHT) jugador.setDx(0);
                if (c == KeyEvent.VK_UP || c == KeyEvent.VK_DOWN) jugador.setDy(0);
            }
        });
    }

    private void crearEnemigo() {
        final int TAMANO_FIJO = 32;
        int y = 40 + r.nextInt(getHeight() - 120);
        int x = getWidth() + TAMANO_FIJO;
        int v = 2 + r.nextInt(4);
        enemigos.add(new Enemigo(x, y, TAMANO_FIJO, TAMANO_FIJO, -v));
    }

    private void animar() {
        // Esta animación es la vibración vertical del enemigo.
        for (Enemigo e : enemigos) e.setY(e.getY() + (r.nextBoolean() ? 1 : -1));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!pausa) {
            // Llama a actualizar, que ahora también gestiona la animación del sprite
            jugador.actualizar(getWidth(), getHeight()); 
            
            Iterator<Enemigo> it = enemigos.iterator();
            while (it.hasNext()) {
                Enemigo en = it.next();
                en.actualizar();
                if (en.getX() + en.getAncho() < 0) {
                    it.remove();
                    puntuacion += 5;
                }
            }
            Rectangle rj = jugador.getRect();
            Iterator<Enemigo> it2 = enemigos.iterator();
            while (it2.hasNext()) {
                Enemigo en = it2.next();
                if (rj.intersects(en.getRect())) {
                    it2.remove();
                    puntuacion = Math.max(0, puntuacion - 20);
                    parpadeo = 8;
                }
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(new GradientPaint(0, 0, new Color(12, 14, 28), 0, getHeight(), new Color(20, 28, 48)));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 16));
        g2.drawString("Puntuación: " + puntuacion, 16, 28);
        g2.drawString("Enemigos: " + enemigos.size(), 200, 28);
        if (pausa) g2.drawString("PAUSA", 400, 28);
        jugador.pintar(g2);
        for (Enemigo e : enemigos) e.pintar(g2);
        if (parpadeo > 0) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
            g2.setColor(Color.RED);
            g2.fillRect(0, 0, getWidth(), getHeight());
            parpadeo--;
        }
        g2.dispose();
    }
}