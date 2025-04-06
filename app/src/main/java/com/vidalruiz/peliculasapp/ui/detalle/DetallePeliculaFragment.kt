package com.vidalruiz.peliculasapp.ui.detalle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.vidalruiz.peliculasapp.R
import com.vidalruiz.peliculasapp.data.model.Pelicula
import com.vidalruiz.peliculasapp.databinding.FragmentDetallePeliculaBinding
import com.vidalruiz.peliculasapp.data.network.RetrofitClient
import com.vidalruiz.peliculasapp.repository.FavoritosRepository
import com.vidalruiz.peliculasapp.utils.toFavoriteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetallePeliculaFragment : Fragment() {

    private var _binding: FragmentDetallePeliculaBinding? = null
    private val binding get() = _binding!!

    private var idPelicula: Int = -1
    private lateinit var favoritosRepository: FavoritosRepository
    private var esFavorita = false
    private lateinit var peliculaActual: Pelicula

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idPelicula = arguments?.getInt("idPelicula") ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetallePeliculaBinding.inflate(inflater, container, false)
        favoritosRepository = FavoritosRepository(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (idPelicula != -1) {
            obtenerPeliculaPorId(idPelicula)
        } else {
            Toast.makeText(requireContext(), getString(R.string.id_pelicula_invalido), Toast.LENGTH_SHORT).show()
        }

        binding.btnFavorito.setOnClickListener {
            if (::peliculaActual.isInitialized) {
                toggleFavorito()
            }
        }
    }

    private fun obtenerPeliculaPorId(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val pelicula = RetrofitClient.api.obtenerPeliculaPorId(id)
                withContext(Dispatchers.Main) {
                    mostrarPelicula(pelicula)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), getString(R.string.error_cargar_pelicula), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun mostrarPelicula(pelicula: Pelicula) {
        peliculaActual = pelicula

        binding.tvTitulo.text = pelicula.titulo
        binding.tvDirector.text = getString(R.string.prefix_director) + pelicula.director
        binding.tvGenero.text = getString(R.string.prefix_genero) + pelicula.genero
        binding.tvDuracion.text = getString(R.string.prefix_duracion) + pelicula.duracion
        binding.tvIdioma.text = getString(R.string.prefix_idioma) + pelicula.idioma
        binding.tvSinopsis.text = getString(R.string.prefix_sinopsis) + pelicula.sinopsis

        Glide.with(requireContext())
            .load(pelicula.poster)
            .into(binding.imgPoster)

        // Verificar si ya está en favoritos
        CoroutineScope(Dispatchers.IO).launch {
            esFavorita = favoritosRepository.esFavorito(pelicula.id)
            withContext(Dispatchers.Main) {
                actualizarTextoBotonFavorito()
            }
        }
    }

    private fun actualizarTextoBotonFavorito() {
        binding.btnFavorito.text = if (esFavorita)
            getString(R.string.remove_from_favorites)
        else
            getString(R.string.add_to_favorites)
    }

    private fun toggleFavorito() {
        CoroutineScope(Dispatchers.IO).launch {
            if (esFavorita) {
                favoritosRepository.eliminarDeFavoritos(peliculaActual.toFavoriteEntity())
                esFavorita = false
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), getString(R.string.removed_from_favorites), Toast.LENGTH_SHORT).show()
                    actualizarTextoBotonFavorito()
                }
            } else {
                favoritosRepository.agregarAFavoritos(peliculaActual.toFavoriteEntity())
                esFavorita = true
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show()
                    actualizarTextoBotonFavorito()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}