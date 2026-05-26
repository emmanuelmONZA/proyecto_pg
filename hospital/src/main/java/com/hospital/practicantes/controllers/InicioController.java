package com.hospital.practicantes.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 * Controlador del panel de inicio.
 */


public class InicioController {

     @FXML
    private LineChart<String, Number> graficaServicios;

     @FXML
    public void initialize() {
         String[] areas   = {"Urgencias", "Pediatría", "UCI", "Med. Interna", "Cirugía"};
    Number[] valores = {20, 18, 15, 16, 12};
    String[] colores = {"#DC2626", "#1F8A8A", "#F59E0B", "#6366F1", "#10B981"};

    XYChart.Series<String, Number> serie = new XYChart.Series<>();
    serie.setName("Estudiantes por área");

    for (int i = 0; i < areas.length; i++) {
        serie.getData().add(new XYChart.Data<>(areas[i], valores[i]));
    }

    graficaServicios.getData().add(serie);

    // Colorear cada punto individualmente
    graficaServicios.sceneProperty().addListener((obs, oldScene, newScene) -> {
        if (newScene != null) {
            for (int i = 0; i < serie.getData().size(); i++) {
                String color = colores[i];
                serie.getData().get(i).getNode().setStyle(
                    "-fx-background-color: " + color + ", white;" +
                    "-fx-background-radius: 5px;" +
                    "-fx-padding: 5px;"
                );
            }
        }
    });

    }
}
