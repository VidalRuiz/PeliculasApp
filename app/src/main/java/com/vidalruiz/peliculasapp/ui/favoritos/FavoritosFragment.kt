package com.vidalruiz.peliculasapp.ui.favoritos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vidalruiz.peliculasapp.databinding.FragmentFavoritosBinding
import com.vidalruiz.peliculasapp.ui.peliculas.PeliculasAdapter
import com.vidalruiz.peliculasapp.ui.peliculas.PeliculasViewModel
import com.vidalruiz.peliculasapp.util.toPelicula

class FavoritosFragment : Fragment() {

    private lateinit var binding: FragmentFavoritosBinding
    private val viewModel: PeliculasViewModel by viewModels()
    private lateinit var adapter: PeliculasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = PeliculasAdapter { /* Aquí podrías permitir eliminar favoritos si gustas */ }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.favoritos.observe(viewLifecycleOwner) { favoritos ->
            adapter.submitList(favoritos.map { it.toPelicula() })
            adapter.actualizarFavoritos(favoritos.map { it.id }.toSet())
        }
    }
}