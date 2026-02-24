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
import javafx.scene.layout.VBox;

/**
 * Este es el controlador para la pantalla de "Perfil".
 * Básicamente sirve para que el usuario vea quién es y pueda cerrar sesión o ir
 * a su cuenta.
 */
public class PerfilController {

    @FXML
    private Label userNameLabel;

    /**
     * Este método se ejecuta en cuanto se carga la vista del perfil.
     * Lo que hace es buscar al usuario que está logueado en la app (el currentUser)
     * y pone su nombre y primer apellido en el Label de arriba para que quede
     * personalizado.
     */
    @FXML
    public void initialize() {
        Usuario user = MainApplication.currentUser;
        if (user != null) {
            String fullName = user.getNombre() + " " + user.getApellido1();
            userNameLabel.setText(fullName);
        }
    }

    /**
     * Si el usuario hace clic en "Mi Cuenta", venimos aquí.
     * Lo que hacemos es cargar el FXML de la cuenta y meterlo en el contenedor
     * principal
     * para que cambie la vista sin abrir ventanas nuevas.
     */
    @FXML
    public void onCuentaClick(MouseEvent event) {
        try {
            // Cargamos la vista de detalles de la cuenta ("cuenta-view.fxml")
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("cuenta-view.fxml"));
            VBox cuentaView = fxmlLoader.load();

            // Aquí buscamos el contenedor padre para cambiar lo que hay dentro por la vista
            // de cuenta
            VBox parent = (VBox) userNameLabel.getParent().getParent().getParent();
            parent.getChildren().setAll(cuentaView);
        } catch (IOException e) {
            // Si algo falla al cargar el FXML, lo soltamos por consola
            e.printStackTrace();
        }
    }

    /**
     * Este es el botón para cerrar sesión.
     * Te manda de vuelta a la pantalla de login y resetea la ventana.
     */
    @FXML
    public void onLogoutClick(MouseEvent event) {
        try {
            // Volvemos a cargar la pantalla de login
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            scene.getStylesheets().add(MainApplication.class.getResource("styles.css").toExternalForm());

            // Pillamos la ventana actual y le cambiamos la escena por la del login
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Tarea Informes - Login");
            stage.setScene(scene);
            stage.centerOnScreen(); // Esto es para que se quede centradito
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
