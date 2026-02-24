package com.example.tarearetrofit.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tarearetrofit.R
import com.example.tarearetrofit.databinding.FragmentSettingsBinding
import com.example.tarearetrofit.model.User
import com.example.tarearetrofit.repository.UserRepository
import com.example.tarearetrofit.ui.user.UserUiState
import com.example.tarearetrofit.ui.user.UserViewModel
import com.example.tarearetrofit.ui.user.UserViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

/**
 * Pantalla de configuración que permite editar los datos básicos del perfil.
 * (Funcionalidad similar a AccountFragment, adaptada para ajustes rápidos).
 */
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: UserViewModel by lazy {
        val repository = UserRepository()
        val factory = UserViewModelFactory(repository)
        ViewModelProvider(this, factory)[UserViewModel::class.java]
    }

    private var currentUser: User? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        setupListeners()
        observeViewModel()
        viewModel.loadUserData()
    }

    private fun setupListeners() {
        binding.btnSave.setOnClickListener {
            // Crea una copia del usuario con los nuevos valores de los campos
            val user = currentUser?.copy(
                nombre = binding.etNombre.text.toString(),
                apellido_1 = binding.etApellido1.text.toString(),
                apellido_2 = binding.etApellido2.text.toString(),
                dni = binding.etDni.text.toString(),
                telefono = binding.etTelefono.text.toString()
            )

            if (user != null) {
                viewModel.updateUserData(user)
            } else {
                Toast.makeText(requireContext(), "Error al guardar cambios", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UserUiState.Success -> {
                            currentUser = state.user
                            // Rellena los campos con la información actual
                            binding.etNombre.setText(state.user.nombre)
                            binding.etApellido1.setText(state.user.apellido_1)
                            binding.etApellido2.setText(state.user.apellido_2)
                            binding.etDni.setText(state.user.dni)
                            binding.etTelefono.setText(state.user.telefono)
                            
                            // Feedback al usuario si el botón estaba bloqueado (proceso de guardado previo)
                            if (binding.btnSave.isEnabled.not()) {
                                Toast.makeText(requireContext(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
                                binding.btnSave.isEnabled = true
                            }
                        }
                        is UserUiState.Loading -> {
                            binding.btnSave.isEnabled = false // Deshabilita el botón durante la red
                        }
                        is UserUiState.Error -> {
                            binding.btnSave.isEnabled = true
                            Toast.makeText(requireContext(), "Error al cargar/guardar datos", Toast.LENGTH_SHORT).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}
