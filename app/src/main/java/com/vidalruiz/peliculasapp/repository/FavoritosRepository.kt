package com.vidalruiz.peliculasapp.repository

import androidx.lifecycle.LiveData
import com.vidalruiz.peliculasapp.data.local.FavoriteMovieDao
import com.vidalruiz.peliculasapp.data.model.FavoriteMovieEntity

/**
 * Author: Vidal Ruiz
 * Created on: April 4, 2025
 * Description: Repository for accessing favorite movies using the DAO and Room database.
 */

class FavoritosRepository(private val favoriteMovieDao: FavoriteMovieDao) {

    val favoritos: LiveData<List<FavoriteMovieEntity>> = favoriteMovieDao.getAllFavorites()

    suspend fun agregarAFavoritos(pelicula: FavoriteMovieEntity) {
        favoriteMovieDao.insertFavorite(pelicula)
    }

    suspend fun eliminarDeFavoritos(pelicula: FavoriteMovieEntity) {
        favoriteMovieDao.deleteFavorite(pelicula)
    }

    suspend fun esFavorito(id: Int): Boolean {
        return favoriteMovieDao.isFavorite(id)
    }
}