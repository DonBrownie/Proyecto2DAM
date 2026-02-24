package com.example.tarearetrofit.repository

import com.example.tarearetrofit.model.Habitacion
import com.example.tarearetrofit.model.Nomina
import com.example.tarearetrofit.model.Reserva
import com.example.tarearetrofit.model.User
import com.google.firebase.Timestamp
import kotlinx.coroutines.tasks.await
import java.util.Calendar

/**
 * Objeto encargado de inicializar la base de datos de Firestore con datos de prueba iniciales.
 */
object DataInitializer {

    /**
     * Carga los datos de prueba (semillas) en Firestore.
     * Esta función está diseñada para ejecutarse una vez para pre-poblar la base de datos.
     */
    suspend fun seedData() {
        val db = FirebaseProvider.db

        // 1. Usuarios de prueba
        val users = listOf(
            User("usu_1", "Juan", "Perez", "Garcia", "Recepcionista", "12345678A", "600111222", "jperez@hotel.com"),
            User("usu_2", "Ana", "Gomez", "Lopez", "Limpieza", "87654321B", "600333444", "agomez@hotel.com"),
            User("usu_3", "Luis", "Martinez", "Santos", "Mantenimiento", "11223344C", "600555666", "lmartinez@hotel.com"),
            User("usu_4", "Maria", "Lopez", "Diaz", "Gerente", "44332211D", "600777888", "mlopez@hotel.com"),
            User("usu_5", "Carlos", "Sanchez", "Ruiz", "Cocinero", "55667788E", "600999000", "csanchez@hotel.com")
        )
        for (user in users) {
             db.collection("users").document(user.uid).set(user).await()
        }

        // 2. Habitaciones de prueba
        val rooms = listOf(
            Habitacion(101, true),
            Habitacion(102, false),
            Habitacion(103, true),
            Habitacion(201, true),
            Habitacion(202, false)
        )
        for (room in rooms) {
            db.collection("habitaciones").document(room.id_habitacion.toString()).set(room).await()
        }

        // 3. Nóminas de prueba
        val payrolls = listOf(
            Nomina("nom_1", "usu_1", 1200.50),
            Nomina("nom_2", "usu_2", 1100.00),
            Nomina("nom_3", "usu_3", 1300.75),
            Nomina("nom_4", "usu_4", 2500.00),
            Nomina("nom_5", "usu_5", 1400.25)
        )
        for (payroll in payrolls) {
            db.collection("nominas").document(payroll.id_nomina).set(payroll).await()
        }

        // 4. Reservas de prueba
        val reservations = listOf(
            Reserva("res_1", "usu_1", "99887766Z", 101, "2023-10-01", "2023-10-05"),
            Reserva("res_2", "usu_1", "11223344Y", 102, "2023-11-10", "2023-11-15"),
            Reserva("res_3", "usu_2", "55667788X", 103, "2023-12-01", "2023-12-05"),
            Reserva("res_4", "usu_4", "22334455W", 201, "2024-01-10", "2024-01-15"),
            Reserva("res_5", "usu_4", "66778899V", 202, "2024-02-01", "2024-02-05")
        )
        for (res in reservations) {
            db.collection("reservas").document(res.id_reserva).set(res).await()
        }
    }
}
