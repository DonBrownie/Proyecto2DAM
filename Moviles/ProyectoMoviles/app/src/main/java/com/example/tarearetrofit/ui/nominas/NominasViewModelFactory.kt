package com.example.tarearetrofit.ui.nominas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tarearetrofit.repository.NominasRepository

class NominasViewModelFactory(private val repository: NominasRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NominasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NominasViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
