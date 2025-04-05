package com.vidalruiz.peliculasapp.data.network

import com.vidalruiz.peliculasapp.data.model.Pelicula
import com.vidalruiz.peliculasapp.util.Endpoints
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Author: Vidal Ruiz
 * Created on: April 4, 2025
 * Description: Retrofit interface that defines the HTTP operations available for accessing
 * movie data from the API. It supports retrieving a list of movies and a specific movie by ID.
 */

interface PeliculaApiService {

    @GET(Endpoints.GET_PELICULAS)
    fun getPeliculas(): Call<List<Pelicula>>

    @GET("${Endpoints.GET_PELICULAS}/{id}")
    fun getPelicula(@Path("id") id: Int): Call<Pelicula>
}