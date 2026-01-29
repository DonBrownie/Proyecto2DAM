package es.cifpcarlos3.tarea401;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class PerfilController {

    @FXML
    public void onLogoutClick(MouseEvent event) {
        try {
            // Return to Login Screen
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            scene.getStylesheets().add(MainApplication.class.getResource("styles.css").toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Tarea Informes - Login");
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
