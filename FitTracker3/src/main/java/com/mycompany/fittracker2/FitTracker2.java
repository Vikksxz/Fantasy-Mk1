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

// Apache POI XDDF imports for charts
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


// JFreeChart imports for exporting charts to JPG
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

// Apache PDFBox imports for PDF creation
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.ss.util.CellRangeAddress;

public class FitTracker2 extends JFrame {

    private JComboBox<String> comboTipo;
    private JComboBox<String> comboRutina;
    private JLabel lblImagen;
    private JTextField txtDuracion, txtCalorias;
    private JButton btnGuardarExcel, btnLeerExcel, btnInsertarImagen;
    private JButton btnCrearGraficosExcel, btnExportarJPG, btnGenerarPDF;

    private final String excelPath = "entrenamientos.xlsx";
    private String lastInsertedImagePath = null;

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

        btnCrearGraficosExcel = new JButton("Crear graficos en Excel");
        btnExportarJPG = new JButton("Crear grafico JPG (barras)");
        btnGenerarPDF = new JButton("Generar informe PDF");

        comboTipo.addActionListener(e -> actualizarRutinas());

        lblImagen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String tipo = comboTipo.getSelectedItem().toString();
                    String urlImg = tipo.equalsIgnoreCase("Cardio")
                            ? "https://upload.wikimedia.org/wikipedia/commons/4/4f/Running_icon.png"
                            : tipo.equalsIgnoreCase("Fuerza")
                            ? "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Weightlifting_pictogram.svg/512px-Weightlifting_pictogram.svg.png"
                            : "imagenes/imagen1.png"; // ruta relativa al proyecto

                    mostrarImagen(urlImg);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error cargand: " + ex.getMessage());
                }
            }
        });

        btnGuardarExcel.addActionListener(e -> guardarEnExcel());
        btnLeerExcel.addActionListener(e -> leerExcel());
        btnInsertarImagen.addActionListener(e -> insertarImagenExcel());

        btnCrearGraficosExcel.addActionListener(e -> {
            try {
                crearGraficosEnExcel();
                JOptionPane.showMessageDialog(this, "graficos añadidos al Excel.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error creando graficos: " + ex.getMessage());
            }
        });

        btnExportarJPG.addActionListener(e -> {
            try {
                crearGraficoBarrasComoJPG();
                JOptionPane.showMessageDialog(this, "Grafico JPG creado.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error creando JPG: " + ex.getMessage());
            }
        });

        btnGenerarPDF.addActionListener(e -> {
            try {
                generarInformePDF();
                JOptionPane.showMessageDialog(this, "PDF generado.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error generando PDF: " + ex.getMessage());
            }
        });

        int y = 0;
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel("Tipo de entrenamiento:"), gbc);
        gbc.gridx = 1;
        add(comboTipo, gbc);
        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel("Rutina:"), gbc);
        gbc.gridx = 1;
        add(comboRutina, gbc);
        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel("Duración (min):"), gbc);
        gbc.gridx = 1;
        add(txtDuracion, gbc);
        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel("Calorías:"), gbc);
        gbc.gridx = 1;
        add(txtCalorias, gbc);
        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        add(lblImagen, gbc);
        y++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = y;
        add(btnGuardarExcel, gbc);
        gbc.gridx = 1;
        add(btnLeerExcel, gbc);
        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        add(btnInsertarImagen, gbc);
        gbc.gridx = 1;
        add(btnCrearGraficosExcel, gbc);
        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        add(btnExportarJPG, gbc);
        gbc.gridx = 1;
        add(btnGenerarPDF, gbc);

        setSize(600, 700);
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
                comboRutina.addItem("Eliptica");
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
                FileInputStream fis = new FileInputStream(file);
                wb = new XSSFWorkbook(fis);
                sheet = wb.getSheetAt(0);
                fis.close();
            } else {
                wb = new XSSFWorkbook();
                sheet = wb.createSheet("entrenamientos");
                Row header = sheet.createRow(0);
                String[] headers = {"Tipo", "Rutina", "Duracion", "Calorias"};
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

            JOptionPane.showMessageDialog(this, "Datos guardados con exito");

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
            lastInsertedImagePath = imagenFile.getAbsolutePath();

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
        } catch (FileNotFoundException fnf) {
            JOptionPane.showMessageDialog(this, "No se encontro el archivo Excel. Guarda datos primero.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error insertando imagen: " + ex.getMessage());
        }
    }

    private void crearGraficosEnExcel() throws Exception {
        File file = new File(excelPath);
        if (!file.exists()) throw new FileNotFoundException("No existe " + excelPath + ". Guarda datos primero.");

        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);

        int numRows = sheet.getLastRowNum();
        if (numRows < 1) {
            fis.close();
            wb.close();
            throw new Exception("No hay datos suficientes para graficar.");
        }


        XSSFSheet chartSheet = wb.getSheet("Gráficos");
        if (chartSheet == null) chartSheet = wb.createSheet("Gráficos");


        XSSFDrawing drawing = chartSheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 0, 10, 20);
        XSSFChart chart = drawing.createChart(anchor);
        chart.setTitleText("Duración vs Calorías");
        chart.setTitleOverlay(false);

        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);


        String categoryRange = new CellRangeAddress(1, numRows, 1, 1).formatAsString(sheet.getSheetName(), true);
        XDDFDataSource<String> categories = XDDFDataSourcesFactory.fromStringCellRange(sheet,
                CellRangeAddress.valueOf(categoryRange));


        String durRangeRef = new CellRangeAddress(1, numRows, 2, 2).formatAsString(sheet.getSheetName(), true);
        XDDFNumericalDataSource<Double> durValues = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
                CellRangeAddress.valueOf(durRangeRef));


        String calRangeRef = new CellRangeAddress(1, numRows, 3, 3).formatAsString(sheet.getSheetName(), true);
        XDDFNumericalDataSource<Double> calValues = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
                CellRangeAddress.valueOf(calRangeRef));

        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

        XDDFBarChartData barData = (XDDFBarChartData) chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
        XDDFBarChartData.Series barSeries = (XDDFBarChartData.Series) barData.addSeries(categories, durValues);
        barSeries.setTitle("Duración (min)", null);
        barData.setBarDirection(BarDirection.COL);

        chart.plot(barData);

        XDDFValueAxis rightAxis = chart.createValueAxis(AxisPosition.RIGHT);
        rightAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        XDDFLineChartData lineData = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, rightAxis);
        XDDFLineChartData.Series lineSeries = (XDDFLineChartData.Series) lineData.addSeries(categories, calValues);
        lineSeries.setTitle("Calorías", null);
        chart.plot(lineData);


        XSSFClientAnchor anchor2 = drawing.createAnchor(0, 0, 0, 0, 10, 0, 20, 12);
        XSSFChart pieChart = drawing.createChart(anchor2);
        pieChart.setTitleText("Distribucion por Rutina (por numero de registros)");


        java.util.Map<String, Integer> counts = new java.util.HashMap<>();
        for (int r = 1; r <= numRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) continue;
            Cell c = row.getCell(1);
            if (c == null) continue;
            String val = c.toString();
            counts.put(val, counts.getOrDefault(val, 0) + 1);
        }


        int writeRow = 0;
        for (java.util.Map.Entry<String, Integer> e : counts.entrySet()) {
            Row rr = chartSheet.getRow(writeRow);
            if (rr == null) rr = chartSheet.createRow(writeRow);
            Cell cc0 = rr.createCell(0);
            cc0.setCellValue(e.getKey());
            Cell cc1 = rr.createCell(1);
            cc1.setCellValue(e.getValue());
            writeRow++;
        }

        if (writeRow > 0) {
            String pieCatRef = new CellRangeAddress(0, writeRow - 1, 0, 0).formatAsString(chartSheet.getSheetName(), true);
            String pieValRef = new CellRangeAddress(0, writeRow - 1, 1, 1).formatAsString(chartSheet.getSheetName(), true);
            XDDFDataSource<String> pieCats = XDDFDataSourcesFactory.fromStringCellRange(chartSheet, CellRangeAddress.valueOf(pieCatRef));
            XDDFNumericalDataSource<Double> pieVals = XDDFDataSourcesFactory.fromNumericCellRange(chartSheet, CellRangeAddress.valueOf(pieValRef));

            XDDFPieChartData pieData = (XDDFPieChartData) pieChart.createData(ChartTypes.PIE, null, null);
            pieData.addSeries(pieCats, pieVals);
            pieChart.plot(pieData);
        }


        FileOutputStream fos = new FileOutputStream(excelPath);
        wb.write(fos);
        fos.close();
        wb.close();
        fis.close();
    }


    private void crearGraficoBarrasComoJPG() throws Exception {
        File file = new File(excelPath);
        if (!file.exists()) throw new FileNotFoundException("No existe " + excelPath + ". Guarda datos primero.");

        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        int numRows = sheet.getLastRowNum();
        for (int r = 1; r <= numRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) continue;
            Cell rutinaCell = row.getCell(1);
            Cell durCell = row.getCell(2);
            String rutina = rutinaCell != null ? rutinaCell.toString() : "-";
            double dur = 0;
            try { dur = durCell != null ? Double.parseDouble(durCell.toString()) : 0; } catch (NumberFormatException nfe) {  }
            dataset.addValue(dur, "Duracion", rutina + " (" + r + ")");
        }

        JFreeChart chart = ChartFactory.createBarChart("Duracion por rutina", "Rutina", "Duracion (min)", dataset);
        File jpg = new File("grafico_barras.jpg");
        ChartUtils.saveChartAsJPEG(jpg, chart, 800, 600);

        wb.close();
        fis.close();
    }


    private void generarInformePDF() throws Exception {
        File file = new File(excelPath);
        if (!file.exists()) throw new FileNotFoundException("No existe " + excelPath + ". Guarda datos primero.");

        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        doc.addPage(page);

        PDPageContentStream cs = new PDPageContentStream(doc, page);
        cs.beginText();
        cs.setFont(PDType1Font.HELVETICA_BOLD, 18);
        cs.newLineAtOffset(40, 770);
        cs.showText("Informe de entrenamientos");
        cs.endText();

        cs.setFont(PDType1Font.HELVETICA, 10);
        float margin = 40;
        float yStart = 740;
        float rowHeight = 16;
        float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
        float[] colWidths = {tableWidth * 0.25f, tableWidth * 0.25f, tableWidth * 0.25f, tableWidth * 0.25f};

        float nextY = yStart;
        cs.setFont(PDType1Font.HELVETICA_BOLD, 18);
        cs.beginText();
        cs.newLineAtOffset(margin + 2, nextY);
        cs.showText("Tipo");
        cs.newLineAtOffset(colWidths[0], 0);
        cs.showText("Rutina");
        cs.newLineAtOffset(colWidths[1], 0);
        cs.showText("Duración");
        cs.newLineAtOffset(colWidths[2], 0);
        cs.showText("Calorías");
        cs.endText();

        cs.setFont(PDType1Font.HELVETICA, 10);
        nextY -= rowHeight;

        int numRows = sheet.getLastRowNum();
        for (int r = 1; r <= numRows; r++) {
            if (nextY < 80) { 
                cs.close();
                page = new PDPage(PDRectangle.A4);
                doc.addPage(page);
                cs = new PDPageContentStream(doc, page);
                nextY = 740;
            }
            Row row = sheet.getRow(r);
            if (row == null) continue;
            String tipo = cellToString(row.getCell(0));
            String rutina = cellToString(row.getCell(1));
            String dur = cellToString(row.getCell(2));
            String cal = cellToString(row.getCell(3));

            cs.beginText();
            cs.newLineAtOffset(margin + 2, nextY);
            cs.showText(tipo);
            cs.newLineAtOffset(colWidths[0], 0);
            cs.showText(rutina);
            cs.newLineAtOffset(colWidths[1], 0);
            cs.showText(dur);
            cs.newLineAtOffset(colWidths[2], 0);
            cs.showText(cal);
            cs.endText();

            nextY -= rowHeight;
        }

        cs.close();

        File grafJpg = new File("grafico_barras.jpg");
        float imageY = 200;
        if (grafJpg.exists()) {
            PDImageXObject pdImage = PDImageXObject.createFromFileByContent(grafJpg, doc);
            PDPageContentStream imgStream = new PDPageContentStream(doc, doc.getPage(0), PDPageContentStream.AppendMode.APPEND, true);
            float imgWidth = 400;
            float imgHeight = 300;
            imgStream.drawImage(pdImage, 40, imageY, imgWidth, imgHeight);
            imgStream.close();
        }

        if (lastInsertedImagePath != null) {
            File imgFile = new File(lastInsertedImagePath);
            if (imgFile.exists()) {
                PDImageXObject pdImg = PDImageXObject.createFromFileByContent(imgFile, doc);
                PDPageContentStream imgStream2 = new PDPageContentStream(doc, doc.getPage(0), PDPageContentStream.AppendMode.APPEND, true);
                float imgW = 150;
                float imgH = 150;
                imgStream2.drawImage(pdImg, 460, imageY, imgW, imgH);
                imgStream2.close();
            }
        }


        String pdfName = "informe_entrenamientos.pdf";
        doc.save(pdfName);
        doc.close();
        wb.close();
        fis.close();
    }

    private String cellToString(Cell c) {
        if (c == null) return "";
        switch (c.getCellType()) {
            case STRING: return c.getStringCellValue();
            case NUMERIC: return String.valueOf(c.getNumericCellValue());
            case BOOLEAN: return String.valueOf(c.getBooleanCellValue());
            default: return c.toString();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FitTracker2::new);
    }
}

