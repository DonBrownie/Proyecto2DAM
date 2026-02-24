package com.example.tarearetrofit.ui.reservas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tarearetrofit.repository.ReservasRepository

class ReservasViewModelFactory(private val repository: ReservasRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReservasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReservasViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
