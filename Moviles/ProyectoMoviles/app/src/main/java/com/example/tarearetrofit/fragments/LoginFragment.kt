package com.example.tarearetrofit.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tarearetrofit.R
import com.example.tarearetrofit.databinding.FragmentLoginBinding
import com.example.tarearetrofit.ui.auth.AuthUiState
import com.example.tarearetrofit.ui.auth.AuthViewModel
import kotlinx.coroutines.launch

/**
 * Fragmento encargado de la pantalla de inicio de sesión.
 * Maneja la entrada del usuario y la navegación tras la autenticación.
 */
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        setupListeners()
        observeViewModel()
    }

    /**
     * Configura los eventos de interacción de la vista.
     */
    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etUsername.text.toString()
            val pass = binding.etPassword.text.toString()

            // Validación básica de campos vacíos
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                viewModel.login(email, pass)
            } else {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Escucha el estado del ViewModel para reaccionar a cambios en la autenticación.
     */
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is AuthUiState.Loading -> {
                            binding.btnLogin.isEnabled = false // Bloquea el botón durante la carga
                        }
                        is AuthUiState.Success -> {
                            binding.btnLogin.isEnabled = true
                            // Navega al menú principal si el login es correcto
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                        is AuthUiState.Error -> {
                            binding.btnLogin.isEnabled = true
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            binding.btnLogin.isEnabled = true
                        }
                    }
                }
            }
        }
    }
}
