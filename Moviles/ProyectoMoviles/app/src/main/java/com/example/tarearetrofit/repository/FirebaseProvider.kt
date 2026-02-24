package com.example.tarearetrofit.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Proveedor centralizado de instancias de Firebase.
 * Utiliza el patrón Singleton para asegurar una única instancia de Auth y Firestore.
 */
object FirebaseProvider {
    // Instancia de autenticación de Firebase (inicializada solo cuando se necesita)
    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    
    // Instancia de base de datos Firestore (inicializada solo cuando se necesita)
    val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
}
