package es.cifpcarlos3.tarea401;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MainController {

    private String urlDB = "jdbc:sqlite:db/chinook.db";

    @FXML
    public void generarInformeClientes() {
        try {
            Connection conn = DriverManager.getConnection(urlDB);

            String jasperFilePath = "/es/cifpcarlos3/tarea401/informes/InformeClientes.jrxml";
            // Ruta del informe de clientes
            InputStream is = MainApplication.class.getResourceAsStream(jasperFilePath);
            JasperReport report = JasperCompileManager.compileReport(is);

            Map<String, Object> parametros = new HashMap<>();

            JasperPrint print = JasperFillManager.fillReport(report, parametros, conn);

            // false para que no cierre la app al cerrar el visor
            JasperViewer.viewReport(print, false);

        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void generarInformeArtista() {
        try {
            javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(
                    MainApplication.class.getResource("artist-input-view.fxml"));
            javafx.scene.Scene scene = new javafx.scene.Scene(fxmlLoader.load(), 300, 200);
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setTitle("Buscar Artista");
            stage.setScene(scene);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void cerrarApp() {
        Platform.exit();
    }
}
