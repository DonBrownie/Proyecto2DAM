package com.example.tarearetrofit.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tarearetrofit.R

/**
 * Representa un día individual en la vista de calendario.
 */
data class CalendarDay(
    val day: String,
    var state: DayState = DayState.WHITE,
    val isCurrentMonth: Boolean = true
)

/**
 * Posibles estados (colores) que puede tener un día en el calendario.
 */
enum class DayState {
    WHITE, ORANGE, GREEN
}

/**
 * Adaptador personalizado para la cuadrícula del calendario.
 */
class CalendarAdapter(
    private var days: List<CalendarDay>,
    private val onDayClick: (CalendarDay) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardDay: CardView = view.findViewById(R.id.cardDay)
        val tvDayNumber: TextView = view.findViewById(R.id.tvDayNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_day, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val item = days[position]
        holder.tvDayNumber.text = item.day
        
        // Estilo visual si el día no pertenece al mes actual
        if (!item.isCurrentMonth) {
            holder.tvDayNumber.setTextColor(Color.LTGRAY)
            holder.cardDay.setCardBackgroundColor(Color.TRANSPARENT)
            holder.itemView.isEnabled = false
        } else {
            holder.tvDayNumber.setTextColor(Color.BLACK)
            holder.itemView.isEnabled = true
            
            // Asignación de colores según el estado guardado
            when (item.state) {
                DayState.WHITE -> holder.cardDay.setCardBackgroundColor(Color.WHITE)
                DayState.ORANGE -> holder.cardDay.setCardBackgroundColor(Color.parseColor("#FFCC80"))
                DayState.GREEN -> holder.cardDay.setCardBackgroundColor(Color.parseColor("#A5D6A7"))
            }

            holder.itemView.setOnClickListener {
                // Ciclo interactivo de estados: Blanco -> Naranja -> Verde -> Blanco
                item.state = when (item.state) {
                    DayState.WHITE -> DayState.ORANGE
                    DayState.ORANGE -> DayState.GREEN
                    DayState.GREEN -> DayState.WHITE
                }
                notifyItemChanged(position)
                onDayClick(item)
            }
        }
    }

    override fun getItemCount(): Int = days.size

    /**
     * Refresca la lista de días mostrada.
     */
    fun updateDays(newDays: List<CalendarDay>) {
        days = newDays
        notifyDataSetChanged()
    }
}
