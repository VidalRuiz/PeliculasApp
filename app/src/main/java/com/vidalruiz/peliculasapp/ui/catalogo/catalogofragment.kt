package com.vidalruiz.peliculasapp.ui.catalogo

import androidx.fragment.app.viewModels
import com.vidalruiz.peliculasapp.ui.peliculas.BasePeliculasFragment
import com.vidalruiz.peliculasapp.ui.peliculas.PeliculasViewModel

class CatalogoFragment : BasePeliculasFragment() {

    override val viewModel: PeliculasViewModel by viewModels()

    override fun observarPeliculas() {
        viewModel.listaPeliculas.observe(viewLifecycleOwner) { lista ->
            adapter.submitList(lista)
            mostrarMensajeVacio(lista.isEmpty())
        }

        viewModel.favoritos.observe(viewLifecycleOwner) { favoritos ->
            adapter.actualizarFavoritos(favoritos.map { it.id }.toSet())
        }

        viewModel.cargarPeliculas()
    }
}