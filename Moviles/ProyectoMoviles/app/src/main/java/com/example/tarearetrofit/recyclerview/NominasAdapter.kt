package com.example.tarearetrofit.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tarearetrofit.databinding.ItemLayoutBinding
import com.example.tarearetrofit.model.Nomina
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Adaptador para mostrar la lista de nóminas en un RecyclerView.
 */
class NominasAdapter(
    private val list: List<Nomina>,
    private val onItemClick: (Nomina) -> Unit // Acción al pulsar una nómina
) : RecyclerView.Adapter<NominasAdapter.ViewHolder>() {

    // Formato de fecha para mostrar en cada elemento
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Infla el layout de cada elemento de la lista (reutilizado)
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, dateFormat)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = list.size

    /**
     * ViewHolder que vincula los datos de una [Nomina] con la vista.
     */
    class ViewHolder(private val binding: ItemLayoutBinding, private val dateFormat: SimpleDateFormat) : RecyclerView.ViewHolder(binding.root) {
        fun bind(nomina: Nomina) {
            binding.tvName.text = "Pago: ${String.format("%.2f", nomina.pago)}€"
            binding.tvGender.text = "Fecha: ${dateFormat.format(nomina.fecha.toDate())}"
            binding.tvHeight.text = "ID: ${nomina.id_nomina}"
        }
    }
}
