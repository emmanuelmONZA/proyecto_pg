package com.hospital.practicantes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Se carga el FXML usando getResource desde el módulo
        FXMLLoader loader = new FXMLLoader(
                MainApp.class.getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1000, 650);
        stage.setTitle("Hospital San Rafael — Monitoreo de Practicantes");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
