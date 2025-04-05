package com.vidalruiz.peliculasapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vidalruiz.peliculasapp.data.model.FavoriteMovieEntity

/**
 * Author: Vidal Ruiz
 * Created on: April 4, 2025
 * Description: DAO for managing user's favorite movies.
 */

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavorites(): LiveData<List<FavoriteMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(movie: FavoriteMovieEntity)

    @Delete
    suspend fun deleteFavorite(movie: FavoriteMovieEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE id = :movieId)")
    suspend fun isFavorite(movieId: Int): Boolean
}