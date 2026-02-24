package com.example.tarearetrofit.ui.gestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarearetrofit.model.Cliente
import com.example.tarearetrofit.model.User
import com.example.tarearetrofit.repository.GestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Estados de la pantalla de Gestión para el Gerente.
 */
sealed class GestionUiState {
    object Idle : GestionUiState() // Estado de reposo (sin acciones)
    object Loading : GestionUiState() // Procesando alta
    object Success : GestionUiState() // Operación realizada con éxito
    data class Error(val message: String) : GestionUiState()
}

/**
 * ViewModel que maneja el registro de nuevos empleados y clientes.
 * Solo accesible por usuarios con rol "Gerente".
 */
class GestionViewModel(private val repository: GestionRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<GestionUiState>(GestionUiState.Idle)
    val uiState: StateFlow<GestionUiState> = _uiState

    /**
     * Solicita la creación de un nuevo empleado en la base de datos.
     */
    fun addEmployee(user: User) {
        viewModelScope.launch {
            _uiState.value = GestionUiState.Loading
            val success = repository.addEmployee(user)
            if (success) {
                _uiState.value = GestionUiState.Success
            } else {
                _uiState.value = GestionUiState.Error("Error al añadir empleado")
            }
        }
    }

    /**
     * Solicita el registro de un nuevo cliente.
     */
    fun addClient(cliente: Cliente) {
        viewModelScope.launch {
            _uiState.value = GestionUiState.Loading
            val success = repository.addClient(cliente)
            if (success) {
                _uiState.value = GestionUiState.Success
            } else {
                _uiState.value = GestionUiState.Error("Error al añadir cliente")
            }
        }
    }
    
    /**
     * Vuelve el estado a Idle para permitir nuevas acciones y limpiar alertas.
     */
    fun resetState() {
        _uiState.value = GestionUiState.Idle
    }
}
