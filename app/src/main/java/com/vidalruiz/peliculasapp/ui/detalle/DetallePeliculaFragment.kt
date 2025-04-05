package com.vidalruiz.peliculasapp.ui.detalle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.vidalruiz.peliculasapp.databinding.FragmentDetallePeliculaBinding
import com.vidalruiz.peliculasapp.data.model.Pelicula
import com.vidalruiz.peliculasapp.data.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetallePeliculaFragment : Fragment() {

    private var _binding: FragmentDetallePeliculaBinding? = null
    private val binding get() = _binding!!

    private var idPelicula: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idPelicula = arguments?.getInt("idPelicula") ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetallePeliculaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (idPelicula != -1) {
            obtenerPeliculaPorId(idPelicula)
        } else {
            Toast.makeText(requireContext(), "ID de película inválido", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(requireContext(), "Error al cargar película", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun mostrarPelicula(pelicula: Pelicula) {
        binding.tvTitulo.text = pelicula.titulo
        binding.tvDirector.text = "Director: ${pelicula.director}"
        binding.tvGenero.text = "Género: ${pelicula.genero}"
        binding.tvDuracion.text = "Duración: ${pelicula.duracion}"
        binding.tvIdioma.text = "Idioma: ${pelicula.idioma}"
        binding.tvSinopsis.text = "Sinopsis: ${pelicula.sinopsis}"

        Glide.with(requireContext())
            .load(pelicula.poster)
            .into(binding.imgPoster)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}