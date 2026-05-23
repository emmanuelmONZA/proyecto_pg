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
        confirmacion.setTitle("Cerrar sesion");
        confirmacion.setHeaderText("¿Esta seguro que desea cerrar sesion?");
        confirmacion.setContentText("Se perderan los datos no guardados.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                Parent login = FXMLLoader.load(
                        getClass().getResource("/fxml/login.fxml"));
                panelPrincipal.getScene().setRoot(login);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Si presiona Cancelar o cierra el dialogo, no hace nada
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