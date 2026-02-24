package com.example.tarearetrofit.repository

import com.example.tarearetrofit.model.Cliente
import com.example.tarearetrofit.model.User
import kotlinx.coroutines.tasks.await

/**
 * Repositorio para funciones de administración (reservado para el rol de Gerente).
 */
class GestionRepository {
    private val usersCollection = FirebaseProvider.db.collection("users")
    private val clientesCollection = FirebaseProvider.db.collection("clientes")

    /**
     * Registra un nuevo empleado en la base de datos de Firestore.
     * Nota: No crea la cuenta en Auth, solo guarda el documento con su perfil.
     */
    suspend fun addEmployee(user: User): Boolean {
        return try {
            usersCollection.document(user.uid).set(user).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Registra un nuevo cliente en Firestore generando un ID automático.
     */
    suspend fun addClient(cliente: Cliente): Boolean {
        return try {
            val docRef = clientesCollection.document()
            val finalClient = cliente.copy(id_cliente = docRef.id)
            docRef.set(finalClient).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
