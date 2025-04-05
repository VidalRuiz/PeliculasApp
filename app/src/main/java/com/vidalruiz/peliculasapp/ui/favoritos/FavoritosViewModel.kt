package com.vidalruiz.peliculasapp.ui.favoritos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vidalruiz.peliculasapp.data.local.AppDatabase
import com.vidalruiz.peliculasapp.data.model.FavoriteMovieEntity
import com.vidalruiz.peliculasapp.repository.FavoritosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Author: Vidal Ruiz
 * Created on: April 4, 2025
 * Description: ViewModel that manages user's favorite movies using Room.
 * Provides methods to add, remove, check and observe favorites.
 */

class FavoritosViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavoritosRepository

    val favoritos: LiveData<List<FavoriteMovieEntity>>

    init {
        val dao = AppDatabase.getDatabase(application).favoriteMovieDao()
        repository = FavoritosRepository(dao)
        favoritos = repository.favoritos
    }

    fun agregarAFavoritos(pelicula: FavoriteMovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.agregarAFavoritos(pelicula)
        }
    }

    fun eliminarDeFavoritos(pelicula: FavoriteMovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.eliminarDeFavoritos(pelicula)
        }
    }

    fun esFavorito(id: Int, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultado = repository.esFavorito(id)
            callback(resultado)
        }
    }
}