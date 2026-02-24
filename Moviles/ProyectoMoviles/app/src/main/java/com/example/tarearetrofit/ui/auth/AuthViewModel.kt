package com.example.tarearetrofit.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarearetrofit.repository.FirebaseProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * Representa los diferentes estados de la interfaz de autenticación.
 */
sealed class AuthUiState {
    object Idle : AuthUiState() // Estado inicial de espera
    object Loading : AuthUiState() // Mostrando indicador de carga
    object Success : AuthUiState() // Login realizado con éxito
    data class Error(val message: String) : AuthUiState() // Error durante el proceso
}

/**
 * ViewModel encargado de la lógica de inicio y cierre de sesión.
 */
class AuthViewModel : ViewModel() {

    // Estado interno mutable
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    
    // Estado expuesto a la vista de forma inmutable
    val uiState: StateFlow<AuthUiState> = _uiState

    /**
     * Realiza el login utilizando el proveedor de Firebase.
     */
    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                // Espera asíncrona del resultado de Firebase
                FirebaseProvider.auth.signInWithEmailAndPassword(email, pass).await()
                _uiState.value = AuthUiState.Success
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Error en la autenticación")
            }
        }
    }

    /**
     * Finaliza la sesión actual del usuario.
     */
    fun logout() {
        FirebaseProvider.auth.signOut()
        _uiState.value = AuthUiState.Idle
    }
}
