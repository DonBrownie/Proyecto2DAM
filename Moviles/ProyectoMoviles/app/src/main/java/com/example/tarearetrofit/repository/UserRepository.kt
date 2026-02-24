package com.example.tarearetrofit.repository

import com.example.tarearetrofit.model.User
import kotlinx.coroutines.tasks.await

/**
 * Repositorio encargado de gestionar los datos de los usuarios (empleados) en Firestore.
 */
class UserRepository {
    private val collection = FirebaseProvider.db.collection("users")

    /**
     * Obtiene los datos del perfil del usuario actualmente autenticado.
     * @return El objeto [User] si tiene éxito, o null si falla o no hay sesión.
     */
    suspend fun getUserData(): User? {
        val uid = FirebaseProvider.auth.currentUser?.uid ?: return null
        return try {
            val snapshot = collection.document(uid).get().await()
            snapshot.toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Actualiza la información del perfil del usuario actual.
     * @param user El objeto [User] con los nuevos datos.
     * @return True si se guardó correctamente, false en caso contrario.
     */
    suspend fun updateUserData(user: User): Boolean {
        val uid = FirebaseProvider.auth.currentUser?.uid ?: return false
        return try {
            collection.document(uid).set(user).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
