package com.vidalruiz.peliculasapp.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vidalruiz.peliculasapp.data.model.Genero

import com.vidalruiz.peliculasapp.data.model.FavoriteMovieEntity
import com.vidalruiz.peliculasapp.data.model.Pelicula

/**
 * Author: Vidal Ruiz
 * Created on: April 4, 2025
 * Description: Centralized object for defining API endpoint paths.
 */
object Endpoints {
    const val GET_PELICULAS = "peliculas"
    const val GET_PELICULA_POR_ID = "peliculas/{id}"
}

/**
 * Author: Vidal Ruiz
 * Created on: April 4, 2025
 * Description: Holds base configuration values for the API.
 */
object ApiConfig {
    const val BASE_URL = "https://private-d60fbf-peliculasfavoritas.apiary-mock.com/"


}

/**
 * Author: Vidal Ruiz
 * Created on: April 4, 2025
 * Description: Contains constant values used for logging throughout the app.
 */
object LogTags {
    const val PELICULA_REPO = "PeliculaRepository"
}

fun Pelicula.toFavoriteEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = this.id,
        titulo = this.titulo,
        anio = this.anio,
        director = this.director,
        genero = this.genero,
        poster = this.poster
    )
}

fun FavoriteMovieEntity.toPelicula(): Pelicula {
    return Pelicula(
        id = this.id,
        titulo = this.titulo,
        anio = this.anio,
        director = this.director,
        genero = this.genero,
        poster = this.poster
    )
}

fun cargarGenerosDesdeAssets(context: Context): List<Genero> {
    val json = context.assets.open("generos.json")
        .bufferedReader()
        .use { it.readText() }

    val tipoLista = object : TypeToken<List<Genero>>() {}.type
    return Gson().fromJson(json, tipoLista)
}