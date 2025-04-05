package com.vidalruiz.peliculasapp.ui.catalogo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vidalruiz.peliculasapp.databinding.FragmentCatalogoBinding
import com.vidalruiz.peliculasapp.ui.peliculas.PeliculasAdapter
import com.vidalruiz.peliculasapp.ui.peliculas.PeliculasViewModel
import com.vidalruiz.peliculasapp.util.toFavoriteEntity

class CatalogoFragment : Fragment() {

    private lateinit var binding: FragmentCatalogoBinding
    private val viewModel: PeliculasViewModel by viewModels()
    private lateinit var adapter: PeliculasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Adapter con callback para agregar a favoritos
        adapter = PeliculasAdapter { pelicula ->
            viewModel.agregarAFavoritos(pelicula.toFavoriteEntity())
        }

        // Configurar RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Observar lista de películas
        viewModel.listaPeliculas.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        // Observar lista de favoritos
        viewModel.favoritos.observe(viewLifecycleOwner) { favoritos ->
            adapter.actualizarFavoritos(favoritos.map { it.id }.toSet())
        }

        // Cargar películas al iniciar
        viewModel.cargarPeliculas()
    }
}