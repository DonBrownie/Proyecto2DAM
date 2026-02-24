package es.cifpcarlos3.tarea401.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controlador para la pantalla principal de "Inicio" (Home).
 * Contiene los botones grandes que llevan a otras secciones de la aplicación.
 */
public class HomeContentController {

    // Referencia al controlador principal (MainLayout) para poder pedirle que
    // cambie de vista
    private MainLayoutController mainLayoutController;

    /**
     * Guarda la referencia al MainLayout para poder interactuar con él.
     */
    public void setMainLayoutController(MainLayoutController mainLayoutController) {
        this.mainLayoutController = mainLayoutController;
    }

    /**
     * Se ejecuta al pulsar el botón de Reservas.
     */
    @FXML
    private void onReservasBtnClick(ActionEvent event) {
        if (mainLayoutController != null) {
            mainLayoutController.onReservasClick(event);
        }
    }

    /**
     * Se ejecuta al pulsar el botón de Nóminas.
     */
    @FXML
    private void onNominasBtnClick(ActionEvent event) {
        if (mainLayoutController != null) {
            mainLayoutController.onNominasClick(event);
        }
    }

    /**
     * Se ejecuta al pulsar el botón del Calendario.
     */
    @FXML
    private void onCalendarioBtnClick(ActionEvent event) {
        if (mainLayoutController != null) {
            mainLayoutController.onCalendarioClick(event);
        }
    }

    /**
     * Se ejecuta al pulsar el botón de Mi Perfil.
     */
    @FXML
    private void onPerfilBtnClick(ActionEvent event) {
        if (mainLayoutController != null) {
            mainLayoutController.onPerfilClick(event);
        }
    }
}
