package com.vidalruiz.peliculasapp.ui.peliculas

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vidalruiz.peliculasapp.R
import com.vidalruiz.peliculasapp.data.model.Pelicula

/**
 * Author: Vidal Ruiz
 * Created on: April 4, 2025
 * Description: RecyclerView Adapter used to bind a list of Pelicula objects
 * to their corresponding UI card elements for display in a movie list.
 * Includes support for selecting movies as favorites.
 */

class PeliculasAdapter(
    private val onAgregarAFavoritos: (Pelicula) -> Unit
) : RecyclerView.Adapter<PeliculasAdapter.PeliculaViewHolder>() {

    private var peliculas: List<Pelicula> = emptyList()
    private var favoritos: Set<Int> = emptySet()

    fun submitList(lista: List<Pelicula>) {
        peliculas = lista
        notifyDataSetChanged()
    }

    fun actualizarFavoritos(lista: Set<Int>) {
        favoritos = lista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pelicula, parent, false)
        return PeliculaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        holder.bind(peliculas[position])
    }

    override fun getItemCount(): Int = peliculas.size

    inner class PeliculaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
        private val tvGenero: TextView = itemView.findViewById(R.id.tvGenero)
        private val tvAnio: TextView = itemView.findViewById(R.id.tvAnio)
        private val tvDirector: TextView = itemView.findViewById(R.id.tvDirector)
        private val imgPoster: ImageView = itemView.findViewById(R.id.imgPoster)
        private val btnFavorito: ImageButton = itemView.findViewById(R.id.btnFavorito)

        fun bind(pelicula: Pelicula) {
            val context = itemView.context

            tvTitulo.text = pelicula.titulo
            tvGenero.text = context.getString(R.string.prefix_genero) + pelicula.genero
            tvAnio.text = context.getString(R.string.prefix_anio) + pelicula.anio
            tvDirector.text = context.getString(R.string.prefix_director) + pelicula.director

            try {
                Glide.with(context)
                    .load(pelicula.poster)
                    .placeholder(R.drawable.placeholder)
                    .into(imgPoster)
            } catch (e: Exception) {
                Log.d("PeliculasAdapter", "Image load failed: ${e.message}")
            }

            // Cambiar el ícono del botón dependiendo si es favorito o no
            val esFavorito = favoritos.contains(pelicula.id)
            btnFavorito.setImageResource(
                if (esFavorito) R.drawable.ic_star_border else R.drawable.ic_star
            )

            btnFavorito.setOnClickListener {
                onAgregarAFavoritos(pelicula)
            }
        }
    }
}