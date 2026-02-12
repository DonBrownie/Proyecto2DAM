package es.cifpcarlos3.tarea401.dao;

import es.cifpcarlos3.tarea401.model.Nomina;
import es.cifpcarlos3.tarea401.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NominaDAO {

    public List<Nomina> getAllNominas() {
        List<Nomina> nominas = new ArrayList<>();
        String sql = "SELECT * FROM Nomina";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                nominas.add(new Nomina(
                        rs.getInt("id_nomina"),
                        rs.getInt("id_usu"),
                        rs.getFloat("pago"),
                        rs.getDate("fecha")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nominas;
    }
}
