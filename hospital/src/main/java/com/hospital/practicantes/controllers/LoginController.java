package com.hospital.practicantes.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;


public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private Label lblMensaje;

    @FXML private ImageView imgHospital;

@FXML
public void initialize() {
    try {
        Image img = new Image(getClass().getResourceAsStream("/images/hospital.jpg"));
        imgHospital.setImage(img);
    } catch (Exception e) {
        System.out.println("No se pudo cargar la imagen: " + e.getMessage());
    }
}

    @FXML
    private void ingresar() {
        String usuario    = txtUsuario.getText();
        String contrasena = txtContrasena.getText();

        if (usuario == null || usuario.isBlank()
                || contrasena == null || contrasena.isBlank()) {
            lblMensaje.setText("Ingrese usuario y contrasena.");
            return;
        }

        try {
            // Carga la vista principal y reemplaza la escena
            Parent principal = FXMLLoader.load(
                    getClass().getResource("/fxml/main.fxml"));
            txtUsuario.getScene().setRoot(principal);
        } catch (IOException e) {
            lblMensaje.setText("No se pudo abrir la pantalla principal.");
            e.printStackTrace();
        }
    }
}
