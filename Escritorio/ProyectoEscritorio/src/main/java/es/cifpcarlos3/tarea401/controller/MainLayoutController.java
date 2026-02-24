package es.cifpcarlos3.tarea401.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import es.cifpcarlos3.tarea401.MainApplication;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador de la ventana "envoltorio" principal.
 * Mantiene el menú lateral fijo y solo cambia el contenido del centro
 * (ContentArea)
 * según el botón que se pulse.
 */
public class MainLayoutController implements Initializable {

    @FXML
    private Label headerTitle; // El título de cabecera que va cambiando

    @FXML
    private StackPane contentArea; // La caja central donde cargaremos las distintas pantallas (reservas,
                                   // nóminas...)

    @FXML
    private javafx.scene.control.Button btnGestion; // El botón especial que solo ven los Gerentes

    /**
     * Este método se ejecuta automáticamente cuando JavaFX carga la pantalla.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Por defecto al entrar a la app, cargamos la pantalla de INICIO dentro de
        // contentArea
        loadView("home-content.fxml", "INICIO");

        // Habilitar botón de gestión solo para los usuarios que tengan el puesto de
        // "Gerente"
        if (MainApplication.currentUser != null
                && "Gerente".equalsIgnoreCase(MainApplication.currentUser.getPuesto())) {
            btnGestion.setManaged(true);
            btnGestion.setVisible(true);
        }
    }

    /**
     * Método centralizado para cambiar de pantalla.
     * Carga un archivo FXML y lo incrusta en el "contentArea".
     */
    private void loadView(String fxmlFileName, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlFileName));
            Parent view = loader.load();

            // Si cargamos el "Home", le pasamos a su controlador este ("this") controlador
            // principal
            if ("home-content.fxml".equals(fxmlFileName)) {
                HomeContentController homeController = loader.getController();
                homeController.setMainLayoutController(this);
            }

            // Cambiamos el centro de la pantalla por la nueva vista
            contentArea.getChildren().setAll(view);
            // Cambiamos el título en grande
            headerTitle.setText(title);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not load view: " + fxmlFileName);
        }
    }


    @FXML
    public void onHomeClick(ActionEvent event) {
        loadView("home-content.fxml", "INICIO");
    }

    @FXML
    public void onReservasClick(ActionEvent event) {
        loadView("reservas-view.fxml", "RESERVAS");
    }

    @FXML
    public void onNominasClick(ActionEvent event) {
        loadView("nominas-view.fxml", "NOMINAS");
    }

    @FXML
    public void onCalendarioClick(ActionEvent event) {
        loadView("calendario-view.fxml", "CALENDARIO");
    }

    @FXML
    public void onGestionClick(ActionEvent event) {
        loadView("gestion-gerente-view.fxml", "GESTIÓN GERENTE");
    }

    @FXML
    public void onPerfilClick(ActionEvent event) {
        loadView("perfil-view.fxml", "PERFIL");
    }
}
