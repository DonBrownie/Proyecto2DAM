package es.cifpcarlos3.tarea401.dao;

import es.cifpcarlos3.tarea401.model.Reserva;
import es.cifpcarlos3.tarea401.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    public List<Reserva> getAllReservas() {
        List<Reserva> reservas = new ArrayList<>();
        // Left Join to ensure we get reservations even if client is missing (though
        // ideally they shouldn't be)
        String sql = "SELECT r.*, c.nombre, c.apellidos FROM Reservas r " +
                "LEFT JOIN Clientes c ON r.dni_cliente = c.dni";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String nombreCompleto = rs.getString("nombre");
                if (nombreCompleto == null) {
                    nombreCompleto = "Desconocido";
                } else {
                    nombreCompleto += " " + rs.getString("apellidos");
                }

                reservas.add(new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getString("dni_cliente"),
                        nombreCompleto,
                        rs.getInt("numero_habitacion"),
                        rs.getString("fecha_inicio"),
                        rs.getString("fecha_fin")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al cargar reservas: " + e.getMessage());
        }
        return reservas;
    }

    public boolean insertReserva(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO Reservas (dni_cliente, numero_habitacion, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, reserva.getDniCliente());
            pstmt.setInt(2, reserva.getNumeroHabitacion());
            pstmt.setString(3, reserva.getFechaInicio());
            pstmt.setString(4, reserva.getFechaFin());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}
