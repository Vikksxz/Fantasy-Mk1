/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica_di;

/**
 *
 * @author george
 */


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Image;
import javax.swing.ImageIcon; 

public class Enemigo {
    private int x, y, ancho, alto;
    private int vx;
    
   
    private Image spriteSheet; 
    private final int spriteAncho = 32; 
    private final int spriteAlto = 32;  
    private int frameX = 0;     
    private final int totalFrames = 8; 
    private int animacionTimer = 0; 
    private final int animacionVelocidad = 2;

    
    public Enemigo(int x, int y, int ancho, int alto, int vx){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.ancho = spriteAncho;
        this.alto = spriteAlto;
        
        try {
            spriteSheet = new ImageIcon(getClass().getResource("/practica_di/Mushroom1-Run.png")).getImage();//Nombre incorrecto, si quieres probar, simplemente quita el 1 del nombre.
            
            this.ancho = spriteAncho;
            this.alto = spriteAlto;
        } catch (Exception e) {
            System.err.println("Error al cargar el sprite sheet del enemigo: " + e.getMessage());
           
            this.ancho = ancho; 
            this.alto = alto;
        }
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    

    public void setY(int y) { 
        this.y = y; 
    }


    public void actualizar() { 
        x += vx;
        
        
        animacionTimer++;
        if (animacionTimer >= animacionVelocidad) {
            animacionTimer = 0;
            
            
            frameX = (frameX + 1) % totalFrames;
        }
    }
    

    public Rectangle getRect() { 
        return new Rectangle(x, y, ancho, alto); 
    }
    
    public void pintar(Graphics2D g2) { 
        if (spriteSheet != null) {
            
            int sx = frameX * spriteAncho;
            int sy = 0; 

            g2.drawImage(
                spriteSheet, 
                x, y, x + ancho, y + alto,        
                sx, sy, sx + spriteAncho, sy + spriteAlto,
                null
            );
        } else {
            g2.setColor(Color.RED);
            g2.fillRect(x, y, ancho, alto);
        }
    }
}