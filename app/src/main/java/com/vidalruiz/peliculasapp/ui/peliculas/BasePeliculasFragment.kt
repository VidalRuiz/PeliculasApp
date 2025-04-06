package com.vidalruiz.peliculasapp.ui.peliculas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vidalruiz.peliculasapp.R
import com.vidalruiz.peliculasapp.databinding.FragmentPeliculasBinding
import com.vidalruiz.peliculasapp.ui.detalle.DetallePeliculaFragment
import com.vidalruiz.peliculasapp.utils.toFavoriteEntity

abstract class BasePeliculasFragment : Fragment() {

    protected lateinit var binding: FragmentPeliculasBinding
    protected lateinit var adapter: PeliculasAdapter
    protected abstract val viewModel: PeliculasViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPeliculasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = PeliculasAdapter(
            onItemClick = { abrirDetalle(it.id) },
            onToggleFavorito = { pelicula, agregar ->
                if (agregar) viewModel.agregarAFavoritos(pelicula.toFavoriteEntity())
                else viewModel.eliminarDeFavoritos(pelicula.toFavoriteEntity())
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        observarPeliculas()
    }

    protected abstract fun observarPeliculas()

    private fun abrirDetalle(idPelicula: Int) {
        val detalleFragment = DetallePeliculaFragment().apply {
            arguments = Bundle().apply {
                putInt("idPelicula", idPelicula)
            }
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.nav_host_fragment, detalleFragment)
            .addToBackStack(null)
            .commit()
    }
    protected fun mostrarMensajeVacio(mostrar: Boolean) {
        binding.tvEmptyMessage.visibility = if (mostrar) View.VISIBLE else View.GONE
    }
}