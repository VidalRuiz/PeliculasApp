package com.vidalruiz.peliculasapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.vidalruiz.peliculasapp.R
import com.vidalruiz.peliculasapp.databinding.ActivityBienvenidaBinding
import com.vidalruiz.peliculasapp.utils.JsonUtils
import com.vidalruiz.peliculasapp.utils.PreferenceHelper

/**
 * BienvenidaActivity
 *
 * Pantalla de bienvenida que solicita al usuario su nombre, sexo y género favorito de películas.
 * Esta información se guarda en SharedPreferences utilizando PreferenceHelper.
 *
 * Los géneros y sexos se cargan desde archivos JSON locales (assets).
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
     */
    private fun configurarSpinners() {
        // Spinner Sexo
        val sexos = JsonUtils.cargarSexosDesdeAssets(this)
        val adapterSexo = ArrayAdapter(this, android.R.layout.simple_spinner_item, sexos.map { it.nombre })
        adapterSexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSexo.adapter = adapterSexo

        // Spinner Género
        val generos = JsonUtils.cargarGenerosDesdeAssets(this)
        val adapterGenero = ArrayAdapter(this, android.R.layout.simple_spinner_item, generos.map { it.nombre })
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGenero.adapter = adapterGenero
    }

    /**
     * Valida los datos ingresados y los guarda usando PreferenceHelper.
     */
    private fun guardarPreferenciasYContinuar() {
        val nombre = binding.etNombre.text.toString().trim()
        val sexo = binding.spinnerSexo.selectedItem.toString()
        val generoFavorito = binding.spinnerGenero.selectedItem.toString()

        if (nombre.isEmpty()) {
            binding.etNombre.error = getString(R.string.campo_obligatorio)
            return
        }

        // Guardar en SharedPreferences
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