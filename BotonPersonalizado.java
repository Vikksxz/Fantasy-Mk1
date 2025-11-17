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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BotonPersonalizado extends JButton{
    private Color base = new Color(72, 209, 204);
    private Color hover = new Color(255, 183, 3);
    private boolean encima = false;
    
    public BotonPersonalizado(String texto) {
        super(texto);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setForeground(Color.black);
        setFont(getFont().deriveFont(Font.BOLD, 14f));
        setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {encima = true; repaint();}
            @Override
            public void mouseExited(MouseEvent e) {encima = false; repaint();}
        });
    }
    
    @Override
    
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int w = getWidth(), h = getHeight();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color c1 = encima ? hover : base;
        Color c2 = c1.darker();
        g2.setPaint(new GradientPaint(0,0,c1,0,h,c2));
        g2.fillRoundRect(0, 0, w, h, 16, 16);
        if (getModel().isPressed()) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
            g2.setColor(Color.black);
            g2.fillRoundRect(0, 0, w, h, 16, 16);
        }
        super.paintComponent(g);
        g2.dispose();
    }
    
    @Override
    public boolean isContentAreaFilled(){
        return false;
    }
}
