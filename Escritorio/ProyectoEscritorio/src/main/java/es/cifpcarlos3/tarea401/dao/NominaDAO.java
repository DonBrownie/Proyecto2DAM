package es.cifpcarlos3.tarea401.dao;

import es.cifpcarlos3.tarea401.model.Nomina;
import es.cifpcarlos3.tarea401.utils.ApiClient;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase encarga de gestionar los datos de las Nóminas (Data Access Object).
 * Se comunica con la API para obtener la información.
 */
public class NominaDAO {

    /**
     * Obtiene una lista de todas las nóminas llamando a la API.
     * 
     * @return Una lista de objetos Nomina, o una lista vacía si hay un error.
     */
    public List<Nomina> getAllNominas() {
        try {
            // Hacemos una petición GET a la ruta "/nominas" y guardamos el resultado en un
            // array
            Nomina[] nominasArray = ApiClient.get("/nominas", Nomina[].class);
            // Convertimos el array a una Lista (ArrayList) y la devolvemos
            return new ArrayList<>(Arrays.asList(nominasArray));
        } catch (Exception e) {
            // Si algo falla, imprimimos el error por consola y devolvemos una lista vacía
            System.err.println("Error al cargar nominas desde API: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
