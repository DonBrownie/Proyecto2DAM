package com.example.tarearetrofit.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tarearetrofit.databinding.FilmLayoutBinding


class FilmAdapter(var films: List<Film>, val characterName: String) : RecyclerView.Adapter<FilmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = FilmLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(films[position])
    }

    override fun getItemCount(): Int = films.size

}

class FilmViewHolder(val binding: FilmLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind (film: Film) {
        binding.tvTitleFilm.text = film.title
        binding.tvDirector.text = "Director: ${film.director}"
        binding.tvReleaseDate.text = "Estreno: ${film.release_date}"
    }

}