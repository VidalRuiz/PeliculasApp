package com.vidalruiz.peliculasapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vidalruiz.peliculasapp.data.model.Pelicula
import com.vidalruiz.peliculasapp.data.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import com.vidalruiz.peliculasapp.utils.LogTags

/**
 * Author: Vidal Ruiz
 * Created on: April 4, 2025
 * Description: Repository class responsible for handling the data operations related to movies.
 * It fetches movie data from the API via Retrofit and exposes it through LiveData.
 */
class PeliculaRepository {

    private val _peliculas = MutableLiveData<List<Pelicula>>()
    val peliculas: LiveData<List<Pelicula>> = _peliculas

    fun obtenerPeliculas() {
        RetrofitClient.api.getPeliculas().enqueue(object : Callback<List<Pelicula>> {
            override fun onResponse(
                call: Call<List<Pelicula>>,
                response: Response<List<Pelicula>>
            ) {
                if (response.isSuccessful) {
                    _peliculas.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<Pelicula>>, t: Throwable) {
                Log.d(LogTags.PELICULA_REPO, "Error fetching movies: ${t.message}")
                _peliculas.postValue(emptyList())
            }
        })
    }
}