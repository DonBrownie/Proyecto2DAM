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

/**
 * Este es el controlador para la ventana de crear una reserva nueva.
 * Es un formulario sencillo donde metemos los datos del cliente y la
 * habitación.
 */
public class NuevaReservaController {

    // Casillas de texto y selectores de fecha del FXML
    @FXML
    private TextField dniField;
    @FXML
    private TextField habitacionField;
    @FXML
    private DatePicker fechaInicioPicker;
    @FXML
    private DatePicker fechaFinPicker;

    /**
     * Este método se activa al pulsar el botón de "Guardar".
     * Valida que todo esté relleno y que las fechas estén bien antes de mandarlo a
     * la base de datos.
     */
    @FXML
    private void onSaveClick() {
        String dni = dniField.getText();
        String habitacionStr = habitacionField.getText();

        // Primero miramos que no haya dejado ningún campo vacío
        if (dni.isEmpty() || habitacionStr.isEmpty() || fechaInicioPicker.getValue() == null
                || fechaFinPicker.getValue() == null) {
            showAlert("Error", "Oye, que todos los campos son obligatorios.");
            return;
        }

        // Comprobamos que no se haya liado con las fechas (la de fin no puede ser antes
        // que la de inicio)
        if (fechaFinPicker.getValue().isBefore(fechaInicioPicker.getValue())) {
            showAlert("Error de Fechas", "La fecha de fin no puede ser anterior a la de inicio, ¡no tiene sentido!");
            return;
        }

        try {
            // Pasamos la habitación a número y las fechas a texto con el formato que le
            // gusta a SQL
            int habitacion = Integer.parseInt(habitacionStr);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fechaInicio = fechaInicioPicker.getValue().format(formatter);
            String fechaFin = fechaFinPicker.getValue().format(formatter);

            // Creamos el objeto de la reserva y usamos el DAO para guardarlo
            Reserva nuevaReserva = new Reserva(null, dni, habitacion, fechaInicio, fechaFin);
            ReservaDAO dao = new ReservaDAO();

            if (dao.insertReserva(nuevaReserva)) {
                // Si se guarda bien, cerramos esta ventanita
                closeWindow();
            } else {
                showAlert("Error", "Vaya, no se ha podido guardar la reserva...");
            }
        } catch (NumberFormatException e) {
            // Por si escriben letras donde va el número de habitación
            showAlert("Error", "El número de habitación tiene que ser un número de verdad.");
        } catch (SQLException e) {
            // Fallo típico de conexión o de la propia base de datos
            showAlert("Error de Base de Datos", "Ha habido un jaleo con la base de datos: " + e.getMessage());
        }
    }

    /**
     * Si le dan a cancelar, pues cerramos la ventana y no hacemos nada.
     */
    @FXML
    private void onCancelClick() {
        closeWindow();
    }

    /**
     * Un pequeño truco para cerrar la ventana actual pillando el Scene de
     * cualquiera de sus controles.
     */
    private void closeWindow() {
        Stage stage = (Stage) dniField.getScene().getWindow();
        stage.close();
    }

    /**
     * Función para sacar los avisos de error típicos de JavaFX.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
