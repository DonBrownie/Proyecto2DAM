package com.example.tarearetrofit.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tarearetrofit.R
import com.example.tarearetrofit.databinding.FragmentPerfilBinding
import com.example.tarearetrofit.repository.UserRepository
import com.example.tarearetrofit.ui.user.UserUiState
import com.example.tarearetrofit.ui.user.UserViewModel
import com.example.tarearetrofit.ui.user.UserViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

/**
 * Fragmento que muestra un resumen del perfil y opciones de gestión de cuenta.
 */
class PerfilFragment : Fragment(R.layout.fragment_perfil) {

    private lateinit var binding: FragmentPerfilBinding
    private val viewModel: UserViewModel by lazy {
        val repository = UserRepository()
        val factory = UserViewModelFactory(repository)
        ViewModelProvider(this, factory)[UserViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPerfilBinding.bind(view)

        setupListeners()
        observeViewModel()
        viewModel.loadUserData()
    }

    private fun setupListeners() {
        // Cierra la sesión en Firebase y vuelve a la pantalla de Login
        binding.llLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_global_loginFragment)
        }

        // Navega a la edición de datos personales
        binding.llAccount.setOnClickListener {
            findNavController().navigate(R.id.action_perfilFragment_to_accountFragment)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UserUiState.Success -> {
                            val user = state.user
                            // Muestra el nombre completo del empleado en la cabecera
                            binding.tvUserName.text = "${user.nombre} ${user.apellido_1} ${user.apellido_2}"
                        }
                        is UserUiState.Error -> {
                            binding.tvUserName.text = "Error al cargar datos"
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}
