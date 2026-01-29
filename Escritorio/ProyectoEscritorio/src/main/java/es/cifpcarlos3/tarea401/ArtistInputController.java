package es.cifpcarlos3.tarea401;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class ArtistInputController {

    @FXML
    private TextField txtArtista;

    private String urlDB = "jdbc:sqlite:db/chinook.db";

    @FXML
    public void generarInforme() {
        String nombreArtista = txtArtista.getText();

        if (nombreArtista == null || nombreArtista.trim().isEmpty()) {
            System.out.println("Por favor, introduce el nombre de un artista.");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection(urlDB);

            String jasperFilePath = "/es/cifpcarlos3/tarea401/informes/InformeArtistas.jrxml";

            InputStream is = MainApplication.class.getResourceAsStream(jasperFilePath);
            JasperReport report = JasperCompileManager.compileReport(is);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("P_ARTIST_NAME", nombreArtista.trim());

            JasperPrint print = JasperFillManager.fillReport(report, parametros, conn);
            JasperViewer.viewReport(print, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
