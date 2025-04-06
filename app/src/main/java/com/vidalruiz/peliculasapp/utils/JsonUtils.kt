package com.vidalruiz.peliculasapp.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vidalruiz.peliculasapp.data.model.Genero
import com.vidalruiz.peliculasapp.data.model.Sexo
import java.io.InputStreamReader

object JsonUtils {
    fun cargarGenerosDesdeAssets(context: Context): List<Genero> {
        return try {
            val json = context.assets.open("generos.json")
                .bufferedReader()
                .use { it.readText() }

            val tipoLista = object : TypeToken<List<Genero>>() {}.type
            Gson().fromJson(json, tipoLista)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun cargarSexosDesdeAssets(context: Context): List<Sexo> {
        return try {
            val inputStream = context.assets.open("sexos.json")
            val reader = InputStreamReader(inputStream)
            val tipoLista = object : TypeToken<List<Sexo>>() {}.type
            Gson().fromJson(reader, tipoLista)
        } catch (e: Exception) {
            e.printStackTrace()
            listOf(
                Sexo("Male", "Masculino"),
                Sexo("Female", "Femenino"),
                Sexo("Other", "Otro")
            ) // Fallback por si falla la carga
        }
    }
}