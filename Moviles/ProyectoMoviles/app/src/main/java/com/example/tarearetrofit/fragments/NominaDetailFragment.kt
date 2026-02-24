package com.example.tarearetrofit.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.tarearetrofit.R
import com.example.tarearetrofit.databinding.FragmentNominaDetailBinding
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Pantalla que muestra la información detallada de una nómina específica.
 */
class NominaDetailFragment : Fragment(R.layout.fragment_nomina_detail) {

    private lateinit var binding: FragmentNominaDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNominaDetailBinding.bind(view)

        // Recupera los datos pasados a través del Bundle desde el fragmento anterior
        val idNomina = arguments?.getString("id_nomina") ?: ""
        val pago = arguments?.getDouble("pago") ?: 0.0
        val seconds = arguments?.getLong("fecha_seconds") ?: 0L
        val nanoseconds = arguments?.getInt("fecha_nanos") ?: 0

        // Reconstruye el Timestamp de Firebase para formatear la fecha
        val timestamp = Timestamp(seconds, nanoseconds)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        // Asigna los valores a los componentes de la interfaz
        binding.tvDetailIdNomina.text = idNomina
        binding.tvDetailPago.text = "${String.format("%.2f", pago)}€"
        binding.tvDetailFecha.text = dateFormat.format(timestamp.toDate())
    }
}
