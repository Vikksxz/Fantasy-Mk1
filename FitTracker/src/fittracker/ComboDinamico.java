/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fittracker;

/**
 *
 * @author george
 */
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ComboDinamico extends JPanel {
    private JComboBox<String> tipoEntrenamiento;
    private JComboBox<String> listaEjercicios;
    private Map<String, String[]> ejerciciosPorTipo;
    
    public ComboDinamico(){
        setLayout(new FlowLayout());
        tipoEntrenamiento = new JComboBox<>(new String[]{"Fuerza", "Cardio", "Estiramientos"});
        listaEjercicios = new JComboBox<>();
        
        ejerciciosPorTipo = new HashMap<>();
        ejerciciosPorTipo.put("Fuerza", new String[]{"Pres banca", "sentadillas", "Jalon"});
        ejerciciosPorTipo.put("Cardio", new String[]{"Correr", "bicicleta"});
        ejerciciosPorTipo.put("Estiramientos", new String[]{"Deltoides", "biceps", "Triceps"});
        
        tipoEntrenamiento.addActionListener(e -> actualizarLista());
        
        add(new JLabel("Tipo:"));
        add(tipoEntrenamiento);
        add(new JLabel("Ejercicio:"));
        add(listaEjercicios);
        
        actualizarLista();
    }
    private void actualizarLista(){
        listaEjercicios.removeAllItems();
        String tipo = (String) tipoEntrenamiento.getSelectedItem();
        for (String ejercicio : ejerciciosPorTipo.get(tipo)) {
            listaEjercicios.addItem(ejercicio);
        }
    }
}
