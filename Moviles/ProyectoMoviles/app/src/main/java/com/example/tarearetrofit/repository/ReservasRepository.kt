package com.example.tarearetrofit.repository

import com.example.tarearetrofit.model.Reserva
import kotlinx.coroutines.tasks.await

/**
 * Repositorio para la gestión de reservas de habitaciones.
 */
class ReservasRepository {
    private val collection = FirebaseProvider.db.collection("reservas")

    /**
     * Obtiene todas las reservas registradas en el sistema.
     * @return Lista de objetos [Reserva].
     */
    suspend fun getReservas(): List<Reserva> {
        return try {
            val snapshot = collection.get().await()
            snapshot.toObjects(Reserva::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Añade una nueva reserva a la colección de Firestore.
     * @param reserva Objeto con los datos de la reserva.
     * @return True si se añadió con éxito, false en caso contrario.
     */
    suspend fun addReserva(reserva: Reserva): Boolean {
        return try {
            val docRef = collection.document() // Genera una referencia con ID
            val reservaConId = reserva.copy(id_reserva = docRef.id) // Copia el objeto con el ID
            docRef.set(reservaConId).await() // Guarda el objeto completo
            true
        } catch (e: Exception) {
            false
        }
    }
}
