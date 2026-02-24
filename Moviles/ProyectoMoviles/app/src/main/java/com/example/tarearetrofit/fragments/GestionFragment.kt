package com.example.tarearetrofit.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tarearetrofit.R
import com.example.tarearetrofit.databinding.FragmentGestionBinding
import com.example.tarearetrofit.model.Cliente
import com.example.tarearetrofit.model.User
import com.example.tarearetrofit.repository.GestionRepository
import com.example.tarearetrofit.ui.gestion.GestionUiState
import com.example.tarearetrofit.ui.gestion.GestionViewModel
import com.example.tarearetrofit.ui.gestion.GestionViewModelFactory
import kotlinx.coroutines.launch

/**
 * Fragmento administrativo para la creación de nuevos empleados y alta de clientes.
 * Solo accesible para usuarios con rol de "Gerente".
 */
class GestionFragment : Fragment(R.layout.fragment_gestion) {

    private lateinit var binding: FragmentGestionBinding
    private val viewModel: GestionViewModel by viewModels {
        GestionViewModelFactory(GestionRepository())
    }

    private var isEmployeeTab = true // Controla si estamos en la pestaña de empleados o clientes

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGestionBinding.bind(view)

        setupTabs()
        setupListeners()
        observeViewModel()
    }

    /**
     * Configura el comportamiento de las pestañas para alternar entre Empleados y Clientes.
     */
    private fun setupTabs() {
        binding.btnTabEmpleado.setOnClickListener {
            switchToEmployeeTab()
        }
        binding.btnTabCliente.setOnClickListener {
            switchToClientTab()
        }
    }

    /**
     * Prepara la interfaz para crear un nuevo empleado.
     */
    private fun switchToEmployeeTab() {
        isEmployeeTab = true
        binding.btnTabEmpleado.setBackgroundTintList(android.content.res.ColorStateList.valueOf(resources.getColor(R.color.blue_700, null)))
        binding.btnTabEmpleado.setTextColor(resources.getColor(R.color.white, null))
        binding.btnTabCliente.setBackgroundTintList(android.content.res.ColorStateList.valueOf(resources.getColor(R.color.white, null)))
        binding.btnTabCliente.setTextColor(resources.getColor(R.color.blue_700, null))

        // Muestra campos específicos de empleado
        binding.llPuestoContainer.visibility = View.VISIBLE
        binding.llUsuarioContainer.visibility = View.VISIBLE
        binding.llPasswordContainer.visibility = View.VISIBLE
        binding.btnAction.text = "CREAR EMPLEADO"
        
        binding.tvUsuarioLabel.text = "Usuario Acceso"
        binding.etUsuario.hint = "jperez"
    }

    /**
     * Prepara la interfaz para dar de alta a un cliente.
     */
    private fun switchToClientTab() {
        isEmployeeTab = false
        binding.btnTabCliente.setBackgroundTintList(android.content.res.ColorStateList.valueOf(resources.getColor(R.color.blue_700, null)))
        binding.btnTabCliente.setTextColor(resources.getColor(R.color.white, null))
        binding.btnTabEmpleado.setBackgroundTintList(android.content.res.ColorStateList.valueOf(resources.getColor(R.color.white, null)))
        binding.btnTabEmpleado.setTextColor(resources.getColor(R.color.blue_700, null))

        // Oculta campos no necesarios para clientes
        binding.llPuestoContainer.visibility = View.GONE
        binding.llUsuarioContainer.visibility = View.VISIBLE
        binding.llPasswordContainer.visibility = View.GONE
        binding.btnAction.text = "ALTA CLIENTE"

        binding.tvUsuarioLabel.text = "Email"
        binding.etUsuario.hint = "cliente@example.com"
    }

    private fun setupListeners() {
        binding.btnAction.setOnClickListener {
            if (isEmployeeTab) {
                createEmployee()
            } else {
                createClient()
            }
        }
    }

    /**
     * Recopila datos y solicita al ViewModel crear un empleado.
     */
    private fun createEmployee() {
        val user = User(
            uid = binding.etUsuario.text.toString(), // Simplificación para el ejercicio
            nombre = binding.etNombre.text.toString(),
            apellido_1 = binding.etApellido1.text.toString(),
            puesto = binding.etPuesto.text.toString(),
            dni = binding.etDni.text.toString(),
            telefono = binding.etTelefono.text.toString(),
            email = binding.etUsuario.text.toString() + "@hotel.com"
        )
        if (validateFields()) {
            viewModel.addEmployee(user)
        }
    }

    /**
     * Recopila datos y solicita al ViewModel registrar un cliente.
     */
    private fun createClient() {
        val client = Cliente(
            nombre = binding.etNombre.text.toString(),
            apellido_1 = binding.etApellido1.text.toString(),
            dni = binding.etDni.text.toString(),
            telefono = binding.etTelefono.text.toString(),
            email = binding.etUsuario.text.toString()
        )
        if (validateFields()) {
            viewModel.addClient(client)
        }
    }

    /**
     * Validación simple de campos obligatorios.
     */
    private fun validateFields(): Boolean {
        if (binding.etNombre.text.isNullOrEmpty() || binding.etApellido1.text.isNullOrEmpty() || binding.etDni.text.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Por favor rellena los campos obligatorios", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is GestionUiState.Loading -> binding.btnAction.isEnabled = false
                        is GestionUiState.Success -> {
                            binding.btnAction.isEnabled = true
                            Toast.makeText(requireContext(), "Operación realizada con éxito", Toast.LENGTH_SHORT).show()
                            clearFields()
                            viewModel.resetState() // Vuelve a estado Idle tras el éxito
                        }
                        is GestionUiState.Error -> {
                            binding.btnAction.isEnabled = true
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> binding.btnAction.isEnabled = true
                    }
                }
            }
        }
    }

    /**
     * Limpia el formulario tras una inserción correcta.
     */
    private fun clearFields() {
        binding.etNombre.text.clear()
        binding.etApellido1.text.clear()
        binding.etPuesto.text.clear()
        binding.etDni.text.clear()
        binding.etTelefono.text.clear()
        binding.etUsuario.text.clear()
        binding.etPassword.text.clear()
    }
}
