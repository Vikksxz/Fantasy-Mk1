/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica_di;

/**
 *
 * @author george
 */


import java.awt.*;
import javax.swing.ImageIcon;


public class Jugador {
    private int x,y,ancho,alto;
    private int dx=0, dy=0;
    private int velocidad;
    
    private Image spriteSheet;
    private final int spriteAncho = 32;
    private final int spriteAlto = 32;  
    private int frameX = 0;     
    private int frameY = 0;    
    private int animacionTimer = 0; 
    private final int animacionVelocidad = 5;
    
    public Jugador(int x, int y, int ancho, int alto, int velocidad){
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        
        try {
            
            spriteSheet = new ImageIcon(getClass().getResource("/practica_di/AnimationSheet_Character.png")).getImage();
            
            
            this.ancho = spriteAncho;
            this.alto = spriteAlto;
        } catch (Exception e) {
            System.err.println("Error al cargar el sprite sheet del jugador: " + e.getMessage());
            this.ancho = ancho;
            this.alto = alto;
        }
        this.y = y;
    }
    
    public void setDx(int dx){this.dx = dx;}
    public void setDy(int dy){this.dy = dy;}
    public int getVelocidad(){return velocidad; }
    public void setVelocidad(int v) {this.velocidad = v;}
    
    public void actualizar (int w, int h){
        x += dx; y += dy;
        if (x < 8) x = 8;
        if (y < 40) y = 40;
        if (x + ancho > w - 8) x = w - 8 - ancho;
        if (y + alto > h - 8) y = h - 8 - alto;
        
        
        animacionTimer++;
        if (animacionTimer >= animacionVelocidad) {
            animacionTimer = 0;
            
           
            if (dx != 0 || dy != 0) {
                
                frameX = (frameX + 1) % 4;
            } else {
                
                frameX = 0;
            }
        }
    }
    
    public void pintar(Graphics2D g2) {
        if (spriteSheet != null) {
            
            int sx = frameX * spriteAncho;
            int sy = frameY * spriteAlto;

           
            g2.drawImage(
                spriteSheet, 
                x, y, x + ancho, y + alto,        
                sx, sy, sx + spriteAncho, sy + spriteAlto, 
                null
            );
        } else {
            
            g2.setColor(new Color(72, 209, 204));
            g2.fillRoundRect(x, y, ancho, alto, 12, 12);
            g2.setColor(Color.WHITE);
            g2.drawRoundRect(x, y, ancho, alto, 12, 12);
        }
    }
    public Rectangle getRect() {return new Rectangle(x, y, ancho, alto); }
}