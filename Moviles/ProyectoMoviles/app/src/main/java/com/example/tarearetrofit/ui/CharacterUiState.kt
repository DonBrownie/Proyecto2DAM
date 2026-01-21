package com.example.tarearetrofit.ui

import com.example.tarearetrofit.recyclerview.Character
import com.example.tarearetrofit.recyclerview.Film

sealed class CharacterUiState
{
    object Idle : CharacterUiState()
    object Loading : CharacterUiState()
    data class Success(val character : Character) : CharacterUiState()
    data class Error(val message: String) : CharacterUiState()
    data class SuccessList(val character : List<Character>) : CharacterUiState()
    data class SuccessFilms(val films: List<Film>) : CharacterUiState()
}