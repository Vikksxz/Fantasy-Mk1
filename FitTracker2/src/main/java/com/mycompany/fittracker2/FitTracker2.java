/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.fittracker2;

/**
 *
 * @author george
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.util.IOUtils;

public class FitTracker2 extends JFrame {

    private JComboBox<String> comboTipo;
    private JComboBox<String> comboRutina;
    private JLabel lblImagen;
    private JTextField txtDuracion, txtCalorias;
    private JButton btnGuardarExcel, btnLeerExcel, btnInsertarImagen;

    private final String excelPath = "entrenamientos.xlsx";

    public FitTracker2() {
        super("App de deporte y salud");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        comboTipo = new JComboBox<>(new String[]{"Cardio", "Fuerza", "Calentamiento"});
        comboRutina = new JComboBox<>();
        lblImagen = new JLabel("Imagen de ejercicio", SwingConstants.CENTER);
        lblImagen.setPreferredSize(new Dimension(200, 200));
        lblImagen.setBorder(BorderFactory.createLineBorder(java.awt.Color.GRAY));

        txtDuracion = new JTextField(10);
        txtCalorias = new JTextField(10);
        btnGuardarExcel = new JButton("Guardar en Excel");
        btnLeerExcel = new JButton("Leer Excel");
        btnInsertarImagen = new JButton("Insertar Imagen en Excel");

        comboTipo.addActionListener(e -> actualizarRutinas());

        lblImagen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            try {
                String tipo = comboTipo.getSelectedItem().toString();
                String urlImg = tipo.equalsIgnoreCase("Cardio")
                    ? "https://upload.wikimedia.org/wikipedia/commons/4/4f/Running_icon.png"
                    : tipo.equalsIgnoreCase("Fuerza")
                    // ¡URL CORREGIDA a un .PNG!
                    ? "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Weightlifting_pictogram.svg/512px-Weightlifting_pictogram.svg.png"
                    // ¡RUTA CORREGIDA! Añadimos "/" al inicio
                    : "/imagenes/imagen1.png";
        
            // Llamamos a la nueva función mejorada
            mostrarImagen(urlImg);
        
            } catch (Exception ex){
                // Imprime el stack trace para ver el error completo en la consola
                ex.printStackTrace(); 
        JOptionPane.showMessageDialog(null, "Error cargando la imagen: " + ex.getMessage());
            }
}
        });

        btnGuardarExcel.addActionListener(e -> guardarEnExcel());
        btnLeerExcel.addActionListener(e -> leerExcel());
        btnInsertarImagen.addActionListener(e -> insertarImagenExcel());

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; add(new JLabel("Tipo de entrenamiento:"), gbc);
        gbc.gridx = 1; add(comboTipo, gbc); y++;
        gbc.gridx = 0; gbc.gridy = y; add(new JLabel("Rutina:"), gbc);
        gbc.gridx = 1; add(comboRutina, gbc); y++;
        gbc.gridx = 0; gbc.gridy = y; add(new JLabel("Duración (min):"), gbc);
        gbc.gridx = 1; add(txtDuracion, gbc); y++;
        gbc.gridx = 0; gbc.gridy = y; add(new JLabel("Calorías:"), gbc);
        gbc.gridx = 1; add(txtCalorias, gbc); y++;
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2; add(lblImagen, gbc); y++;
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = y; add(btnGuardarExcel, gbc);
        gbc.gridx = 1; add(btnLeerExcel, gbc); y++;
        gbc.gridx = 0; gbc.gridy = y; add(btnInsertarImagen, gbc);

        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        actualizarRutinas();
    }

    private void actualizarRutinas() {
        comboRutina.removeAllItems();
        String tipo = comboTipo.getSelectedItem().toString();
        switch (tipo.toLowerCase()) {
            case "cardio":
                comboRutina.addItem("Cinta");
                comboRutina.addItem("Elíptica");
                comboRutina.addItem("Escalera");
                break;
            case "fuerza":
                comboRutina.addItem("Pesas");
                comboRutina.addItem("Flexiones");
                comboRutina.addItem("Dominadas");
                break;
            default:
                comboRutina.addItem("Estiramiento 1");
                comboRutina.addItem("Estiramiento 2");
                break;
        }
    }

    private void mostrarImagen(String urlImg) {
        try {
            BufferedImage img;
            if (urlImg.startsWith("http")) {
                img = ImageIO.read(new URL(urlImg));
            } else {
                img = ImageIO.read(new File(urlImg));
            }
            Image scaled = img.getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(scaled));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error cargando imagen: " + ex.getMessage());
        }
    }

    private void guardarEnExcel() {
    try {
        XSSFWorkbook wb;
        XSSFSheet sheet;
        File file = new File(excelPath);

        if (file.exists() && file.length() > 0) {
            // Si el archivo existe y tiene contenido, abrimos el Excel existente
            FileInputStream fis = new FileInputStream(file);
            wb = new XSSFWorkbook(fis);
            sheet = wb.getSheetAt(0);
            fis.close();
        } else {
            // Si no existe o está vacío, creamos uno nuevo
            wb = new XSSFWorkbook();
            sheet = wb.createSheet("entrenamientos");
            Row header = sheet.createRow(0);
            String[] headers = {"Tipo", "Rutina", "Duración", "Calorías"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(headers[i]);
                XSSFCellStyle style = wb.createCellStyle();
                org.apache.poi.ss.usermodel.Font font = wb.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }
        }

        int rowNum = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(comboTipo.getSelectedItem().toString());
        row.createCell(1).setCellValue(comboRutina.getSelectedItem().toString());
        row.createCell(2).setCellValue(txtDuracion.getText());
        row.createCell(3).setCellValue(txtCalorias.getText());

        for (int i = 0; i < 4; i++) sheet.autoSizeColumn(i);

        FileOutputStream fos = new FileOutputStream(file);
        wb.write(fos);
        fos.close();
        wb.close();

        JOptionPane.showMessageDialog(this, "Datos guardados con éxito");

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
    }
}


    private void leerExcel() {
        try {
            FileInputStream fis = new FileInputStream(excelPath);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);
            StringBuilder sb = new StringBuilder();
            for (Row row : sheet) {
                for (Cell cell : row) {
                    sb.append(cell.toString()).append(" | ");
                }
                sb.append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
            wb.close();
            fis.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error leyendo Excel: " + ex.getMessage());
        }
    }

    private void insertarImagenExcel() {
        try {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona una imagen");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Archivos de imagen", "jpg", "jpeg", "png", "gif"
        ));

        int result = fileChooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File imagenFile = fileChooser.getSelectedFile();

        FileInputStream fis = new FileInputStream(excelPath);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        fis.close();

        InputStream imgStream = new FileInputStream(imagenFile);
        byte[] bytes = IOUtils.toByteArray(imgStream);
        int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
        imgStream.close();

        CreationHelper helper = wb.getCreationHelper();
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1(5);
        anchor.setRow1(1);
        Picture pict = drawing.createPicture(anchor, pictureIdx);
        pict.resize(3, 3);

        FileOutputStream fos = new FileOutputStream(excelPath);
        wb.write(fos);
        fos.close();
        wb.close();

            JOptionPane.showMessageDialog(this, "Imagen insertada en Excel.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error insertando imagen: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FitTracker2::new);
    }
}
