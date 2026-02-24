package com.example.tarearetrofit.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarearetrofit.R
import com.example.tarearetrofit.databinding.FragmentNominasBinding
import com.example.tarearetrofit.recyclerview.NominasAdapter
import com.example.tarearetrofit.repository.NominasRepository
import com.example.tarearetrofit.ui.nominas.NominasUiState
import com.example.tarearetrofit.ui.nominas.NominasViewModel
import com.example.tarearetrofit.ui.nominas.NominasViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

/**
 * Pantalla que muestra la lista de nóminas del empleado.
 */
class NominasFragment : Fragment(R.layout.fragment_nominas) {

    private lateinit var binding: FragmentNominasBinding
    private val viewModel: NominasViewModel by lazy {
        val repository = NominasRepository()
        val factory = NominasViewModelFactory(repository)
        ViewModelProvider(this, factory)[NominasViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNominasBinding.bind(view)

        setupRecyclerView()
        observeViewModel()
        viewModel.loadMyNominas() // Solicita la carga de datos al entrar
    }

    private fun setupRecyclerView() {
        binding.recyclerViewNominas.layoutManager = LinearLayoutManager(requireContext())
    }

    /**
     * Observa los cambios de estado en las nóminas para actualizar la lista.
     */
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is NominasUiState.Loading -> {
                            // Aquí se podría mostrar un ProgressBar
                        }
                        is NominasUiState.Success -> {
                            // Inicializa el adaptador con la lista recibida
                            val adapter = NominasAdapter(state.list) { nomina ->
                                // Prepara los datos para pasar al fragmento de detalle
                                val bundle = Bundle().apply {
                                    putString("id_nomina", nomina.id_nomina)
                                    putString("uid", nomina.uid)
                                    putDouble("pago", nomina.pago)
                                    putLong("fecha_seconds", nomina.fecha.seconds)
                                    putInt("fecha_nanos", nomina.fecha.nanoseconds)
                                }
                                findNavController().navigate(R.id.action_nominasFragment_to_nominaDetailFragment, bundle)
                            }
                            binding.recyclerViewNominas.adapter = adapter
                        }
                        is NominasUiState.Error -> {
                            Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}
