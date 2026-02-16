package es.cifpcarlos3.tarea401.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomeContentController {

    private MainLayoutController mainLayoutController;

    public void setMainLayoutController(MainLayoutController mainLayoutController) {
        this.mainLayoutController = mainLayoutController;
    }

    @FXML
    private void onReservasBtnClick(ActionEvent event) {
        if (mainLayoutController != null) {
            mainLayoutController.onReservasClick(event);
        }
    }

    @FXML
    private void onNominasBtnClick(ActionEvent event) {
        if (mainLayoutController != null) {
            mainLayoutController.onNominasClick(event);
        }
    }

    @FXML
    private void onCalendarioBtnClick(ActionEvent event) {
        if (mainLayoutController != null) {
            mainLayoutController.onCalendarioClick(event);
        }
    }

    @FXML
    private void onPerfilBtnClick(ActionEvent event) {
        if (mainLayoutController != null) {
            mainLayoutController.onPerfilClick(event);
        }
    }
}
