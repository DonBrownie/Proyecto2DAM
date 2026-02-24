package es.cifpcarlos3.tarea401.controller;

import es.cifpcarlos3.tarea401.MainApplication;
import es.cifpcarlos3.tarea401.dao.UsuarioDAO;
import es.cifpcarlos3.tarea401.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CuentaController {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField apellido1Field;

    @FXML
    private TextField apellido2Field;

    @FXML
    private TextField telefonoField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    @FXML
    public void initialize() {
        Usuario currentUser = MainApplication.currentUser;
        if (currentUser != null) {
            nombreField.setText(currentUser.getNombre());
            apellido1Field.setText(currentUser.getApellido1());
            apellido2Field.setText(currentUser.getApellido2());
            telefonoField.setText(String.valueOf(currentUser.getTelefono()));
        }
    }

    @FXML
    public void onSaveClick(ActionEvent event) {
        String newNombre = nombreField.getText().trim();
        String newApellido1 = apellido1Field.getText().trim();
        String newApellido2 = apellido2Field.getText().trim();
        String newTelefonoStr = telefonoField.getText().trim();
        String newPassword = passwordField.getText().trim();

        if (newNombre.isEmpty() || newApellido1.isEmpty() || newApellido2.isEmpty() || newTelefonoStr.isEmpty()
                || newPassword.isEmpty()) {
            statusLabel.setText("Todos los campos (Nombre, Apellidos, Teléfono y Contraseña) son obligatorios.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        Usuario currentUser = MainApplication.currentUser;
        if (currentUser == null)
            return;

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean success = true;

        // Actualizar datos de usuario (nombre, apellidos, telefono)
        try {
            int newTelefono = Integer.parseInt(newTelefonoStr);
            currentUser.setNombre(newNombre);
            currentUser.setApellido1(newApellido1);
            currentUser.setApellido2(newApellido2);
            currentUser.setTelefono(newTelefono);

            boolean userUpdated = usuarioDAO.updateUsuario(currentUser);
            if (!userUpdated) {
                success = false;
                statusLabel.setText("Error al actualizar los datos del usuario.");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("El teléfono debe ser un número válido.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Actualizar contraseña con MessageDigest
        if (!newPassword.isEmpty()) {
            try {
                String hashedPassword = hashPassword(currentUser.getNombre(), newPassword);
                boolean pwdUpdated = usuarioDAO.updatePassword(currentUser.getId(), hashedPassword);
                if (!pwdUpdated) {
                    success = false;
                    statusLabel.setText("Error al actualizar la contraseña.");
                    statusLabel.setStyle("-fx-text-fill: red;");
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                statusLabel.setText("Error interno de encriptación.");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }
        }

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Los datos de la cuenta se han actualizado correctamente.");
            returnToProfile();
        }
    }

    @FXML
    public void onCancelClick(ActionEvent event) {
        returnToProfile();
    }

    private void returnToProfile() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("perfil-view.fxml"));
            VBox perfilView = fxmlLoader.load();

            VBox parent = (VBox) telefonoField.getParent().getParent().getParent();
            parent.getChildren().setAll(perfilView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String hashPassword(String username, String password) throws NoSuchAlgorithmException {
        // En el backend el hash se hace como: sha256(username + "$$" + password)
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String input = username + "$$" + password;
        byte[] hashBytes = digest.digest(input.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
