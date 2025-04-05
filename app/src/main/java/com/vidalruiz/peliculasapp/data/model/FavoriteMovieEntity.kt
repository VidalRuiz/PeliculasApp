package com.vidalruiz.peliculasapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Author: Vidal Ruiz
 * Created on: April 4, 2025
 * Description: Entity representing a user's favorite movie for local persistence using Room.
 */

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey val id: Int,
    val titulo: String,
    val anio: Int,
    val director: String,
    val genero: String,
    val poster: String
)