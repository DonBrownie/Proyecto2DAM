package com.example.tarearetrofit.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tarearetrofit.R
import com.example.tarearetrofit.repository.UserRepository
import kotlinx.coroutines.launch

/**
 * Menú principal de la aplicación.
 * Permite cambiar entre las distintas secciones mediante un sistema de tarjetas (Cards).
 */
class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Navegación a las distintas secciones al pulsar las tarjetas
        view.findViewById<View>(R.id.card_reservas).setOnClickListener {
            findNavController().navigate(R.id.reservasFragment)
        }
        view.findViewById<View>(R.id.card_nominas).setOnClickListener {
            findNavController().navigate(R.id.nominasFragment)
        }
        view.findViewById<View>(R.id.card_calendario).setOnClickListener {
            findNavController().navigate(R.id.calendarioFragment)
        }
        view.findViewById<View>(R.id.card_gestion).setOnClickListener {
            findNavController().navigate(R.id.gestionFragment)
        }
        view.findViewById<View>(R.id.card_perfil).setOnClickListener {
            findNavController().navigate(R.id.perfilFragment)
        }

        // Verifica si el usuario tiene permiso para ver el panel de Gestión
        checkUserRole(view)
    }

    /**
     * Muestra el botón de Gestión solo si el usuario tiene el puesto de "Gerente".
     */
    private fun checkUserRole(view: View) {
        viewLifecycleOwner.lifecycleScope.launch {
            val repository = UserRepository()
            val user = repository.getUserData()
            if (user?.puesto == "Gerente") {
                view.findViewById<View>(R.id.card_gestion).visibility = View.VISIBLE
            }
        }
    }
}
