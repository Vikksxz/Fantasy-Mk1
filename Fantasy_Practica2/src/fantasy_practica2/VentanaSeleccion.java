/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fantasy_practica2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author george
 */
public class VentanaSeleccion extends JFrame{
    private JComboBox<String> comboTipo;
    private JComboBox<String> comboEspecifico;
    private JSpinner spinnerNivel;
    
    public VentanaSeleccion() {
        setTitle("Selecciona bicharraco");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4,2));
        
        //Tipo inicial
        comboTipo = new JComboBox<>(new String[]{"Elfo", "Magician", "Ladronsuelo"});
        comboEspecifico = new JComboBox<>();
        spinnerNivel = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        
        //cambiar opciones dinamicas
        comboTipo.addActionListener(e -> actualizarOpciones());
        
        add(new JLabel("Tipo:"));
        add(comboTipo);
        add(new JLabel("Subtipo:"));
        add(comboEspecifico);
        add(new JLabel("Nivel:"));
        add(spinnerNivel);
        
        JButton btnAceptar = new JButton("Añadir al equipo");
        btnAceptar.addActionListener(e -> {
            String seleccion = comboTipo.getSelectedItem() + "-" +
                               comboEspecifico.getSelectedItem() + "(Nivel " +
                               spinnerNivel.getValue() + ")";
            new VentanaEquipo(seleccion).setVisible(true);
            dispose();
        });
        
        add(btnAceptar);
        
        actualizarOpciones();
    }
    
    private void actualizarOpciones() {
        comboEspecifico.removeAllItems();
        switch(comboTipo.getSelectedItem().toString()) {
            case "Elfo":
                comboEspecifico.addItem("Elfo Arquerito");
                comboEspecifico.addItem("Elfo Barbarico");
                break;
            case "Magician":
                comboEspecifico.addItem("Magiciano fueguito");
                comboEspecifico.addItem("Magiciano hielito");
                break;
            case "Ladronsuelo":
                comboEspecifico.addItem("Asesino");
                comboEspecifico.addItem("AtracaAbuelas");
                break;
        }
    }
}
