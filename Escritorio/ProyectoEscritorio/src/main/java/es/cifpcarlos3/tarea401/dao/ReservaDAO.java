package es.cifpcarlos3.tarea401.dao;

import es.cifpcarlos3.tarea401.model.Reserva;
import es.cifpcarlos3.tarea401.utils.ApiClient;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Clase que gestiona la comunicación con la API para la tabla Reservas.
 * Actúa como intermediario (DAO - Data Access Object).
 */
public class ReservaDAO {

    /**
     * Obtiene una lista de todas las reservas registradas.
     * 
     * @return Lista de objetos Reserva. Si hay error, devuelve lista vacía.
     */
    public List<Reserva> getAllReservas() {
        try {
            // Pide las reservas a la API usando el método GET
            Reserva[] reservasArray = ApiClient.get("/reservas", Reserva[].class);
            // Convierte el array en una ArrayList para trabajar más fácil
            return new ArrayList<>(Arrays.asList(reservasArray));
        } catch (Exception e) {
            System.err.println("Error al cargar reservas desde API: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Guarda una nueva reserva enviándola a la API.
     * 
     * @param reserva Objeto con los datos de la reserva a guardar.
     * @return true si se guarda correctamente.
     * @throws SQLException si ocurre un error al insertarla en la API.
     */
    public boolean insertReserva(Reserva reserva) throws SQLException {
        try {
            // Envía los datos de la reserva a la API usando el método POST
            ApiClient.post("/reservas", reserva, Reserva.class);
            return true;
        } catch (Exception e) {
            // Si falla, lanza una excepción (error) para avisar al controlador
            throw new SQLException("Error al insertar reserva vía API: " + e.getMessage());
        }
    }
}
