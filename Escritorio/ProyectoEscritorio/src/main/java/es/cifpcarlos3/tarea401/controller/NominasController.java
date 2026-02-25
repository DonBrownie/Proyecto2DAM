package es.cifpcarlos3.tarea401.controller;

import es.cifpcarlos3.tarea401.MainApplication;
import es.cifpcarlos3.tarea401.dao.NominaDAO;
import es.cifpcarlos3.tarea401.model.Nomina;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import es.cifpcarlos3.tarea401.utils.DatabaseConnection;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            if (MainApplication.currentUser != null && nomina.getIdUsu() == MainApplication.currentUser.getId()) {
                HBox itemBox = createNominaItem(nomina);
                itemsContainer.getChildren().add(itemBox);
            }
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
        try {
            // Cargar el archivo JRXML (asegúrate de que esté en
            // src/main/resources/informes/Nomina.jrxml)
            InputStream reportStream = getClass().getResourceAsStream("/informes/Nomina.jrxml");
            if (reportStream == null) {
                System.err.println("No se encontró el archivo Nomina.jrxml en resources/informes/");
                return;
            }

            // Compilar el informe
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Preparar parámetros: el informe espera ID_NOMINA_PARAM
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ID_NOMINA_PARAM", String.valueOf(nomina.getIdNomina()));

            // Llenar el informe utilizando la conexión a la base de datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
                    DatabaseConnection.getConnection());

            // Mostrar el informe en una ventana del visor de Jasper
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception ex) {
            System.err.println("Error al generar el informe Jasper: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
