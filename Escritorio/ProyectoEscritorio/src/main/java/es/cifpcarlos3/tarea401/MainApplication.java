package es.cifpcarlos3.tarea401;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import es.cifpcarlos3.tarea401.model.Usuario;


public class MainApplication extends Application {
    public static Usuario currentUser;

    @Override
    public void start(Stage stage) throws IOException {
        // Initialize Database

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add(MainApplication.class.getResource("styles.css").toExternalForm());
        stage.setTitle("Tarea Informes");
        stage.setScene(scene);
        stage.show();
    }
}
