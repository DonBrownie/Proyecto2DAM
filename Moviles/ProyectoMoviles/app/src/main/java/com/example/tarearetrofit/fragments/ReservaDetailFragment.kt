package com.example.tarearetrofit.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.tarearetrofit.R
import com.example.tarearetrofit.databinding.FragmentReservaDetailBinding

/**
 * Pantalla que muestra los datos pormenorizados de una reserva.
 */
class ReservaDetailFragment : Fragment(R.layout.fragment_reserva_detail) {

    private lateinit var binding: FragmentReservaDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReservaDetailBinding.bind(view)

        // Obtiene los datos pasados desde ReservasFragment
        val idReserva = arguments?.getString("id_reserva") ?: "-"
        val dni = arguments?.getString("dni_cliente") ?: "-"
        val room = arguments?.getInt("numero_habitacion") ?: 0
        val start = arguments?.getString("fecha_inicio") ?: "-"
        val end = arguments?.getString("fecha_fin") ?: "-"

        // Muestra la información en los campos correspondientes
        binding.tvDetailIdReserva.text = idReserva
        binding.tvDetailDni.text = dni
        binding.tvDetailRoom.text = room.toString()
        binding.tvDetailStart.text = start
        binding.tvDetailEnd.text = end
    }
}
