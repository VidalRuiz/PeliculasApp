package com.vidalruiz.peliculasapp.utils

import com.vidalruiz.peliculasapp.data.model.FavoriteMovieEntity
import com.vidalruiz.peliculasapp.data.model.Pelicula

fun Pelicula.toFavoriteEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = id,
        titulo = titulo,
        anio = anio,
        director = director,
        genero = genero,
        poster = poster
    )
}

fun FavoriteMovieEntity.toPelicula(): Pelicula {
    return Pelicula(
        id = id,
        titulo = titulo,
        anio = anio,
        director = director,
        genero = genero,
        poster = poster
    )
}