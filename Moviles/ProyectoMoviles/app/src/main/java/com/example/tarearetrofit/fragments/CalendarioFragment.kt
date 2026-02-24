package com.example.tarearetrofit.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tarearetrofit.R
import com.example.tarearetrofit.adapter.CalendarAdapter
import com.example.tarearetrofit.adapter.CalendarDay
import com.example.tarearetrofit.adapter.DayState
import com.example.tarearetrofit.databinding.FragmentCalendarioBinding
import com.example.tarearetrofit.repository.CalendarRepository
import com.example.tarearetrofit.ui.calendar.CalendarUiState
import com.example.tarearetrofit.ui.calendar.CalendarViewModel
import com.example.tarearetrofit.ui.calendar.CalendarViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*

/**
 * Fragmento que implementa un calendario interactivo para gestionar turnos o estados diarios.
 */
class CalendarioFragment : Fragment(R.layout.fragment_calendario) {

    private lateinit var binding: FragmentCalendarioBinding
    private lateinit var calendarAdapter: CalendarAdapter
    private var calendar = Calendar.getInstance() // Instancia para manejar el mes actual visualizado

    private val viewModel: CalendarViewModel by lazy {
        val repository = CalendarRepository()
        val factory = CalendarViewModelFactory(repository)
        ViewModelProvider(this, factory)[CalendarViewModel::class.java]
    }

    // Mapa temporal que almacena los estados de los días cargados de Firestore
    private var eventsMap = mapOf<String, DayState>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarioBinding.bind(view)

        setupRecyclerView()
        setupListeners()
        observeViewModel()
        viewModel.loadEvents() // Carga inicial de datos de Firestore
    }

    private fun setupRecyclerView() {
        calendarAdapter = CalendarAdapter(emptyList()) { day ->
            // Al hacer clic en un día del calendario, se actualiza su estado en la base de datos
            val dateKey = getDateKey(day.day.toInt())
            viewModel.updateDayState(dateKey, day.state)
        }
        binding.rvCalendar.apply {
            layoutManager = GridLayoutManager(requireContext(), 7) // Rejilla de 7 columnas (días de la semana)
            adapter = calendarAdapter
        }
    }

    private fun setupListeners() {
        // Navegación entre meses
        binding.btnPrevMonth.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateCalendar()
        }
        binding.btnNextMonth.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateCalendar()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is CalendarUiState.Success -> {
                            eventsMap = state.events
                            updateCalendar() // Redibuja el calendario con los datos nuevos
                        }
                        is CalendarUiState.Error -> {
                            // Gestión de errores
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    /**
     * Genera un identificador de fecha único (String) para usarlo como clave en Firestore.
     */
    private fun getDateKey(day: Int): String {
        val cal = calendar.clone() as Calendar
        cal.set(Calendar.DAY_OF_MONTH, day)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return sdf.format(cal.time)
    }

    /**
     * Calcula y redibuja los días del mes actual en la cuadrícula.
     */
    private fun updateCalendar() {
        val daysList = mutableListOf<CalendarDay>()
        
        val cal = calendar.clone() as Calendar
        cal.set(Calendar.DAY_OF_MONTH, 1) // Ir al primer día del mes

        val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale("es", "ES"))
        binding.tvMonthYear.text = monthYearFormat.format(calendar.time).replaceFirstChar { it.uppercase() }

        // Calcula el desplazamiento inicial (huecos vacíos antes del día 1)
        val firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
        val offset = if (firstDayOfWeek == Calendar.SUNDAY) 6 else firstDayOfWeek - 2

        for (i in 0 until offset) {
            daysList.add(CalendarDay("", isCurrentMonth = false))
        }

        // Añade cada día del mes con su estado correspondiente
        val daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 1..daysInMonth) {
            val dateKey = getDateKey(i)
            val state = eventsMap[dateKey] ?: DayState.WHITE
            daysList.add(CalendarDay(i.toString(), state = state))
        }

        calendarAdapter.updateDays(daysList)
    }
}
