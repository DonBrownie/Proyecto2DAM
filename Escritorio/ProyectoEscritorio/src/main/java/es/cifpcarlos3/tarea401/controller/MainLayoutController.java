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

public class MainLayoutController implements Initializable {

    @FXML
    private Label headerTitle;

    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load Home content by default
        loadView("home-content.fxml", "INICIO");
    }

    private void loadView(String fxmlFileName, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlFileName));

            // Special handling for home-content to set its controller dynamically if
            // needed,
            // or just rely on the FXML. But home-content might need access to this
            // controller
            // to trigger navigations (like clicking "Reservas" button in home should go to
            // Reservas view).
            // For simplicity, we can set a controller or just let it bubble up events if we
            // had a shared model,
            // but here we will just load it.
            // BETTER APPROACH: Give HomeContentController a reference to this
            // MainLayoutController.

            // Let's first just load it.
            Parent view = loader.load();

            // If the view is Home, we might want to hook up its buttons to this
            // controller's navigation methods.
            // But for now, let's keep it simple.
            // If the home buttons need to switch views, the home controller needs a way to
            // ask MainLayout to do so.
            // We can pass `this` to the sub-controller if we want.
            if ("home-content.fxml".equals(fxmlFileName)) {
                HomeContentController homeController = loader.getController();
                homeController.setMainLayoutController(this);
            }

            contentArea.getChildren().setAll(view);
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
    public void onPerfilClick(ActionEvent event) {
        loadView("perfil-view.fxml", "PERFIL");
    }
}
