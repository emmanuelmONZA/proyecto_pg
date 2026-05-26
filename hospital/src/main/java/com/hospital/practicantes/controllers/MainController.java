package com.hospital.practicantes.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class MainController {

    @FXML private BorderPane panelPrincipal;

    @FXML
    public void initialize() {
        cargarVista("/fxml/inicio.fxml");
    }

    @FXML private void abrirInicio()       { cargarVista("/fxml/inicio.fxml"); }
    @FXML private void abrirPracticantes() { cargarVista("/fxml/practicantes.fxml"); }
    @FXML private void abrirRotaciones()   { cargarVista("/fxml/rotaciones.fxml"); }
    @FXML private void abrirAccesos()      { cargarVista("/fxml/accesos.fxml"); }

   @FXML
private void cerrarSesion() {
    Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    confirmacion.setTitle("Cerrar sesión");
    confirmacion.setHeaderText("¿Desea cerrar sesión?");
    confirmacion.setContentText("Se perderán los datos no guardados.\nEsta acción no se puede deshacer.");


    ButtonType btnSi = new ButtonType("✅  Sí, cerrar sesión");
    ButtonType btnNo = new ButtonType("❌  No, quedarse");
    confirmacion.getButtonTypes().setAll(btnSi, btnNo);

    
   confirmacion.getDialogPane().setStyle(
    "-fx-background-color: white;" +
    "-fx-border-color: #1E3A5F;" +
    "-fx-border-width: 2;" +
    "-fx-border-radius: 10;" +
    "-fx-background-radius: 10;"
);
    confirmacion.getDialogPane().lookup(".header-panel").setStyle(
    "-fx-background-color: #EFF6FF;" +
    "-fx-background-radius: 8 8 0 0;"
);
   confirmacion.getDialogPane().lookup(".header-panel .label").setStyle(
    "-fx-text-fill: white;" +
    "-fx-font-size: 15px;" +
    "-fx-font-weight: bold;" +
    "-fx-opacity: 1;"
);
   confirmacion.getDialogPane().lookup(".content.label").setStyle(
    "-fx-text-fill: #374151;" +
    "-fx-font-size: 13px;"
);

    Optional<ButtonType> resultado = confirmacion.showAndWait();
    if (resultado.isPresent() && resultado.get() == btnSi) {
        try {
            Parent login = FXMLLoader.load(
                    getClass().getResource("/fxml/login.fxml"));
            panelPrincipal.getScene().setRoot(login);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    private void cargarVista(String ruta) {
        try {
            Parent vista = FXMLLoader.load(getClass().getResource(ruta));
            panelPrincipal.setCenter(vista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}