package es.cifpcarlos3.tarea401.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import es.cifpcarlos3.tarea401.MainApplication;
import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import java.util.List;
import es.cifpcarlos3.tarea401.dao.ReservaDAO;
import es.cifpcarlos3.tarea401.model.Reserva;
import javafx.fxml.FXML;

/**
 * Este controlador es el que maneja la lista de reservas.
 * Se encarga de leerlas de la base de datos y pintarlas en la pantalla
 * principal.
 */
public class ReservasController {

    @FXML
    private VBox itemsContainer; // Aquí es donde metemos cada fila de reserva

    /**
     * Este es el botón para añadir una reserva nueva.
     * Abre una ventana emergente (modal) con el formulario de
     * nueva-reserva-view.fxml.
     */
    @FXML
    private void onAddButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("nueva-reserva-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(MainApplication.class.getResource("styles.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Añadir Nueva Reserva");
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL); // Esto hace que no puedas tocar lo de atrás
                                                                         // hasta cerrar esto
            stage.setScene(scene);

            // Importante: Cuando se cierre la ventana, recargamos la lista por si han
            // añadido algo
            stage.setOnHidden(e -> loadReservas());

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Al arrancar, cargamos la lista de reservas directamente.
     */
    public void initialize() {
        loadReservas();
    }

    /**
     * Esta función borra lo que haya en el contenedor y vuelve a pedir las reservas
     * al DAO.
     * Luego las recorre y crea un "item" por cada una.
     */
    private void loadReservas() {
        itemsContainer.getChildren().clear();
        ReservaDAO reservaDAO = new ReservaDAO();
        List<Reserva> reservas = reservaDAO.getAllReservas();

        // Título de la sección
        Label headerLabel = new Label("RESERVAS");
        headerLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1a4f8b; -fx-padding: 10;");

        // Si no hay ninguna reserva en la DB, avisamos al usuario
        if (reservas.isEmpty()) {
            Label emptyLabel = new Label("No hay reservas registradas.");
            emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #888; -fx-padding: 20;");
            itemsContainer.getChildren().add(emptyLabel);
        } else {
            // Vamos creando cada tarjetita de reserva y la metemos al contenedor
            for (Reserva reserva : reservas) {
                HBox itemBox = createReservaItem(reserva);
                itemsContainer.getChildren().add(itemBox);
            }
        }
    }

    /**
     * Aquí fabricamos el "dibujo" de cada reserva en la lista.
     * Metemos un icono, el nombre del cliente y el ID.
     */
    private HBox createReservaItem(Reserva reserva) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(15));
        hbox.setSpacing(10);
        hbox.getStyleClass().add("item-card"); // Usamos el estilo que definimos en CSS
        hbox.setMaxWidth(500);
        VBox.setMargin(hbox, new Insets(5, 0, 5, 0));

        // Un icono de cama para que quede más visual
        Label icon = new Label("🛏");
        icon.setStyle("-fx-font-size: 30px; -fx-text-fill: #888;");

        VBox infoBox = new VBox();
        infoBox.setAlignment(Pos.CENTER_LEFT);
        Label titleLabel = new Label("Reserva de " + reserva.getNombreCliente());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        Label subLabel = new Label(reserva.getIdReserva() + " - Habitación " + reserva.getNumeroHabitacion());
        subLabel.setStyle("-fx-text-fill: #888; -fx-font-size: 12px;");
        infoBox.getChildren().addAll(titleLabel, subLabel);

        // Una flechita al final de la fila
        Label arrow = new Label(">");
        arrow.setStyle("-fx-font-size: 20px; -fx-text-fill: #ccc;");
        HBox.setHgrow(infoBox, Priority.ALWAYS);

        hbox.getChildren().addAll(icon, infoBox, arrow);

        // Si pinchan en la fila, enseñamos los detalles a pantalla completa
        hbox.setOnMouseClicked(e -> showReservaDetails(reserva));

        return hbox;
    }

    /**
     * Muestra la info detallada de una reserva tapando la lista.
     */
    private void showReservaDetails(Reserva reserva) {
        itemsContainer.getChildren().clear();

        VBox detailBox = new VBox();
        detailBox.setPadding(new Insets(20));
        detailBox.setSpacing(15);
        detailBox.setStyle("-fx-background-color: #2c3e50; -fx-background-radius: 10;");
        detailBox.setMaxWidth(500);
        detailBox.setMaxHeight(300);

        // Título de los detalles
        Label title = new Label("Detalles de reserva");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        // Datos básicos del cliente
        Label idLabel = new Label("ID reserva: " + reserva.getIdReserva());
        idLabel.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 14px;");

        Label nameLabel = new Label("Cliente: " + reserva.getNombreCliente());
        nameLabel.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 14px;");

        Label dniLabel = new Label("DNI: " + reserva.getDniCliente());
        dniLabel.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 14px;");

        // Sección de fechas y habitación
        Label infoHeader = new Label("Información general");
        infoHeader.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10 0 5 0;");

        Label dateLabel = new Label("Llegada: " + reserva.getFechaInicio() + " - Salida: " + reserva.getFechaFin());
        dateLabel.setStyle("-fx-text-fill: #bdc3c7;");

        Label roomLabel = new Label("Nº habitación: " + reserva.getNumeroHabitacion());
        roomLabel.setStyle("-fx-text-fill: #bdc3c7;");

        // Botón para volver atrás a la lista completa
        Label backButton = new Label("⬅ Volver");
        backButton.setStyle(
                "-fx-text-fill: #3498db; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 14px; -fx-padding: 10 0 0 0;");
        backButton.setOnMouseClicked(e -> loadReservas());

        detailBox.getChildren().addAll(
                title,
                idLabel, nameLabel, dniLabel,
                infoHeader,
                dateLabel, roomLabel,
                backButton);

        itemsContainer.getChildren().add(detailBox);
    }
}
