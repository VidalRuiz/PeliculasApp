package com.vidalruiz.peliculasapp.ui.favoritos

import androidx.fragment.app.viewModels
import com.vidalruiz.peliculasapp.ui.peliculas.BasePeliculasFragment
import com.vidalruiz.peliculasapp.ui.peliculas.PeliculasViewModel
import com.vidalruiz.peliculasapp.utils.toPelicula

class FavoritosFragment : BasePeliculasFragment() {

    override val viewModel: PeliculasViewModel by viewModels()

    override fun observarPeliculas() {
        viewModel.favoritos.observe(viewLifecycleOwner) { favoritos ->
            val peliculas = favoritos.map { it.toPelicula() }
            adapter.submitList(peliculas)
            adapter.actualizarFavoritos(peliculas.map { it.id }.toSet())
            mostrarMensajeVacio(peliculas.isEmpty())
        }
    }
}