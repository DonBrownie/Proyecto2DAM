package com.example.tarearetrofit.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tarearetrofit.R

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        view.findViewById<View>(R.id.card_reservas).setOnClickListener {
            findNavController().navigate(R.id.reservasFragment)
        }
        view.findViewById<View>(R.id.card_nominas).setOnClickListener {
            findNavController().navigate(R.id.nominasFragment)
        }
        view.findViewById<View>(R.id.card_calendario).setOnClickListener {
            findNavController().navigate(R.id.calendarioFragment)
        }
        view.findViewById<View>(R.id.card_perfil).setOnClickListener {
            findNavController().navigate(R.id.perfilFragment)
        }
    }
}
