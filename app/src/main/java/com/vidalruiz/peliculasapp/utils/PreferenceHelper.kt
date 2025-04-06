package com.vidalruiz.peliculasapp.utils

import android.content.Context
import android.content.SharedPreferences

/*
 * PreferenceHelper
 *
 * Objeto utilitario para leer y escribir la información del usuario en SharedPreferences.
 * Centraliza el acceso a nombre, sexo y categoría favorita.
 *
 * SharedPreferences se almacenan en un archivo XML en:
 * /data/data/<nombre_del_paquete>/shared_prefs/
 **/

object PreferenceHelper {

    /**
     * Obtiene la instancia de SharedPreferences usando el nombre definido en Constants.
    */
    private fun getPrefs(context: Context): SharedPreferences =
    context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)

    /**
     * Guarda el nombre, sexo y categoría favorita del usuario.
     *
     * @param context Contexto de la app
     * @param name Nombre del usuario
     * @param gender Sexo del usuario (Male, Female, Other)
     * @param category Género favorito (extraído del catálogo de géneros)
    */
    fun saveUserInfo(context: Context, name: String, gender: String, category: String) {
    getPrefs(context).edit()
    .putString(Constants.PREF_NOMBRE, name)
    .putString(Constants.PREF_SEXO, gender)
    .putString(Constants.PREF_GENERO, category)
    .apply()
    }

    /**
     * Devuelve el nombre del usuario o "Invitado" si no hay datos guardados.
    */
    fun getUserName(context: Context): String =
    getPrefs(context).getString(Constants.PREF_NOMBRE, "Invitado") ?: "Invitado"

    /**
     * Devuelve el sexo del usuario o "N/A" si no está definido.
    */
    fun getUserGender(context: Context): String =
    getPrefs(context).getString(Constants.PREF_SEXO, "N/A") ?: "N/A"

    /**
     * Devuelve el género favorito del usuario o "N/A" si no hay datos.
    */
    fun getUserCategory(context: Context): String =
    getPrefs(context).getString(Constants.PREF_GENERO, "N/A") ?: "N/A"
}