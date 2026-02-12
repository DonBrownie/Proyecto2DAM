package es.cifpcarlos3.tarea401;

import es.cifpcarlos3.tarea401.dao.ReservaDAO;
import es.cifpcarlos3.tarea401.model.Reserva;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class ReservasController {

    @FXML
    private VBox itemsContainer;

    public void initialize() {
        loadReservas();
    }

    private void loadReservas() {
        itemsContainer.getChildren().clear();
        ReservaDAO reservaDAO = new ReservaDAO();
        List<Reserva> reservas = reservaDAO.getAllReservas();

        // Add header
        Label headerLabel = new Label("RESERVAS");
        headerLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1a4f8b; -fx-padding: 10;");
        // Center header in a container if needed, but VBox aligns left by default
        // unless configured.
        // For now, simple adding.

        if (reservas.isEmpty()) {
            Label emptyLabel = new Label("No hay reservas registradas.");
            emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #888; -fx-padding: 20;");
            itemsContainer.getChildren().add(emptyLabel);
        } else {
            for (Reserva reserva : reservas) {
                HBox itemBox = createReservaItem(reserva);
                itemsContainer.getChildren().add(itemBox);
            }
        }
    }

    private HBox createReservaItem(Reserva reserva) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(15));
        hbox.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        hbox.setPrefHeight(80);
        hbox.setSpacing(10);
        VBox.setMargin(hbox, new Insets(5, 20, 5, 20));

        // Icon placeholder (using a label or simple shape if no image)
        Label icon = new Label("🛏"); // Bed character
        icon.setStyle("-fx-font-size: 30px; -fx-text-fill: #888;");

        VBox infoBox = new VBox();
        infoBox.setAlignment(Pos.CENTER_LEFT);
        Label titleLabel = new Label("Reserva de " + reserva.getNombreCliente());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        Label subLabel = new Label(reserva.getIdReserva() + " - " + reserva.getNumeroHabitacion());
        subLabel.setStyle("-fx-text-fill: #888; -fx-font-size: 12px;");
        infoBox.getChildren().addAll(titleLabel, subLabel);

        // Arrow
        Label arrow = new Label(">");
        arrow.setStyle("-fx-font-size: 20px; -fx-text-fill: #ccc;");
        HBox.setHgrow(infoBox, Priority.ALWAYS);

        hbox.getChildren().addAll(icon, infoBox, arrow);

        // Click event to show details
        hbox.setOnMouseClicked(e -> showReservaDetails(reserva));

        return hbox;
    }

    private void showReservaDetails(Reserva reserva) {
        itemsContainer.getChildren().clear();

        VBox detailBox = new VBox();
        detailBox.setPadding(new Insets(20));
        detailBox.setSpacing(15);
        detailBox.setStyle("-fx-background-color: #2c3e50; -fx-background-radius: 10;"); // Dark background like
                                                                                         // screenshot

        // Header Title
        Label title = new Label("Detalles de reserva");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        // ID & Name
        Label idLabel = new Label("ID reserva: " + reserva.getIdReserva());
        idLabel.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 14px;");

        Label nameLabel = new Label("Cliente: " + reserva.getNombreCliente());
        nameLabel.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 14px;");

        Label dniLabel = new Label("DNI: " + reserva.getDniCliente());
        dniLabel.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 14px;");

        // Section: Información general
        Label infoHeader = new Label("Información general");
        infoHeader.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10 0 5 0;");

        Label dateLabel = new Label("Llegada: " + reserva.getFechaInicio() + " - Salida: " + reserva.getFechaFin());
        dateLabel.setStyle("-fx-text-fill: #bdc3c7;");

        Label roomLabel = new Label("Nº habitación: " + reserva.getNumeroHabitacion());
        roomLabel.setStyle("-fx-text-fill: #bdc3c7;");

        // Button to go back
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
