package es.cifpcarlos3.tarea401.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

/**
 * Esta clase es como nuestro "mensajero" personal para hablar con el servidor
 * (la API).
 * Nos ahorra tener que escribir todo el jaleo de las conexiones HTTP cada vez
 * que queremos datos.
 */
public class ApiClient {
    // Aquí es donde vive nuestra API. De momento en nuestro ordenador.
    private static final String BASE_URL = "http://localhost:8080/api";

    // Este objeto es el que se encarga de mandar de verdad las peticiones por la
    // red.
    private static final HttpClient client = HttpClient.newHttpClient();

    // GSON es la librería que usamos para traducir de JSON (el formato que usa la
    // web) a objetos Java.
    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss") // Le decimos cómo vienen las fechas para que no se líe.
            .create();

    /**
     * El método GET sirve para "pedir" cosas.
     * Le pasas la ruta (el endpoint) y qué tipo de objeto esperas recibir.
     */
    public static <T> T get(String endpoint, Class<T> responseType) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Si el servidor nos dice que todo OK (está entre el 200 y el 299), devolvemos
        // los datos.
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return gson.fromJson(response.body(), responseType);
        } else {
            // Si nos da un error, lanzamos una excepción para que nos enteremos.
            throw new RuntimeException("Vaya, la API ha fallado: " + response.statusCode() + " - " + response.body());
        }
    }

    /**
     * El método POST es para "enviar" o "crear" cosas nuevas.
     * Muy útil para registrar usuarios o hacer el Login.
     */
    public static <T> T post(String endpoint, Object body, Class<T> responseType) throws Exception {
        // Convertimos el objeto Java a texto JSON para mandarlo.
        String jsonBody = gson.toJson(body);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json") // Avisamos de que lo que mandamos es un JSON.
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return gson.fromJson(response.body(), responseType);
        } else {
            throw new RuntimeException("Error en el POST: " + response.statusCode() + " - " + response.body());
        }
    }

    /**
     * El método PUT sirve para "actualizar" o "cambiar" algo que ya existe en la
     * base de datos.
     */
    public static <T> T put(String endpoint, Object body, Class<T> responseType) throws Exception {
        String jsonBody = gson.toJson(body);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return gson.fromJson(response.body(), responseType);
        } else {
            throw new RuntimeException("Error al actualizar (PUT): " + response.statusCode() + " - " + response.body());
        }
    }

    /**
     * Este es un PUT especial que no devuelve nada (void).
     * Lo usamos cuando solo nos importa que el servidor diga "OK" y no necesitamos
     * datos de vuelta.
     */
    public static void putVoid(String endpoint, Object body) throws Exception {
        String jsonBody = gson.toJson(body);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Comprobamos si ha habido algún fallo.
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new RuntimeException("Fallo en la actualización: " + response.statusCode() + " - " + response.body());
        }
    }
}
