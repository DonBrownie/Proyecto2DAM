package es.cifpcarlos3.tarea401;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import es.cifpcarlos3.tarea401.dao.UsuarioDAO;
import es.cifpcarlos3.tarea401.model.Usuario;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error de validación", "Por favor, introduzca usuario y contraseña.");
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            Usuario usuario = usuarioDAO.login(username, password);
            if (usuario != null) {
                // Store user in valid session (simplified using a Singleton or static field in
                // MainApplication for this scope)
                MainApplication.currentUser = usuario;

                System.out.println("Login exitoso: " + usuario.getNombre());

                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-layout.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                scene.getStylesheets().add(MainApplication.class.getResource("styles.css").toExternalForm());

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Gestión DonBrownie");
                stage.setScene(scene);
                stage.centerOnScreen();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error de inicio de sesión", "Usuario o contraseña incorrectos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error del sistema", "No se pudo conectar a la base de datos.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
