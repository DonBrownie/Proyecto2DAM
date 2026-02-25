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
    private static final String URL = "jdbc:mysql://localhost:3306/GestionHotel";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("¡Conectados a la base de datos!");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Uy, no encuentro el driver de MySQL.", e);
            }
        }
        return connection;
    }

}
