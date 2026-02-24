package com.example.tarearetrofit

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import com.example.tarearetrofit.repository.DataInitializer

/**
 * Actividad principal que sirve como contenedor para la navegación de la aplicación.
 * Maneja el BottomNavigationView y los insets del sistema.
 */
class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita el diseño de borde a borde (transparencia de barras)
        setContentView(R.layout.activity_main)

        // Configuración del NavHost y NavController para la navegación entre fragmentos
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        // Vincula el menú de navegación inferior con el controlador de navegación
        bottomNav.setupWithNavController(navController)

        // Listener para ocultar/mostrar la barra de navegación según el fragmento actual
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.loginFragment -> bottomNav.visibility = View.GONE // Oculto en el login
                else -> bottomNav.visibility = View.VISIBLE
            }
        }

        // Determina qué opciones del menú mostrar basándose en el rol del usuario
        checkUserRoleAndSetupNav(bottomNav)
    }

    /**
     * Verifica el puesto del usuario para habilitar funciones administrativas (Gestión).
     */
    private fun checkUserRoleAndSetupNav(bottomNav: BottomNavigationView) {
        lifecycleScope.launch {
            val repository = com.example.tarearetrofit.repository.UserRepository()
            val user = repository.getUserData()
            // El menú de Gestión solo es visible para gerentes
            if (user?.puesto == "Gerente") {
                bottomNav.menu.findItem(R.id.gestionFragment).isVisible = true
            }
        }
    }
}