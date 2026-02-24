package com.example.tarearetrofit.ui.gestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tarearetrofit.repository.GestionRepository

class GestionViewModelFactory(private val repository: GestionRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GestionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GestionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
