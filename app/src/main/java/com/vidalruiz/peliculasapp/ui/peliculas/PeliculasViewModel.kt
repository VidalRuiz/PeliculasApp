package com.vidalruiz.peliculasapp.ui.peliculas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vidalruiz.peliculasapp.data.model.Pelicula
import com.vidalruiz.peliculasapp.repository.PeliculaRepository
import com.vidalruiz.peliculasapp.data.local.AppDatabase
import com.vidalruiz.peliculasapp.data.model.FavoriteMovieEntity
import com.vidalruiz.peliculasapp.repository.FavoritosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Author: Vidal Ruiz
 * Created on: April 4, 2025
 * Description: ViewModel for the Peliculas screen. It exposes LiveData of movie lists
 * retrieved from the repository and provides a method to trigger data loading.
 * Part of the MVVM architecture, it separates the UI logic from the data source.
 */

class PeliculasViewModel(application: Application) : AndroidViewModel(application) {

    private val peliculaRepository = PeliculaRepository()

    private val favoritosRepository: FavoritosRepository by lazy {
        val dao = AppDatabase.getDatabase(application).favoriteMovieDao()
        FavoritosRepository(dao)
    }

    val listaPeliculas: LiveData<List<Pelicula>> = peliculaRepository.peliculas

    // ✅ Esto es lo que faltaba para que tus fragmentos compilen
    val favoritos: LiveData<List<FavoriteMovieEntity>> = favoritosRepository.favoritos

    fun cargarPeliculas() {
        peliculaRepository.obtenerPeliculas()
    }

    fun agregarAFavoritos(favorita: FavoriteMovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favoritosRepository.agregarAFavoritos(favorita)
        }
    }

    fun eliminarDeFavoritos(favorita: FavoriteMovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favoritosRepository.eliminarDeFavoritos(favorita)
        }
    }

    fun esFavorito(id: Int, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultado = favoritosRepository.esFavorito(id)
            callback(resultado)
        }
    }
}