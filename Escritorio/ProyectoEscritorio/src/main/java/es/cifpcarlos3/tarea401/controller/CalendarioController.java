package es.cifpcarlos3.tarea401.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import es.cifpcarlos3.tarea401.MainApplication;
import es.cifpcarlos3.tarea401.model.Usuario;

/**
 * Este es el controlador para el Calendario interactivo.
 * Sirve para que el usuario pueda marcar días con colores (como para turnos o
 * algo así).
 * Guardamos todo en un archivo JSON para que no se pierda al cerrar la app.
 */
public class CalendarioController {

    @FXML
    private Label monthYearLabel; // Aquí ponemos el nombre del mes y el año

    @FXML
    private GridPane calendarGrid; // La cuadrícula donde pintamos los días

    private LocalDate selectedDate; // El mes que estamos viendo ahora mismo

    // Mapa para guardar los datos: ID del usuario -> (Fecha -> Color)
    private Map<Integer, Map<String, String>> calendarData;
    private static final String DATA_FILE = "calendario_data.json"; // El archivo donde se guarda todo
    private Gson gson;

    // Estos son los colores que vamos turnando
    private static final String COLOR_WHITE = "white";
    private static final String COLOR_ORANGE = "orange";
    private static final String COLOR_GREEN = "green";

    /**
     * Al arrancar, ponemos la fecha de hoy, preparamos el GSON y cargamos los
     * datos.
     */
    public void initialize() {
        selectedDate = LocalDate.now();
        gson = new GsonBuilder().setPrettyPrinting().create();
        loadCalendarData();
        drawCalendar(); // Pintamos el calendario por primera vez
    }

    /**
     * Lee el archivo JSON "calendario_data.json" y lo mete en el mapa.
     * Si no existe el archivo, pues de momento lo dejamos vacío.
     */
    private void loadCalendarData() {
        calendarData = new HashMap<>();
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                // Usamos TypeToken porque es un mapa de mapas y GSON necesita saber el tipo exacto
                Type type = new TypeToken<Map<Integer, Map<String, String>>>() {
                }.getType();
                Map<Integer, Map<String, String>> data = gson.fromJson(reader, type);
                if (data != null) {
                    calendarData = data;
                }
            } catch (IOException e) {
                System.err.println("Vaya, ha fallado al cargar los datos del calendario: " + e.getMessage());
            }
        }
    }

    /**
     * Guarda el mapa actual en el archivo JSON para que se quede guardadito.
     */
    private void saveCalendarData() {
        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            gson.toJson(calendarData, writer);
        } catch (IOException e) {
            System.err.println("No se han podido guardar los datos: " + e.getMessage());
        }
    }

    /**
     * Mira qué color tiene un día concreto para el usuario que está logueado.
     * Si no tiene nada, devolvemos blanco por defecto.
     */
    private String getDayColor(LocalDate date) {
        if (MainApplication.currentUser == null)
            return COLOR_WHITE;
        int userId = MainApplication.currentUser.getId();
        if (calendarData.containsKey(userId)) {
            Map<String, String> userDates = calendarData.get(userId);
            String dateStr = date.toString();
            if (userDates.containsKey(dateStr)) {
                return userDates.get(dateStr);
            }
        }
        return COLOR_WHITE;
    }

    /**
     * Al hacer clic en un día, cambiamos su color siguiendo el orden:
     * Blanco -> Naranja -> Verde -> Blanco de nuevo.
     */
    private void cycleDayColor(LocalDate date, VBox dayCell) {
        if (MainApplication.currentUser == null)
            return;
        int userId = MainApplication.currentUser.getId();

        String currentColor = getDayColor(date);
        String newColor;

        // Aquí hacemos el cambio de color
        switch (currentColor) {
            case COLOR_WHITE:
                newColor = COLOR_ORANGE;
                break;
            case COLOR_ORANGE:
                newColor = COLOR_GREEN;
                break;
            case COLOR_GREEN:
            default:
                newColor = COLOR_WHITE;
                break;
        }

        // Le aplicamos el estilo CSS a la celda
        applyColorClass(dayCell, newColor);

        // Actualizamos nuestros datos y guardamos en el JSON
        calendarData.putIfAbsent(userId, new HashMap<>());
        Map<String, String> userDates = calendarData.get(userId);

        if (newColor.equals(COLOR_WHITE)) {
            userDates.remove(date.toString()); // Si es blanco, lo quitamos para que el archivo pese menos
        } else {
            userDates.put(date.toString(), newColor);
        }

        saveCalendarData();
    }

    /**
     * Quita las clases de colores antiguas y pone la nueva al VBox del día.
     */
    private void applyColorClass(VBox cell, String color) {
        cell.getStyleClass().removeAll("calendar-day-white", "calendar-day-orange", "calendar-day-green");
        switch (color) {
            case COLOR_ORANGE:
                cell.getStyleClass().add("calendar-day-orange");
                break;
            case COLOR_GREEN:
                cell.getStyleClass().add("calendar-day-green");
                break;
            default:
                cell.getStyleClass().add("calendar-day-white");
                break;
        }
    }

    /**
     * Esta es la función tocha que dibuja todo el calendario en el GridPane.
     */
    private void drawCalendar() {
        calendarGrid.getChildren().clear(); // Limpiamos lo que hubiera antes

        // Ponemos el nombre del mes en español y en mayúsculas
        String monthName = selectedDate.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES"));
        monthYearLabel.setText(monthName.toUpperCase() + " " + selectedDate.getYear());

        // Ponemos las cabeceras de los días de la semana
        String[] daysOfWeek = { "L", "M", "M", "J", "V", "S", "D" };
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label dayHeader = new Label(daysOfWeek[i]);
            dayHeader.getStyleClass().add("calendar-day-header");
            calendarGrid.add(dayHeader, i, 0);
        }

        // Calculamos dónde empieza el mes y cuántos días tiene
        YearMonth yearMonth = YearMonth.from(selectedDate);
        LocalDate firstOfMonth = yearMonth.atDay(1);
        int startDayOfWeek = firstOfMonth.getDayOfWeek().getValue(); // 1 es Lunes, 7 es Domingo
        int daysInMonth = yearMonth.lengthOfMonth();

        int row = 1;
        int col = 0;

        // Rellenamos los huecos vacíos antes del día 1 del mes
        for (int i = 1; i < startDayOfWeek; i++) {
            VBox emptyCell = createEmptyCell();
            calendarGrid.add(emptyCell, col, row);
            col++;
        }

        LocalDate today = LocalDate.now();

        // Ahora pintamos los días del mes uno por uno
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate currentDate = yearMonth.atDay(day);
            VBox dayCell = new VBox();
            dayCell.getStyleClass().add("calendar-day-cell");

            // Le ponemos el color que tenga guardado
            String color = getDayColor(currentDate);
            applyColorClass(dayCell, color);

            // Si haces clic, que cambie de color
            dayCell.setOnMouseClicked(e -> cycleDayColor(currentDate, dayCell));

            Text dayText = new Text(String.valueOf(day));
            dayText.getStyleClass().add("calendar-day-text");
            dayCell.getChildren().add(dayText);

            // Si es el día de hoy, le ponemos un estilo especial
            if (today.equals(currentDate)) {
                dayCell.getStyleClass().add("calendar-day-today");
            }

            calendarGrid.add(dayCell, col, row);

            col++;
            if (col > 6) { // Si llegamos al final de la fila (Domingo), pasamos a la siguiente
                col = 0;
                row++;
            }
        }

        // Rellenamos con celdas vacías hasta completar 6 filas, para que no se deforme
        int totalCellsDrawn = (row - 1) * 7 + col;
        while (totalCellsDrawn < 42) {
            VBox emptyCell = createEmptyCell();
            calendarGrid.add(emptyCell, col, row);
            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
            totalCellsDrawn++;
        }
    }

    /**
     * Crea una celda vacía para rellenar el calendario.
     */
    private VBox createEmptyCell() {
        VBox emptyCell = new VBox();
        emptyCell.getStyleClass().add("calendar-day-cell");
        return emptyCell;
    }

    /**
     * Botón para ir al mes anterior.
     */
    @FXML
    private void onPreviousMonth() {
        selectedDate = selectedDate.minusMonths(1);
        drawCalendar();
    }

    /**
     * Botón para ir al mes siguiente.
     */
    @FXML
    private void onNextMonth() {
        selectedDate = selectedDate.plusMonths(1);
        drawCalendar();
    }
}
