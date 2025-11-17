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

public class DesplegablePersonalizado<E> extends JComboBox<E> {
    public DesplegablePersonalizado(E[] elementos){
        super(elementos);
        setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> lista, Object valor, int indice, boolean sel, boolean foco) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(lista , valor, indice, sel, foco);
                lbl.setOpaque(true);
                lbl.setBackground(new Color(36, 44, 79));
                lbl.setForeground(Color.WHITE);
                lbl.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
                return lbl;
            } 
        });
        setBackground(new Color(36, 44, 79));
        setForeground(Color.WHITE);
    }
}
