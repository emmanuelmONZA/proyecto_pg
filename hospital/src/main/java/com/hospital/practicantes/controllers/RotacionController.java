package com.hospital.practicantes.controllers;

import com.hospital.practicantes.entities.Rotacion;
import com.hospital.practicantes.facade.SistemaFacade;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class RotacionController {

    private final SistemaFacade sistema = SistemaFacade.getInstancia();

    @FXML private TextField txtArea;
    @FXML private TextField txtFechaInicio;
    @FXML private TextField txtFechaFin;
    @FXML private TextField txtHoraInicio;
    @FXML private TextField txtHoraFin;
    @FXML private TextField txtCupo;
    @FXML private TextField txtDocente;
    @FXML private FlowPane  panelRotaciones;
    @FXML private Label     lblMensaje;
    @FXML private Label     lblTituloFormulario;
    @FXML private Label     lblMesActual;

    private String filtroActivo = "todos";

    @FXML
    public void initialize() {
        lblMesActual.setText(mesActual());
        actualizarPanel();
    }

    @FXML
    private void guardarRotacion() {
        if (txtArea.getText().isBlank() || txtHoraInicio.getText().isBlank()) {
            lblMensaje.setStyle("-fx-text-fill: #DC2626; -fx-font-size: 12px;");
            lblMensaje.setText("⚠ Area y hora inicio son obligatorios.");
            return;
        }
        int cupo = 0;
        try { cupo = Integer.parseInt(txtCupo.getText().trim()); }
        catch (NumberFormatException ignored) {}

        sistema.registrarRotacion(
                txtArea.getText(),
                txtFechaInicio.getText(),
                txtFechaFin.getText(),
                txtHoraInicio.getText(),
                txtHoraFin.getText(),
                cupo,
                txtDocente.getText()
        );
        lblMensaje.setStyle("-fx-text-fill: #059669; -fx-font-size: 12px;");
        lblMensaje.setText("✔ Rotacion guardada correctamente.");
        limpiarCampos();
        lblTituloFormulario.setText("Nueva rotacion mensual");
        actualizarPanel();
    }

    @FXML private void filtrarTodos()  { filtroActivo = "todos";   actualizarPanel(); }
    @FXML private void filtrarManana() { filtroActivo = "manana";  actualizarPanel(); }
    @FXML private void filtrarTarde()  { filtroActivo = "tarde";   actualizarPanel(); }
    @FXML private void filtrarNoche()  { filtroActivo = "noche";   actualizarPanel(); }

    @FXML
    private void limpiarFormulario() {
        limpiarCampos();
        lblMensaje.setText("");
        lblTituloFormulario.setText("Nueva rotacion mensual");
    }

    private void actualizarPanel() {
        panelRotaciones.getChildren().clear();
        List<Rotacion> lista = sistema.listarRotaciones();
        for (Rotacion r : lista) {
            String turno = turnoDeHora(r.getHoraInicio());
            if (filtroActivo.equals("todos") || filtroActivo.equals(turno)) {
                panelRotaciones.getChildren().add(crearTarjeta(r));
            }
        }
        if (panelRotaciones.getChildren().isEmpty()) {
            Label vacio = new Label("No hay rotaciones para este turno.");
            vacio.setStyle("-fx-text-fill: #9CA3AF; -fx-font-size: 13px; -fx-padding: 20;");
            panelRotaciones.getChildren().add(vacio);
        }
    }

    private VBox crearTarjeta(Rotacion r) {
        String colorAccent = colorPorHora(r.getHoraInicio());
        String turno = turnoDeHora(r.getHoraInicio());
        String turnoLabel = turno.substring(0, 1).toUpperCase() + turno.substring(1);
        String colorBadgeBg = badgeBg(turno);
        String colorBadgeFg = colorAccent;

        VBox tarjeta = new VBox(0);
        tarjeta.setPrefWidth(235);
        tarjeta.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 14;" +
            "-fx-border-color: #E5E7EB;" +
            "-fx-border-radius: 14;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);" +
            "-fx-cursor: hand;"
        );

        // Barra superior de color
        Rectangle barra = new Rectangle(235, 6);
        barra.setFill(Color.web(colorAccent));
        barra.setArcWidth(14);
        barra.setArcHeight(14);

        // Cuerpo
        VBox cuerpo = new VBox(8);
        cuerpo.setPadding(new Insets(12, 14, 14, 14));

        // Fila: area + badge turno
        HBox filaTitulo = new HBox(8);
        filaTitulo.setAlignment(Pos.CENTER_LEFT);
        Label lblArea = new Label(r.getArea());
        lblArea.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1E3A5F;");
        lblArea.setWrapText(true);
        lblArea.setMaxWidth(150);
        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);
        Label badge = new Label(turnoLabel);
        badge.setStyle("-fx-background-color: " + colorBadgeBg + "; -fx-text-fill: " + colorBadgeFg + ";" +
                       "-fx-padding: 3 8; -fx-background-radius: 20; -fx-font-size: 10px; -fx-font-weight: bold;");
        filaTitulo.getChildren().addAll(lblArea, espaciador, badge);

        // Hora grande
        Label lblHora = new Label(r.getHoraInicio() + "  –  " + r.getHoraFin());
        lblHora.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: " + colorAccent + ";");

        // Separador
        Region sep = new Region();
        sep.setPrefHeight(1);
        sep.setStyle("-fx-background-color: #F3F4F6;");

        // Fechas
        HBox fechas = filaInfo("📅", r.getFechaInicio() + " → " + r.getFechaFin());

        // Barra de progreso cupo (simulada con ocupacion aleatoria para mockup)
        int ocupados = (int)(r.getCupoMaximo() * 0.7);
        double pct = r.getCupoMaximo() > 0 ? (double) ocupados / r.getCupoMaximo() : 0;
        String colorBarra = pct >= 0.9 ? "#DC2626" : pct >= 0.7 ? "#F59E0B" : "#10B981";

        HBox filaCupo = new HBox(8);
        filaCupo.setAlignment(Pos.CENTER_LEFT);
        Label lblCupo = new Label("👥 " + ocupados + "/" + r.getCupoMaximo());
        lblCupo.setStyle("-fx-font-size: 11px; -fx-text-fill: #6B7280;");
        ProgressBar pb = new ProgressBar(pct);
        pb.setPrefWidth(80);
        pb.setPrefHeight(6);
        pb.setStyle("-fx-accent: " + colorBarra + "; -fx-background-radius: 4; -fx-background-color: #F3F4F6;");
        filaCupo.getChildren().addAll(lblCupo, pb);

        // Docente
        HBox docenteBox = filaInfo("🎓", r.getDocente());

        // Botón eliminar pequeño
        Button btnEliminar = new Button("✕ Eliminar");
        btnEliminar.setStyle(
            "-fx-background-color: #FEF2F2; -fx-text-fill: #DC2626;" +
            "-fx-background-radius: 8; -fx-padding: 4 10; -fx-font-size: 10px;" +
            "-fx-cursor: hand; -fx-border-color: #FECACA; -fx-border-radius: 8;"
        );

        cuerpo.getChildren().addAll(filaTitulo, lblHora, sep, fechas, filaCupo, docenteBox, btnEliminar);
        tarjeta.getChildren().addAll(barra, cuerpo);

        // Clic en tarjeta carga formulario
        tarjeta.setOnMouseClicked(e -> cargarEnFormulario(r));

        return tarjeta;
    }

    private void cargarEnFormulario(Rotacion r) {
        lblTituloFormulario.setText("Editando: " + r.getArea());
        txtArea.setText(r.getArea());
        txtFechaInicio.setText(r.getFechaInicio());
        txtFechaFin.setText(r.getFechaFin());
        txtHoraInicio.setText(r.getHoraInicio());
        txtHoraFin.setText(r.getHoraFin());
        txtCupo.setText(String.valueOf(r.getCupoMaximo()));
        txtDocente.setText(r.getDocente());
        lblMensaje.setText("");
    }

    private HBox filaInfo(String emoji, String texto) {
        Label icon = new Label(emoji);
        icon.setStyle("-fx-font-size: 11px;");
        Label lbl = new Label(texto);
        lbl.setStyle("-fx-font-size: 11px; -fx-text-fill: #6B7280;");
        lbl.setWrapText(true);
        lbl.setMaxWidth(185);
        HBox fila = new HBox(6, icon, lbl);
        fila.setAlignment(Pos.CENTER_LEFT);
        return fila;
    }

    private String colorPorHora(String horaInicio) {
        try {
            int hora = Integer.parseInt(horaInicio.split(":")[0]);
            if (hora < 12) return "#1F8A8A";
            if (hora < 18) return "#E07B39";
            return "#5A4B8A";
        } catch (Exception e) { return "#1F8A8A"; }
    }

    private String turnoDeHora(String horaInicio) {
        try {
            int hora = Integer.parseInt(horaInicio.split(":")[0]);
            if (hora < 12) return "manana";
            if (hora < 18) return "tarde";
            return "noche";
        } catch (Exception e) { return "mañana"; }
    }

    private String badgeBg(String turno) {
        return switch (turno) {
            case "tarde"  -> "#FEF0E7";
            case "noche"  -> "#EDE9F6";
            default       -> "#E5F6F6";
        };
    }

    private String mesActual() {
        String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio",
                          "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        java.time.LocalDate hoy = java.time.LocalDate.now();
        return meses[hoy.getMonthValue() - 1] + " " + hoy.getYear();
    }


    private void limpiarCampos() {
        txtArea.clear(); txtFechaInicio.clear(); txtFechaFin.clear();
        txtHoraInicio.clear(); txtHoraFin.clear();
        txtCupo.clear(); txtDocente.clear();
    }
}