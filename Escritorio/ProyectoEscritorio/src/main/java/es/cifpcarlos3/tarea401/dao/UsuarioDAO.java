package es.cifpcarlos3.tarea401.dao;

import es.cifpcarlos3.tarea401.model.Usuario;
import es.cifpcarlos3.tarea401.utils.ApiClient;
import java.util.Map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase responsable de las operaciones de base de datos (vía API) para los
 * Usuarios.
 */
public class UsuarioDAO {


    /**
     * Intenta iniciar sesión con un usuario y contraseña.
     * 
     * @param username El nombre de usuario.
     * @param password La contraseña (en texto plano o hash, según lo espere la
     *                 API).
     * @return El objeto Usuario si las credenciales son correctas, o null si
     *         fallan.
     */
    public Usuario login(String username, String password) {
        try {
            // Envía el usuario y contraseña a la ruta de login por el método POST
            return ApiClient.post("/auth/login", Map.of("username", username, "password", password), Usuario.class);
        } catch (Exception e) {
            System.err.println("Login API Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Actualiza la información de un usuario existente.
     * @param usuario El objeto usuario con sus datos nuevos.
     * @return true si se actualizó con éxito, false si hubo error.
     */
    public boolean updateUsuario(Usuario usuario) {
        try {
            // Hace una petición PUT a la API incluyendo la ID del usuario para decirle a
            // quién modificar
            ApiClient.put("/usuarios/" + usuario.getId(), usuario, Usuario.class);
            return true;
        } catch (Exception e) {
            System.err.println("Update Usuario API Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cambia la contraseña de un usuario en concreto.
     * 
     * @param userId       El ID del usuario.
     * @param passwordHash La nueva contraseña (normalmente ya cifrada/hasheada).
     * @return true si se cambió correctamente, false si no.
     */
    public boolean updatePassword(int userId, String passwordHash) {
        try {
            ApiClient.putVoid("/auth/updatePassword/" + userId, Map.of("newPassword", passwordHash));
            return true;
        } catch (Exception e) {
            System.err.println("Update Password API Error: " + e.getMessage());
            return false;
        }
    }
}
