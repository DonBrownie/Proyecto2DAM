package es.cifpcarlos3.tarea401.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class CalendarioController {

    @FXML
    private Label monthYearLabel;

    @FXML
    private GridPane calendarGrid;

    private LocalDate selectedDate;

    public void initialize() {
        selectedDate = LocalDate.now();
        drawCalendar();
    }

    private void drawCalendar() {
        calendarGrid.getChildren().clear();

        // Month and Year Title
        String monthName = selectedDate.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES"));
        monthYearLabel.setText(monthName.toUpperCase() + " " + selectedDate.getYear());

        // Day Headers
        String[] daysOfWeek = { "L", "M", "M", "J", "V", "S", "D" };
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label dayHeader = new Label(daysOfWeek[i]);
            dayHeader.getStyleClass().add("calendar-day-header");
            calendarGrid.add(dayHeader, i, 0);
        }

        // Fill Days
        YearMonth yearMonth = YearMonth.from(selectedDate);
        LocalDate firstOfMonth = yearMonth.atDay(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue(); // 1 (Mon) to 7 (Sun)
        int daysInMonth = yearMonth.lengthOfMonth();

        int row = 1;
        int col = dayOfWeek - 1;

        LocalDate today = LocalDate.now();

        for (int day = 1; day <= daysInMonth; day++) {
            VBox dayCell = new VBox();
            dayCell.getStyleClass().add("calendar-day-cell");

            Text dayText = new Text(String.valueOf(day));
            dayText.getStyleClass().add("calendar-day-text");
            dayCell.getChildren().add(dayText);

            // Highlight today
            if (today.getYear() == selectedDate.getYear() &&
                    today.getMonth() == selectedDate.getMonth() &&
                    today.getDayOfMonth() == day) {
                dayCell.getStyleClass().add("calendar-day-today");
            }

            calendarGrid.add(dayCell, col, row);

            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }
    }

    @FXML
    private void onPreviousMonth() {
        selectedDate = selectedDate.minusMonths(1);
        drawCalendar();
    }

    @FXML
    private void onNextMonth() {
        selectedDate = selectedDate.plusMonths(1);
        drawCalendar();
    }
}
