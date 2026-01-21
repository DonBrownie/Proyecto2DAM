package com.example.tarearetrofit.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarearetrofit.R
import com.example.tarearetrofit.databinding.FragmentReservasBinding
import com.example.tarearetrofit.recyclerview.FilmAdapter
import com.example.tarearetrofit.repository.CharacterProvider
import com.example.tarearetrofit.retrofit.RetrofitInstance
import com.example.tarearetrofit.ui.CharacterUiState
import com.example.tarearetrofit.ui.CharacterViewModel
import com.example.tarearetrofit.ui.CharacterViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ReservasFragment : Fragment(R.layout.fragment_reservas) {

    private lateinit var binding: FragmentReservasBinding
    private val viewModel: CharacterViewModel by lazy {
        val provider = CharacterProvider(RetrofitInstance.api)
        val factory = CharacterViewModelFactory(provider)
        ViewModelProvider(this, factory)[CharacterViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReservasBinding.bind(view)

        setupRecyclerView()
        observeViewModel()
        viewModel.loadFilms()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewReservas.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is CharacterUiState.Loading -> {
                            // Show loading (optional/if progressBar exists)
                        }
                        is CharacterUiState.SuccessFilms -> {
                            val adapter = FilmAdapter(state.films, "")
                            binding.recyclerViewReservas.adapter = adapter
                        }
                        is CharacterUiState.Error -> {
                            Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}
