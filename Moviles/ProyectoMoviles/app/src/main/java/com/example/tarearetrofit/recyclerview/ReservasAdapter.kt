package com.example.tarearetrofit.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tarearetrofit.databinding.ItemLayoutBinding
import com.example.tarearetrofit.model.Reserva

/**
 * Adaptador para listar las reservas en un RecyclerView.
 */
class ReservasAdapter(
    private val list: List<Reserva>,
    private val onItemClick: (Reserva) -> Unit // Callback para gestionar el clic
) : RecyclerView.Adapter<ReservasAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Usa el layout genérico de items para visualizar los datos
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = list.size

    /**
     * Contenedor de vista que mapea los campos de una [Reserva].
     */
    class ViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reserva: Reserva) {
            binding.tvName.text = "DNI Cliente: ${reserva.dni_cliente}"
            binding.tvGender.text = "Entrada: ${reserva.fecha_inicio}"
            binding.tvHeight.text = "Salida: ${reserva.fecha_fin}"
        }
    }
}
