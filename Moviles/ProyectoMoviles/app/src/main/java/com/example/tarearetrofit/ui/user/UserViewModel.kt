package com.example.tarearetrofit.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarearetrofit.model.User
import com.example.tarearetrofit.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Estados para el perfil del usuario.
 */
sealed class UserUiState {
    object Idle : UserUiState()
    object Loading : UserUiState()
    data class Success(val user: User) : UserUiState()
    object Error : UserUiState()
}

/**
 * ViewModel que gestiona la información del perfil del usuario (consulta y actualización).
 */
class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Idle)
    val uiState: StateFlow<UserUiState> = _uiState

    /**
     * Carga los datos del empleado logueado desde Firestore.
     */
    fun loadUserData() {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            val user = repository.getUserData()
            if (user != null) {
                _uiState.value = UserUiState.Success(user)
            } else {
                _uiState.value = UserUiState.Error
            }
        }
    }

    /**
     * Guarda los cambios realizados en el perfil del usuario.
     */
    fun updateUserData(user: User) {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            val success = repository.updateUserData(user)
            if (success) {
                _uiState.value = UserUiState.Success(user)
            } else {
                _uiState.value = UserUiState.Error
            }
        }
    }
}
