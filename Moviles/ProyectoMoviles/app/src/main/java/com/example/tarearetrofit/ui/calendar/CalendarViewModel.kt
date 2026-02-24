package com.example.tarearetrofit.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarearetrofit.adapter.DayState
import com.example.tarearetrofit.model.CalendarEvent
import com.example.tarearetrofit.repository.CalendarRepository
import com.example.tarearetrofit.repository.FirebaseProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Estados para el flujo de datos del Calendario.
 */
sealed class CalendarUiState {
    object Loading : CalendarUiState()
    data class Success(val events: Map<String, DayState>) : CalendarUiState() // Mapa de Fecha -> Estado
    data class Error(val message: String) : CalendarUiState()
}

/**
 * ViewModel que gestiona la lógica de turnos y estados en el calendario.
 */
class CalendarViewModel(private val repository: CalendarRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<CalendarUiState>(CalendarUiState.Loading)
    val uiState: StateFlow<CalendarUiState> = _uiState

    /**
     * Carga los eventos del calendario para el usuario actual.
     */
    fun loadEvents() {
        val uid = FirebaseProvider.auth.currentUser?.uid ?: return
        viewModelScope.launch {
            _uiState.value = CalendarUiState.Loading
            val eventsList = repository.getEventsForUser(uid)
            // Convierte la lista de eventos en un mapa para acceso rápido por fecha
            val eventsMap = eventsList.associate { it.date to DayState.valueOf(it.state) }
            _uiState.value = CalendarUiState.Success(eventsMap)
        }
    }

    /**
     * Actualiza o crea un nuevo estado para una fecha específica.
     */
    fun updateDayState(date: String, newState: DayState) {
        val uid = FirebaseProvider.auth.currentUser?.uid ?: return
        viewModelScope.launch {
            val event = CalendarEvent(
                id = "${uid}_$date",
                uid = uid,
                date = date,
                state = newState.name
            )
            repository.saveEvent(event)
        }
    }
}
