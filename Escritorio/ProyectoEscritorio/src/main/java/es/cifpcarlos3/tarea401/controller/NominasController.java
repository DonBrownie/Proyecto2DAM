package es.cifpcarlos3.tarea401.controller;

import es.cifpcarlos3.tarea401.dao.NominaDAO;
import es.cifpcarlos3.tarea401.model.Nomina;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class NominasController {

    @FXML
    private VBox itemsContainer;

    public void initialize() {
        loadNominas();
    }

    private void loadNominas() {
        itemsContainer.getChildren().clear();
        NominaDAO nominaDAO = new NominaDAO();
        List<Nomina> nominas = nominaDAO.getAllNominas();

        // Add header
        Label headerLabel = new Label("NOMINAS");
        headerLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1a4f8b; -fx-padding: 10;");

        for (Nomina nomina : nominas) {
            HBox itemBox = createNominaItem(nomina);
            itemsContainer.getChildren().add(itemBox);
        }
    }

    private HBox createNominaItem(Nomina nomina) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(15));
        hbox.setSpacing(10);
        hbox.getStyleClass().add("item-card"); // Use CSS class instead of inline styles for consistency
        hbox.setMaxWidth(500); // Fixed-ish width for centering
        VBox.setMargin(hbox, new Insets(5, 0, 5, 0)); // No horizontal margin needed since it's centered in VBox

        // Icon placeholder
        Label icon = new Label("💰"); // Wallet/Money bag character
        icon.setStyle("-fx-font-size: 30px; -fx-text-fill: #888;");

        VBox infoBox = new VBox();
        infoBox.setAlignment(Pos.CENTER_LEFT);
        Label titleLabel = new Label("Nomina ID: " + nomina.getIdNomina());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        Label subLabel = new Label("Total: " + nomina.getPago() + " €");
        subLabel.setStyle("-fx-text-fill: #888; -fx-font-size: 12px;");
        infoBox.getChildren().addAll(titleLabel, subLabel);

        // Arrow
        Label arrow = new Label(">");
        arrow.setStyle("-fx-font-size: 20px; -fx-text-fill: #ccc;");
        HBox.setHgrow(infoBox, Priority.ALWAYS);

        hbox.getChildren().addAll(icon, infoBox, arrow);

        // Click event to show details
        hbox.setOnMouseClicked(e -> showNominaDetails(nomina));

        return hbox;
    }

    private void showNominaDetails(Nomina nomina) {
        itemsContainer.getChildren().clear();

        VBox detailBox = new VBox();
        detailBox.setPadding(new Insets(20));
        detailBox.setSpacing(15);
        detailBox.setStyle("-fx-background-color: #2c3e50; -fx-background-radius: 10;");
        detailBox.setMaxWidth(500);
        detailBox.setMaxHeight(300);

        // Header Title
        Label title = new Label("Detalles de nómina");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        // Info
        Label idLabel = new Label("ID Nómina: " + nomina.getIdNomina());
        idLabel.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 14px;");

        Label userLabel = new Label("ID Empleado: " + nomina.getIdUsu());
        userLabel.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 14px;");

        // Section: Pago
        Label infoHeader = new Label("Información de pago");
        infoHeader.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10 0 5 0;");

        Label payLabel = new Label("Importe neto: " + nomina.getPago() + " €");
        payLabel.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 16px; -fx-font-weight: bold;");

        Label dateLabel = new Label("Fecha de emisión: " + nomina.getFecha());
        dateLabel.setStyle("-fx-text-fill: #bdc3c7;");

        // Button to go back
        Label backButton = new Label("⬅ Volver");
        backButton.setStyle(
                "-fx-text-fill: #3498db; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 14px; -fx-padding: 10 0 0 0;");
        backButton.setOnMouseClicked(e -> loadNominas());

        detailBox.getChildren().addAll(
                title,
                idLabel, userLabel,
                infoHeader,
                payLabel, dateLabel,
                backButton);

        itemsContainer.getChildren().add(detailBox);
    }
}
