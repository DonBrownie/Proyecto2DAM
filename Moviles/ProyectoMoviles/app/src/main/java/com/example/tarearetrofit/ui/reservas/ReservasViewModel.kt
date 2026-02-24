package com.example.tarearetrofit.ui.reservas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarearetrofit.model.Reserva
import com.example.tarearetrofit.repository.ReservasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Estados para representar la carga y gestión de reservas.
 */
sealed class ReservasUiState {
    object Idle : ReservasUiState()
    object Loading : ReservasUiState()
    data class Success(val list: List<Reserva>) : ReservasUiState()
    data class Error(val message: String) : ReservasUiState()
}

/**
 * ViewModel que centraliza la lógica de las reservas de habitaciones.
 */
class ReservasViewModel(private val repository: ReservasRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ReservasUiState>(ReservasUiState.Idle)
    val uiState: StateFlow<ReservasUiState> = _uiState

    /**
     * Recupera todas las reservas del sistema.
     */
    fun loadReservas() {
        viewModelScope.launch {
            _uiState.value = ReservasUiState.Loading
            val list = repository.getReservas()
            _uiState.value = ReservasUiState.Success(list)
        }
    }

    /**
     * Valida y añade una nueva reserva.
     */
    fun addReserva(reserva: Reserva) {
        viewModelScope.launch {
            // Validación local: La fecha de fin debe ser posterior a la de inicio
            if (!isDateRangeValid(reserva.fecha_inicio, reserva.fecha_fin)) {
                _uiState.value = ReservasUiState.Error("La fecha de fin no puede ser anterior a la de inicio")
                return@launch
            }

            val success = repository.addReserva(reserva)
            if (success) {
                loadReservas() // Recarga la lista tras añadir una nueva
            } else {
                _uiState.value = ReservasUiState.Error("Error al añadir reserva")
            }
        }
    }

    /**
     * Comprueba que las cadenas de fecha tengan un orden lógico.
     */
    private fun isDateRangeValid(start: String, end: String): Boolean {
        return try {
            val format = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            val startDate = format.parse(start)
            val endDate = format.parse(end)
            if (startDate != null && endDate != null) {
                !endDate.before(startDate)
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}
