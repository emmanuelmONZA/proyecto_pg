package com.hospital.practicantes.controllers;

import com.hospital.practicantes.entities.Practicante;
import com.hospital.practicantes.facade.SistemaFacade;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PracticanteController {

        private final SistemaFacade sistema = SistemaFacade.getInstancia();

        @FXML
        private TextField txtNombre;
        @FXML
        private TextField txtDocumento;
        @FXML
        private TextField txtUniversidad;
        @FXML
        private TextField txtSemestre;
        @FXML
        private TextField txtPrograma;
        @FXML
        private TextField txtDocente;
        @FXML
        private CheckBox chkInduccion;
        @FXML
        private CheckBox chkArl;
        @FXML
        private TextField txtEstado;
        @FXML
        private TextField txtBuscar;
        @FXML
        private Label lblMensaje;
        @FXML
        private Label lblTituloFormulario;

        @FXML
        private TableView<Practicante> tablaPracticantes;
        @FXML
        private TableColumn<Practicante, Integer> colId;
        @FXML
        private TableColumn<Practicante, String> colNombre;
        @FXML
        private TableColumn<Practicante, String> colDocumento;
        @FXML
        private TableColumn<Practicante, String> colUniversidad;
        @FXML
        private TableColumn<Practicante, String> colPrograma;
        @FXML
        private TableColumn<Practicante, String> colDocente;
        @FXML
        private TableColumn<Practicante, String> colEstado;

        private ObservableList<Practicante> listaTodos;

        @FXML
        public void initialize() {
                colId.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getId()));
                colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
                colDocumento.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDocumento()));
                colUniversidad.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getUniversidad()));
                colPrograma.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getPrograma()));
                colDocente.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDocente()));
                colEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstado()));

                actualizarTabla();

                // Busqueda en tiempo real
                txtBuscar.textProperty().addListener((obs, oldVal, newVal) -> filtrar(newVal));

                // Clic en fila para cargar en formulario
                tablaPracticantes.getSelectionModel().selectedItemProperty().addListener(
                                (obs, oldVal, selected) -> {
                                        if (selected != null)
                                                cargarEnFormulario(selected);
                                });
        }

        @FXML
        private void guardarPracticante() {
                if (txtNombre.getText().isBlank() || txtDocumento.getText().isBlank()) {
                        lblMensaje.setStyle("-fx-text-fill: #DC2626; -fx-font-size: 12px;");
                        lblMensaje.setText("⚠ Nombre y documento son obligatorios.");
                        return;
                }
                sistema.registrarPracticante(
                                txtNombre.getText(),
                                txtDocumento.getText(),
                                txtUniversidad.getText(),
                                txtSemestre.getText(),
                                txtPrograma.getText(),
                                txtDocente.getText(),
                                chkInduccion.isSelected(),
                                chkArl.isSelected(),
                                txtEstado.getText());
                lblMensaje.setStyle("-fx-text-fill: #059669; -fx-font-size: 12px;");
                lblMensaje.setText("✔ Practicante guardado correctamente.");
                limpiarCampos();
                actualizarTabla();
        }

        @FXML
        private void limpiarFormulario() {
                limpiarCampos();
                lblMensaje.setText("");
                lblTituloFormulario.setText("Nuevo practicante");
        }

        private void cargarEnFormulario(Practicante p) {
                lblTituloFormulario.setText("Editando: " + p.getNombre());
                txtNombre.setText(p.getNombre());
                txtDocumento.setText(p.getDocumento());
                txtUniversidad.setText(p.getUniversidad());
                txtSemestre.setText(p.getSemestre());
                txtPrograma.setText(p.getPrograma());
                txtDocente.setText(p.getDocente());
                chkInduccion.setSelected(p.isInducionRealizada());
                chkArl.setSelected(p.isArlVigente());
                txtEstado.setText(p.getEstado());
                lblMensaje.setText("");
        }

        private void filtrar(String texto) {
                if (texto == null || texto.isBlank()) {
                        tablaPracticantes.setItems(listaTodos);
                        return;
                }
                String lower = texto.toLowerCase();
                FilteredList<Practicante> filtrados = new FilteredList<>(listaTodos,
                                p -> p.getNombre().toLowerCase().contains(lower) ||
                                                p.getDocumento().toLowerCase().contains(lower));
                tablaPracticantes.setItems(filtrados);
        }

        private void actualizarTabla() {
                listaTodos = FXCollections.observableArrayList(sistema.listarPracticantes());
                tablaPracticantes.setItems(listaTodos);
        }

        private void limpiarCampos() {
                txtNombre.clear();
                txtDocumento.clear();
                txtUniversidad.clear();
                txtSemestre.clear();
                txtPrograma.clear();
                txtDocente.clear();
                chkInduccion.setSelected(false);
                chkArl.setSelected(false);
                txtEstado.clear();
        }
}