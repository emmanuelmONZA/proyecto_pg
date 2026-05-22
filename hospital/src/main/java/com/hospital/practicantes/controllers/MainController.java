package com.hospital.practicantes.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {

    @FXML private BorderPane panelPrincipal;

    @FXML
    public void initialize() {
        // Al abrir la pantalla principal se muestra el panel de inicio
        cargarVista("/fxml/inicio.fxml");
    }

    @FXML private void abrirInicio()       { cargarVista("/fxml/inicio.fxml"); }
    @FXML private void abrirPracticantes() { cargarVista("/fxml/practicantes.fxml"); }
    @FXML private void abrirRotaciones()   { cargarVista("/fxml/rotaciones.fxml"); }
    @FXML private void abrirAccesos()      { cargarVista("/fxml/accesos.fxml"); }

    @FXML
    private void cerrarSesion() {
        try {
            Parent login = FXMLLoader.load(
                    getClass().getResource("/fxml/login.fxml"));
            panelPrincipal.getScene().setRoot(login);
        } catch (IOException e) {
            e.printStackTrace();
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
