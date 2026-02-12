package es.cifpcarlos3.tarea401.dao;

import es.cifpcarlos3.tarea401.model.Usuario;
import es.cifpcarlos3.tarea401.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario login(String username, String password) {
        String sql = "SELECT u.* FROM Usuarios u " +
                "JOIN Contrasena c ON u.id = c.id_usu " +
                "WHERE c.usuario = ? AND c.contrasena = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellido_1"),
                            rs.getString("apellido_2"),
                            rs.getString("puesto"),
                            rs.getString("dni"),
                            rs.getInt("telefono"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
