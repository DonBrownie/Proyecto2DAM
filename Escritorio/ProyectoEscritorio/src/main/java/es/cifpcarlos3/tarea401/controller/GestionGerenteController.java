package es.cifpcarlos3.tarea401.controller;

import es.cifpcarlos3.tarea401.utils.ApiClient;
import es.cifpcarlos3.tarea401.utils.SecurityUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.util.Map;
import java.util.HashMap;

public class GestionGerenteController {

    // Campos Usuario
    @FXML
    private TextField uNombre;
    @FXML
    private TextField uApellido1;
    @FXML
    private TextField uPuesto;
    @FXML
    private TextField uDni;
    @FXML
    private TextField uTelefono;
    @FXML
    private TextField uLogin;
    @FXML
    private PasswordField uPass;

    // Campos Cliente
    @FXML
    private TextField cDni;
    @FXML
    private TextField cNombre;
    @FXML
    private TextField cApellidos;

    @FXML
    public void registrarUsuario() {
        if (uNombre.getText().isEmpty() || uLogin.getText().isEmpty() || uPass.getText().isEmpty()) {
            showAlert("Error", "Los campos Nombre, Usuario y Contraseña son obligatorios.");
            return;
        }

        try {
            // Hasheamos la contraseña antes de enviarla
            String hashedPassword = SecurityUtils.hashPassword(uLogin.getText(), uPass.getText());

            Map<String, Object> data = new HashMap<>();
            data.put("nombre", uNombre.getText());
            data.put("apellido1", uApellido1.getText());
            data.put("puesto", uPuesto.getText());
            data.put("dni", uDni.getText());
            data.put("telefono", Integer.parseInt(uTelefono.getText()));
            data.put("username", uLogin.getText());
            data.put("password", hashedPassword);

            ApiClient.post("/admin/usuarios", data, Void.class);
            showAlert("Éxito", "Usuario registrado correctamente.");
            limpiarCamposUsuario();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo registrar el usuario: " + e.getMessage());
        }
    }

    @FXML
    public void registrarCliente() {
        if (cDni.getText().isEmpty() || cNombre.getText().isEmpty()) {
            showAlert("Error", "DNI y Nombre son obligatorios.");
            return;
        }

        try {
            Map<String, String> data = Map.of(
                    "dni", cDni.getText(),
                    "nombre", cNombre.getText(),
                    "apellidos", cApellidos.getText());

            ApiClient.post("/admin/clientes", data, Void.class);
            showAlert("Éxito", "Cliente registrado correctamente.");
            limpiarCamposCliente();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo registrar el cliente: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(title.equals("Error") ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void limpiarCamposUsuario() {
        uNombre.clear();
        uApellido1.clear();
        uPuesto.clear();
        uDni.clear();
        uTelefono.clear();
        uLogin.clear();
        uPass.clear();
    }

    private void limpiarCamposCliente() {
        cDni.clear();
        cNombre.clear();
        cApellidos.clear();
    }
}
