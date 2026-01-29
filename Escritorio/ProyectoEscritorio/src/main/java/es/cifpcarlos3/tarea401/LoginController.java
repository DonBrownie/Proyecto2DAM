package es.cifpcarlos3.tarea401;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
        // Here you would add authentication logic
        // For now, we just navigate to the main layout

        System.out.println("Login button clicked!");

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-layout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add(MainApplication.class.getResource("styles.css").toExternalForm());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Gestión DonBrownie");
        stage.setScene(scene);
        stage.centerOnScreen();
    }
}
