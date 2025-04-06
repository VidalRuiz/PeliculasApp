package com.vidalruiz.peliculasapp.ui.catalogo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vidalruiz.peliculasapp.R
import com.vidalruiz.peliculasapp.databinding.FragmentCatalogoBinding
import com.vidalruiz.peliculasapp.ui.detalle.DetallePeliculaFragment
import com.vidalruiz.peliculasapp.ui.peliculas.PeliculasAdapter
import com.vidalruiz.peliculasapp.ui.peliculas.PeliculasViewModel
import com.vidalruiz.peliculasapp.utils.toFavoriteEntity

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
        // Adapter con callbacks para detalle y favoritos
        adapter = PeliculasAdapter(
            onItemClick = { pelicula ->
                val detalleFragment = DetallePeliculaFragment().apply {
                    arguments = Bundle().apply {
                        putInt("idPelicula", pelicula.id)
                    }
                }

                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.nav_host_fragment, detalleFragment)
                    .addToBackStack(null)
                    .commit()
            },
            onAgregarAFavoritos = { pelicula ->
                viewModel.agregarAFavoritos(pelicula.toFavoriteEntity())
            }
        )

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