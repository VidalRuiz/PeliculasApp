package com.vidalruiz.peliculasapp.data.network

import com.vidalruiz.peliculasapp.util.ApiConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Author: Vidal Ruiz
 * Created on: April 4, 2025
 * Description: Singleton object that provides a configured Retrofit instance
 * to access the PeliculaApiService. Uses Gson for JSON conversion.
 */

object RetrofitClient {
    val instance: PeliculaApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(PeliculaApiService::class.java)
    }
}