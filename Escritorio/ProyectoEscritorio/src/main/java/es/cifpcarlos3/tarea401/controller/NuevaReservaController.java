package es.cifpcarlos3.tarea401.controller;

import es.cifpcarlos3.tarea401.dao.ReservaDAO;
import es.cifpcarlos3.tarea401.model.Reserva;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class NuevaReservaController {

    @FXML
    private TextField dniField;
    @FXML
    private TextField habitacionField;
    @FXML
    private DatePicker fechaInicioPicker;
    @FXML
    private DatePicker fechaFinPicker;

    @FXML
    private void onSaveClick() {
        String dni = dniField.getText();
        String habitacionStr = habitacionField.getText();

        if (dni.isEmpty() || habitacionStr.isEmpty() || fechaInicioPicker.getValue() == null
                || fechaFinPicker.getValue() == null) {
            showAlert("Error", "Todos los campos son obligatorios.");
            return;
        }

        try {
            int habitacion = Integer.parseInt(habitacionStr);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fechaInicio = fechaInicioPicker.getValue().format(formatter);
            String fechaFin = fechaFinPicker.getValue().format(formatter);

            Reserva nuevaReserva = new Reserva(0, dni, habitacion, fechaInicio, fechaFin);
            ReservaDAO dao = new ReservaDAO();

            if (dao.insertReserva(nuevaReserva)) {
                closeWindow();
            } else {
                showAlert("Error", "No se pudo guardar la reserva.");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "El número de habitación debe ser un número entero.");
        } catch (SQLException e) {
            showAlert("Error de Base de Datos", "No se pudo guardar: " + e.getMessage());
        }
    }

    @FXML
    private void onCancelClick() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) dniField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
