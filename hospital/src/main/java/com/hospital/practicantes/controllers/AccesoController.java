package com.hospital.practicantes.controllers;

import com.hospital.practicantes.entities.RegistroAcceso;
import com.hospital.practicantes.facade.SistemaFacade;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class AccesoController {

    private final SistemaFacade sistema = SistemaFacade.getInstancia();

    @FXML private TextField        txtPracticante;
    @FXML private TextField        txtFecha;
    @FXML private TextField        txtHora;
    @FXML private ComboBox<String> cmbServicio;
    @FXML private TextField        txtTipo;
    @FXML private TextField        txtBuscar;

    @FXML private Label lblMensaje;
    @FXML private Label lblTituloFormulario;
    @FXML private Label lblTotalAdentro;
    @FXML private Label lblTotalEntradas;
    @FXML private Label lblTotalSalidas;

    @FXML private Button btnTipoEntrada;
    @FXML private Button btnTipoSalida;

    @FXML private TableView<RegistroAcceso>            tablaAccesos;
    @FXML private TableColumn<RegistroAcceso, Integer> colId;
    @FXML private TableColumn<RegistroAcceso, String>  colPracticante;
    @FXML private TableColumn<RegistroAcceso, String>  colFecha;
    @FXML private TableColumn<RegistroAcceso, String>  colHora;
    @FXML private TableColumn<RegistroAcceso, String>  colServicio;
    @FXML private TableColumn<RegistroAcceso, String>  colTipo;

    private ObservableList<RegistroAcceso> listaTodos;
    private String tipoSeleccionado = "Entrada";
    private RegistroAcceso accesoEnEdicion = null;

    @FXML
    public void initialize() {
        cmbServicio.setItems(FXCollections.observableArrayList(
            "Urgencias", "Pediatria", "UCI", "Medicina Interna",
            "Cirugia", "Hospitalizacion piso 5", "Laboratorio",
            "Terapia Fisica", "Terapia Respiratoria",
            "Bacteriologia", "Enfermeria", "Internado Medico"
        ));

        colId.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getId()));
        colPracticante.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getPracticante()));
        colFecha.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFecha()));
        colHora.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getHora()));
        colServicio.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getServicio()));
        colTipo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTipo()));

        txtFecha.setText(LocalDate.now().toString());
        txtTipo.setText("Entrada");

        actualizarTabla();

        txtBuscar.textProperty().addListener((obs, old, val) -> filtrarPorTexto(val));

        tablaAccesos.getSelectionModel().selectedItemProperty().addListener(
            (obs, old, sel) -> { if (sel != null) cargarEnFormulario(sel); });
    }

    @FXML
    private void guardarAcceso() {
        if (txtPracticante.getText().isBlank() || cmbServicio.getValue() == null) {
            lblMensaje.setStyle("-fx-text-fill: #DC2626; -fx-font-size: 12px;");
            lblMensaje.setText("⚠ Practicante y servicio son obligatorios.");
            return;
        }

        if (accesoEnEdicion != null) {
            // MODO EDICION
            accesoEnEdicion.setPracticante(txtPracticante.getText());
            accesoEnEdicion.setFecha(txtFecha.getText());
            accesoEnEdicion.setHora(txtHora.getText());
            accesoEnEdicion.setServicio(cmbServicio.getValue());
            accesoEnEdicion.setTipo(tipoSeleccionado);
            sistema.actualizarAcceso(accesoEnEdicion);
            lblMensaje.setStyle("-fx-text-fill: #059669; -fx-font-size: 12px;");
            lblMensaje.setText("✔ Acceso actualizado correctamente.");
        } else {
            // MODO NUEVO
            sistema.registrarAcceso(
                txtPracticante.getText(),
                txtFecha.getText(),
                txtHora.getText(),
                cmbServicio.getValue(),
                tipoSeleccionado
            );
            lblMensaje.setStyle("-fx-text-fill: #059669; -fx-font-size: 12px;");
            lblMensaje.setText("✔ Acceso registrado correctamente.");
        }
        limpiarCampos();
        actualizarTabla();
    }

    @FXML
    private void eliminarAcceso() {
        RegistroAcceso seleccionado = tablaAccesos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            lblMensaje.setStyle("-fx-text-fill: #DC2626; -fx-font-size: 12px;");
            lblMensaje.setText("⚠ Selecciona un registro de la tabla para eliminar.");
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Eliminar registro");
        confirmacion.setHeaderText("¿Eliminar el acceso de " + seleccionado.getPracticante() + "?");
        confirmacion.setContentText("Esta accion no se puede deshacer.");
        ButtonType btnSi = new ButtonType("✅ Sí, eliminar");
        ButtonType btnNo = new ButtonType("❌ Cancelar");
        confirmacion.getButtonTypes().setAll(btnSi, btnNo);

        confirmacion.showAndWait().ifPresent(resultado -> {
            if (resultado == btnSi) {
                sistema.eliminarAcceso(seleccionado.getId());
                lblMensaje.setStyle("-fx-text-fill: #059669; -fx-font-size: 12px;");
                lblMensaje.setText("✔ Registro eliminado correctamente.");
                limpiarCampos();
                actualizarTabla();
            }
        });
    }

    @FXML
    private void seleccionarEntrada() {
        tipoSeleccionado = "Entrada";
        txtTipo.setText("Entrada");
        btnTipoEntrada.setStyle(
            "-fx-background-color: #ECFDF5; -fx-text-fill: #059669;" +
            "-fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 10;" +
            "-fx-cursor: hand; -fx-border-color: #059669; -fx-border-radius: 8;");
        btnTipoSalida.setStyle(
            "-fx-background-color: #F9FAFB; -fx-text-fill: #6B7280;" +
            "-fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 10;" +
            "-fx-cursor: hand; -fx-border-color: #E5E7EB; -fx-border-radius: 8;");
    }

    @FXML
    private void seleccionarSalida() {
        tipoSeleccionado = "Salida";
        txtTipo.setText("Salida");
        btnTipoSalida.setStyle(
            "-fx-background-color: #FEF2F2; -fx-text-fill: #DC2626;" +
            "-fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 10;" +
            "-fx-cursor: hand; -fx-border-color: #DC2626; -fx-border-radius: 8;");
        btnTipoEntrada.setStyle(
            "-fx-background-color: #F9FAFB; -fx-text-fill: #6B7280;" +
            "-fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 10;" +
            "-fx-cursor: hand; -fx-border-color: #E5E7EB; -fx-border-radius: 8;");
    }

    @FXML private void filtrarTodos()    { tablaAccesos.setItems(listaTodos); }

    @FXML
    private void filtrarEntradas() {
        tablaAccesos.setItems(new FilteredList<>(listaTodos,
            r -> "Entrada".equalsIgnoreCase(r.getTipo())));
    }

    @FXML
    private void filtrarSalidas() {
        tablaAccesos.setItems(new FilteredList<>(listaTodos,
            r -> "Salida".equalsIgnoreCase(r.getTipo())));
    }

    @FXML
    private void mostrarEvacuacion() {
        long adentro = listaTodos.stream()
            .filter(r -> "Entrada".equalsIgnoreCase(r.getTipo())).count();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Lista de Evacuacion");
        alert.setHeaderText("🚨 Estudiantes que ingresaron — Total: " + adentro);
        StringBuilder sb = new StringBuilder();
        listaTodos.stream()
            .filter(r -> "Entrada".equalsIgnoreCase(r.getTipo()))
            .forEach(r -> sb.append("• ").append(r.getPracticante())
                .append(" — ").append(r.getServicio())
                .append(" (").append(r.getHora()).append(")\n"));
        alert.setContentText(sb.isEmpty() ?
            "No hay estudiantes registrados adentro." : sb.toString());
        alert.showAndWait();
    }

    @FXML
    private void limpiarFormulario() {
        limpiarCampos();
        lblMensaje.setText("");
    }

    private void cargarEnFormulario(RegistroAcceso r) {
        accesoEnEdicion = r;
        lblTituloFormulario.setText("✏ Editando: " + r.getPracticante());
        txtPracticante.setText(r.getPracticante());
        txtFecha.setText(r.getFecha());
        txtHora.setText(r.getHora());
        cmbServicio.setValue(r.getServicio());
        txtTipo.setText(r.getTipo());
        tipoSeleccionado = r.getTipo();
        if ("Entrada".equalsIgnoreCase(r.getTipo())) seleccionarEntrada();
        else seleccionarSalida();
        lblMensaje.setText("");
    }

    private void filtrarPorTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            tablaAccesos.setItems(listaTodos);
            return;
        }
        String lower = texto.toLowerCase();
        tablaAccesos.setItems(new FilteredList<>(listaTodos,
            r -> r.getPracticante().toLowerCase().contains(lower) ||
                 r.getServicio().toLowerCase().contains(lower)));
    }

    private void actualizarTabla() {
        listaTodos = FXCollections.observableArrayList(sistema.listarAccesos());
        tablaAccesos.setItems(listaTodos);
        long entradas = listaTodos.stream()
            .filter(r -> "Entrada".equalsIgnoreCase(r.getTipo())).count();
        long salidas = listaTodos.stream()
            .filter(r -> "Salida".equalsIgnoreCase(r.getTipo())).count();
        lblTotalEntradas.setText(String.valueOf(entradas));
        lblTotalSalidas.setText(String.valueOf(salidas));
        lblTotalAdentro.setText(String.valueOf(Math.max(entradas - salidas, 0)));
    }

    private void limpiarCampos() {
        accesoEnEdicion = null;
        lblTituloFormulario.setText("Registrar acceso");
        txtPracticante.clear();
        txtFecha.setText(LocalDate.now().toString());
        txtHora.clear();
        cmbServicio.setValue(null);
        txtTipo.setText("Entrada");
        tipoSeleccionado = "Entrada";
        seleccionarEntrada();
    }
}