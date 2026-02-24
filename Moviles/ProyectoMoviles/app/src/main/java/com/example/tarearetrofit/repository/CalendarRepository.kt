package com.example.tarearetrofit.repository

import com.example.tarearetrofit.model.CalendarEvent
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Repositorio para la gestión de eventos y estados en el calendario de turnos.
 */
class CalendarRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("calendar_events")

    /**
     * Obtiene los eventos asociados a un usuario específico.
     * @param uid Identificador único del usuario.
     * @return Lista de eventos del calendario.
     */
    suspend fun getEventsForUser(uid: String): List<CalendarEvent> {
        return try {
            val snapshot = collection
                .whereEqualTo("uid", uid)
                .get()
                .await()
            snapshot.toObjects(CalendarEvent::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Guarda o actualiza un evento en el calendario.
     * Utiliza un ID compuesto por UID y Fecha para evitar duplicados en el mismo día para el mismo usuario.
     */
    suspend fun saveEvent(event: CalendarEvent): Boolean {
        return try {
            val docId = "${event.uid}_${event.date}"
            collection.document(docId).set(event).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
