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
import com.example.tarearetrofit.databinding.FragmentReservasBinding
import com.example.tarearetrofit.recyclerview.ReservasAdapter
import com.example.tarearetrofit.repository.ReservasRepository
import com.example.tarearetrofit.ui.reservas.ReservasUiState
import com.example.tarearetrofit.ui.reservas.ReservasViewModel
import com.example.tarearetrofit.ui.reservas.ReservasViewModelFactory
import android.app.DatePickerDialog
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

/**
 * Fragmento que gestiona la visualización y creación de nuevas reservas de hotel.
 */
class ReservasFragment : Fragment(R.layout.fragment_reservas) {

    private lateinit var binding: FragmentReservasBinding
    private val viewModel: ReservasViewModel by lazy {
        val repository = ReservasRepository()
        val factory = ReservasViewModelFactory(repository)
        ViewModelProvider(this, factory)[ReservasViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReservasBinding.bind(view)

        setupRecyclerView()
        observeViewModel()
        setupListeners()
        viewModel.loadReservas() // Carga inicial de reservas
    }

    private fun setupRecyclerView() {
        binding.recyclerViewReservas.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupListeners() {
        // Al pulsar el botón flotante se abre el diálogo para añadir reserva
        binding.fabAddReserva.setOnClickListener {
            showAddReservaDialog()
        }
    }

    /**
     * Muestra un AlertDialog con un formulario para registrar una nueva reserva.
     */
    private fun showAddReservaDialog() {
        val builder = android.app.AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_reserva, null)
        builder.setView(dialogView)
        builder.setTitle("Nueva Reserva")

        val etDni = dialogView.findViewById<android.widget.EditText>(R.id.et_dni_cliente)
        val etRoom = dialogView.findViewById<android.widget.EditText>(R.id.et_room_number)
        val etStart = dialogView.findViewById<android.widget.EditText>(R.id.et_fecha_inicio)
        val etEnd = dialogView.findViewById<android.widget.EditText>(R.id.et_fecha_fin)

        etStart.setOnClickListener { showDatePicker(etStart) }
        etEnd.setOnClickListener { showDatePicker(etEnd) }

        builder.setPositiveButton("Añadir") { _, _ ->
            val currentUser = com.example.tarearetrofit.repository.FirebaseProvider.auth.currentUser
            val res = com.example.tarearetrofit.model.Reserva(
                id_reserva = "", 
                uid = currentUser?.uid ?: "desconocido",
                dni_cliente = etDni.text.toString(),
                numero_habitacion = etRoom.text.toString().toIntOrNull() ?: 0,
                fecha_inicio = etStart.text.toString(),
                fecha_fin = etEnd.text.toString()
            )
            viewModel.addReserva(res)
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    /**
     * Muestra un DatePickerDialog y pone la fecha seleccionada en el EditText.
     */
    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
            editText.setText(formattedDate)
        }, year, month, day)

        dpd.show()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is ReservasUiState.Loading -> {
                            // Carga en proceso
                        }
                        is ReservasUiState.Success -> {
                            val adapter = ReservasAdapter(state.list) { reserva ->
                                // Navegación al detalle con argumentos
                                val bundle = Bundle().apply {
                                    putString("id_reserva", reserva.id_reserva)
                                    putString("uid", reserva.uid)
                                    putString("dni_cliente", reserva.dni_cliente)
                                    putInt("numero_habitacion", reserva.numero_habitacion)
                                    putString("fecha_inicio", reserva.fecha_inicio)
                                    putString("fecha_fin", reserva.fecha_fin)
                                }
                                findNavController().navigate(R.id.action_reservasFragment_to_reservaDetailFragment, bundle)
                            }
                            binding.recyclerViewReservas.adapter = adapter
                        }
                        is ReservasUiState.Error -> {
                            Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}
