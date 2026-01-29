package es.cifpcarlos3.tarea401;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;

public class CalendarioController {

    @FXML
    private DatePicker datePicker;

    public void initialize() {
        datePicker.setValue(LocalDate.now());
    }
}
