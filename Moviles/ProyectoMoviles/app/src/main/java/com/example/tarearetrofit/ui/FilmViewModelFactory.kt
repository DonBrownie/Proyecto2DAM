package com.example.tarearetrofit.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tarearetrofit.repository.CharacterProvider
class FilmViewModelFactory(private val provider: CharacterProvider) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilmViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FilmViewModel(provider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}