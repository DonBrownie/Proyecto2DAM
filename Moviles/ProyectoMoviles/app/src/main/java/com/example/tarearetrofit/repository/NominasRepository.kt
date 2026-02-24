package com.example.tarearetrofit.repository

import com.example.tarearetrofit.model.Nomina
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

/**
 * Repositorio vinculado a la gestión de nóminas en Firestore.
 */
class NominasRepository {
    private val collection = FirebaseProvider.db.collection("nominas")

    /**
     * Obtiene la lista de nóminas pertenecientes al usuario actualmente autenticado.
     * @return Lista de objetos [Nomina].
     */
    suspend fun getMyNominas(): List<Nomina> {
        val uid = FirebaseProvider.auth.currentUser?.uid ?: return emptyList()
        return try {
            // Filtra las nóminas donde el campo 'id_usu' coincide con el UID del usuario actual
            val snapshot = collection.whereEqualTo("id_usu", uid).get().await()
            snapshot.toObjects(Nomina::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
