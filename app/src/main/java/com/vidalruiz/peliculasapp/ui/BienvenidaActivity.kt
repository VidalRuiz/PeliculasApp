package com.vidalruiz.peliculasapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vidalruiz.peliculasapp.R
import com.vidalruiz.peliculasapp.databinding.ActivityBienvenidaBinding
import com.vidalruiz.peliculasapp.data.model.Genero
import com.vidalruiz.peliculasapp.data.model.Sexo
import com.vidalruiz.peliculasapp.util.PreferenceHelper
import java.io.InputStreamReader

/**
 * BienvenidaActivity
 *
 * Pantalla de bienvenida que solicita al usuario su nombre, sexo y género favorito de películas.
 * Esta información se guarda en SharedPreferences utilizando PreferenceHelper.
 *
 * Los géneros se cargan desde un archivo JSON local (assets/generos.json).
 * El sexo se elige desde un Spinner con valores predefinidos ("Male", "Female", "Other").
 *
 * @author Vidal Ruiz
 * @created April 5, 2025
 */
class BienvenidaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBienvenidaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBienvenidaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarSpinners()

        binding.btnContinuar.setOnClickListener {
            guardarPreferenciasYContinuar()
        }
    }

    /**
     * Configura los spinners de sexo y género.
     * El spinner de género carga los datos desde el archivo JSON de la carpeta assets.
     */
    private fun configurarSpinners() {
        // Spinner Sexo (desde sexos.json)
        val sexos = cargarSexos()
        val adapterSexo = ArrayAdapter(this, android.R.layout.simple_spinner_item, sexos.map { it.nombre })
        adapterSexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSexo.adapter = adapterSexo

        // Spinner Géneros (desde generos.json)
        val generos = cargarGeneros()
        val adapterGenero = ArrayAdapter(this, android.R.layout.simple_spinner_item, generos.map { it.nombre })
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGenero.adapter = adapterGenero
    }


    private fun cargarSexos(): List<Sexo> {
        return try {
            val inputStream = assets.open("sexos.json")
            val reader = InputStreamReader(inputStream)
            val tipoLista = object : TypeToken<List<Sexo>>() {}.type
            Gson().fromJson(reader, tipoLista)
        } catch (e: Exception) {
            e.printStackTrace()
            listOf(
                Sexo("Male", "Masculino"),
                Sexo("Female", "Femenino"),
                Sexo("Other", "Otro")
            ) // fallback
        }
    }
    /**
     * Carga el catálogo de géneros desde el archivo generos.json en assets.
     *
     * @return Lista de objetos Genero
     */
    private fun cargarGeneros(): List<Genero> {
        val inputStream = assets.open("generos.json")
        val reader = InputStreamReader(inputStream)
        val tipoLista = object : TypeToken<List<Genero>>() {}.type
        return Gson().fromJson(reader, tipoLista)
    }

    /**
     * Valida los datos ingresados y los guarda usando PreferenceHelper.
     * Luego redirige al usuario a la pantalla principal (MainActivity).
     */
    private fun guardarPreferenciasYContinuar() {
        val nombre = binding.etNombre.text.toString().trim()
        val sexo = binding.spinnerSexo.selectedItem.toString()
        val generoFavorito = binding.spinnerGenero.selectedItem.toString()

        if (nombre.isEmpty()) {
            binding.etNombre.error = getString(R.string.campo_obligatorio)
            return
        }

        // Guardar preferencias usando la clase centralizada PreferenceHelper
        PreferenceHelper.saveUserInfo(
            context = this,
            name = nombre,
            gender = sexo,
            category = generoFavorito
        )

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}