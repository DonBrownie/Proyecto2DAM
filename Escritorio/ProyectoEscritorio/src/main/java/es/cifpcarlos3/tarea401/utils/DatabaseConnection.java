package es.cifpcarlos3.tarea401.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esta clase se encarga de conectar directamente con nuestra base de datos
 * MySQL.
 * La usamos sobre todo para JasperReports, para que pueda sacar los datos de
 * los informes.
 */
public class DatabaseConnection {
    // La dirección de nuestra base de datos en XAMPP.
    private static final String URL = "jdbc:mysql://localhost:3306/GestionHotel";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Por defecto en XAMPP suele estar vacío.

    // Aquí guardamos la conexión para no abrir mil veces la misma.
    private static Connection connection;

    // Ponemos el constructor privado para que nadie intente crear un objeto de esta
    // clase.
    private DatabaseConnection() {
    }

    /**
     * Este método te da la conexión. Si no hay una abierta, te crea una nueva.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Cargamos el driver de MySQL (el conector).
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Intentamos conectar con el usuario y contraseña.
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("¡Conectados a la base de datos!");
            } catch (ClassNotFoundException e) {
                // Si salta esto, es que nos falta el driver en el proyecto.
                throw new SQLException("Uy, no encuentro el driver de MySQL.", e);
            }
        }
        return connection;
    }

    /**
     * Muy importante: cuando terminemos, hay que cerrar la conexión para no dejar
     * procesos abiertos.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
