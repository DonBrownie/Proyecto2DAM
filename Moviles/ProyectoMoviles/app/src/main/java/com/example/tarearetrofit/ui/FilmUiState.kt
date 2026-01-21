package com.example.tarearetrofit.ui

import com.example.tarearetrofit.recyclerview.Film
sealed class FilmUiState {
    object Loading : FilmUiState()
    data class Success(val films: List<Film>) : FilmUiState()
    data class Error(val message: String) : FilmUiState()
}