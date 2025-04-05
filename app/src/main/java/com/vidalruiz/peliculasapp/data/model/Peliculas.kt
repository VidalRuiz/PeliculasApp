package com.vidalruiz.peliculasapp.data.model

/**
 * Author: Vidal Ruiz
 * Created on: April 4, 2025
 * Description: Data class representing a movie entity fetched from the API.
 * It contains basic information such as title, year, director, genre, and poster URL.
 */

data class Pelicula(
    val id: Int,
    val titulo: String,
    val anio: Int,
    val director: String,
    val genero: String,
    val poster: String,
    val duracion: String? = null,
    val idioma: String? = null,
    val sinopsis: String? = null
)