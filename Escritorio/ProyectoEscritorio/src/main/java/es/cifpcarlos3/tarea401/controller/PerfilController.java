package es.cifpcarlos3.tarea401.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

import es.cifpcarlos3.tarea401.MainApplication;
import javafx.scene.control.Label;
import es.cifpcarlos3.tarea401.model.Usuario;

public class PerfilController {

    @FXML
    private Label userNameLabel;

    @FXML
    public void initialize() {
        Usuario user = MainApplication.currentUser;
        if (user != null) {
            String fullName = user.getNombre() + " " + user.getApellido1();
            userNameLabel.setText(fullName);
        }
    }

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
