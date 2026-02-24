package com.example.tarearetrofit.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import android.widget.Toast
import com.example.tarearetrofit.R
import com.example.tarearetrofit.databinding.FragmentAccountBinding
import com.example.tarearetrofit.model.User
import com.example.tarearetrofit.repository.UserRepository
import com.example.tarearetrofit.ui.user.UserUiState
import com.example.tarearetrofit.ui.user.UserViewModel
import com.example.tarearetrofit.ui.user.UserViewModelFactory
import kotlinx.coroutines.launch

/**
 * Fragmento que gestiona la configuración de la cuenta y perfil del usuario.
 * Permite visualizar y editar la información personal del empleado.
 */
class AccountFragment : Fragment(R.layout.fragment_account) {

    private lateinit var binding: FragmentAccountBinding
    private val viewModel: UserViewModel by lazy {
        val repository = UserRepository()
        val factory = UserViewModelFactory(repository)
        ViewModelProvider(this, factory)[UserViewModel::class.java]
    }

    private var currentUser: User? = null
    private var isEditMode = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAccountBinding.bind(view)

        setupListeners()
        observeViewModel()
        viewModel.loadUserData() // Carga los datos al iniciar
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnEdit.setOnClickListener {
            toggleEditMode(true) // Activa el modo edición
        }

        binding.btnSave.setOnClickListener {
            saveChanges() // Guarda los cambios
        }
    }

    /**
     * Alterna la habilitación de los campos de texto según si se está editando o no.
     */
    private fun toggleEditMode(enable: Boolean) {
        isEditMode = enable
        binding.etNombre.isEnabled = enable
        binding.etApellido1.isEnabled = enable
        binding.etApellido2.isEnabled = enable
        binding.etDni.isEnabled = enable
        binding.etTelefono.isEnabled = enable
        
        // Cambia la visibilidad de los botones de Guardar/Editar
        binding.btnEdit.visibility = if (enable) View.GONE else View.VISIBLE
        binding.btnSave.visibility = if (enable) View.VISIBLE else View.GONE
    }

    /**
     * Recopila los datos de la vista y solicita la actualización al ViewModel.
     */
    private fun saveChanges() {
        val updatedUser = currentUser?.copy(
            nombre = binding.etNombre.text.toString(),
            apellido_1 = binding.etApellido1.text.toString(),
            apellido_2 = binding.etApellido2.text.toString(),
            dni = binding.etDni.text.toString(),
            telefono = binding.etTelefono.text.toString()
        )

        if (updatedUser != null) {
            viewModel.updateUserData(updatedUser)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UserUiState.Success -> {
                            currentUser = state.user
                            // Solo actualiza los campos si no estamos editando (para evitar sobrescribir lo que escribe el usuario)
                            if (!isEditMode) {
                                binding.etNombre.setText(state.user.nombre)
                                binding.etApellido1.setText(state.user.apellido_1)
                                binding.etApellido2.setText(state.user.apellido_2)
                                binding.etDni.setText(state.user.dni)
                                binding.etTelefono.setText(state.user.telefono)
                            } else {
                                Toast.makeText(requireContext(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
                                toggleEditMode(false)
                            }
                        }
                        is UserUiState.Error -> {
                            Toast.makeText(requireContext(), "Error al cargar o guardar datos", Toast.LENGTH_SHORT).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}
