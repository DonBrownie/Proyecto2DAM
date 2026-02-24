package es.cifpcarlos3.tarea401.controller;

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
import es.cifpcarlos3.tarea401.MainApplication;

/**
 * Controlador de la pantalla de Inicio de Sesión (Login).
 */
public class LoginController {

    @FXML
    private TextField usernameField; // Campo donde se escribe el nombre de usuario

    @FXML
    private PasswordField passwordField; // Campo donde se escribe la contraseña oculta

    /**
     * Método que se lanza cuando el usuario hace clic en el botón "Iniciar Sesión"
     * (Login).
     */
    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
        // Obtenemos los textos de los campos y quitamos los espacios en blanco con
        // trim()
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        System.out.println("Intento de login con usuario: '" + username + "'");

        // Si están vacíos, mostramos una alerta
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error de validación", "Por favor, introduzca usuario y contraseña.");
            return;
        }

        // Usamos el DAO para comprobar en la base de datos si el usuario existe
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            Usuario usuario = usuarioDAO.login(username, password);
            if (usuario != null) {
                // Si el usuario existe, lo guardamos en una variable global
                // (MainApplication.currentUser)
                // para saber quién está navegando por la app
                MainApplication.currentUser = usuario;

                System.out.println("Login exitoso: " + usuario.getNombre());

                // Cargamos la siguiente pantalla ("main-layout.fxml") que es el contenedor
                // principal
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-layout.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                scene.getStylesheets().add(MainApplication.class.getResource("styles.css").toExternalForm());

                // Cambiamos la "ventana" (Stage) para que ahora muestre la nueva escena
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Gestión Hotel");
                stage.setScene(scene);
                stage.centerOnScreen();
            } else {
                // Si el DAO devuelve null, significa que las credenciales están mal
                showAlert(Alert.AlertType.ERROR, "Error de inicio de sesión", "Usuario o contraseña incorrectos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error del sistema", "No se pudo conectar a la base de datos.");
        }
    }

    /**
     * Herramienta auxiliar para mostrar ventanas de error/aviso emergentes
     * (Pop-ups).
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
