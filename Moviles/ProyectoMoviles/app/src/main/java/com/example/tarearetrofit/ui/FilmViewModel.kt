package com.example.tarearetrofit.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarearetrofit.recyclerview.Film
import com.example.tarearetrofit.repository.CharacterProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
class FilmViewModel(private val provider: CharacterProvider) : ViewModel() {
    private val _uiState = MutableStateFlow<FilmUiState>(FilmUiState.Loading)
    val uiState: StateFlow<FilmUiState> = _uiState.asStateFlow()
    fun loadFilms(filmUrls: List<String>) {
        viewModelScope.launch {
            _uiState.value = FilmUiState.Loading
            try {
                val films = mutableListOf<Film>()
                for (url in filmUrls) {
                    val film = provider.getFilm(url)
                    films.add(film)
                }
                _uiState.value = FilmUiState.Success(films)
            } catch (e: Exception) {
                _uiState.value = FilmUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}