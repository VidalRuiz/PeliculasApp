package com.vidalruiz.peliculasapp.util

import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper {
    private const val PREF_NAME = "user_preferences"
    private const val KEY_NAME = "user_name"
    private const val KEY_GENDER = "user_gender"
    private const val KEY_CATEGORY = "user_category"

    private fun getPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveUserInfo(context: Context, name: String, gender: String, category: String) {
        getPrefs(context).edit()
            .putString(KEY_NAME, name)
            .putString(KEY_GENDER, gender)
            .putString(KEY_CATEGORY, category)
            .apply()
    }

    fun getUserName(context: Context): String = getPrefs(context).getString(KEY_NAME, "Invitado") ?: "Invitado"
    fun getUserGender(context: Context): String = getPrefs(context).getString(KEY_GENDER, "N/A") ?: "N/A"
    fun getUserCategory(context: Context): String = getPrefs(context).getString(KEY_CATEGORY, "N/A") ?: "N/A"
}