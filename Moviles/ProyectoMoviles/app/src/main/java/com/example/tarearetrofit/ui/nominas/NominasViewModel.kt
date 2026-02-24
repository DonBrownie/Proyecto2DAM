package com.example.tarearetrofit.ui.nominas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarearetrofit.model.Nomina
import com.example.tarearetrofit.repository.NominasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Estados posibles para la pantalla de Nóminas.
 */
sealed class NominasUiState {
    object Idle : NominasUiState()
    object Loading : NominasUiState()
    data class Success(val list: List<Nomina>) : NominasUiState()
    data class Error(val message: String) : NominasUiState()
}

/**
 * ViewModel que gestiona la visualización de las nóminas del usuario.
 */
class NominasViewModel(private val repository: NominasRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<NominasUiState>(NominasUiState.Idle)
    val uiState: StateFlow<NominasUiState> = _uiState

    /**
     * Carga las nóminas asociadas al usuario actual desde el repositorio.
     */
    fun loadMyNominas() {
        viewModelScope.launch {
            _uiState.value = NominasUiState.Loading
            val list = repository.getMyNominas()
            _uiState.value = NominasUiState.Success(list)
        }
    }
}
