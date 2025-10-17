/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package fittracker;

/**
 *
 * @author george
 */

import javax.swing.*;

public class FitTrackerMain extends JFrame {
    public FitTrackerMain(){
        setTitle("App deporte, FitTracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        PanelGridBag panel = new PanelGridBag();
        setContentPane(panel);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FitTrackerMain().setVisible(true);
        });
    }
}
