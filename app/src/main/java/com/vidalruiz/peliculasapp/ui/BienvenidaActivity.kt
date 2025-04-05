package com.vidalruiz.peliculasapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.vidalruiz.peliculasapp.R

/**
 * Author: Vidal Ruiz
 * Created on: April 4, 2025
 * Description: Welcome screen where users enter their name, gender, and favorite genre.
 * Information is saved to SharedPreferences for personalization. User may also enter anonymously.
 */

class BienvenidaActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var rgSexo: RadioGroup
    private lateinit var spGenero: Spinner
    private lateinit var btnContinuar: Button
    private lateinit var btnAnonimo: Button

    private val generos = listOf("Drama", "Crimen", "Comedia", "Terror", "Acción", "Bélica")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)

        etNombre = findViewById(R.id.etNombre)
        rgSexo = findViewById(R.id.rgSexo)
        spGenero = findViewById(R.id.spGenero)
        btnContinuar = findViewById(R.id.btnContinuar)
        btnAnonimo = findViewById(R.id.btnAnonimo)

        // Cargar opciones en el spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, generos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spGenero.adapter = adapter

        btnContinuar.setOnClickListener {
            guardarPreferencias()
            navegarAMain()
        }

        btnAnonimo.setOnClickListener {
            guardarAnonimo()
            navegarAMain()
        }
    }

    private fun guardarPreferencias() {
        val nombre = etNombre.text.toString()
        val generoFavorito = spGenero.selectedItem.toString()

        val sexo = when (rgSexo.checkedRadioButtonId) {
            R.id.rbMasculino -> "Masculino"
            R.id.rbFemenino -> "Femenino"
            R.id.rbOtro -> "Otro"
            else -> "No especificado"
        }

        val prefs = getSharedPreferences("usuario", MODE_PRIVATE)
        prefs.edit()
            .putBoolean("anonimo", false)
            .putString("nombre", nombre)
            .putString("sexo", sexo)
            .putString("genero", generoFavorito)
            .apply()
    }

    private fun guardarAnonimo() {
        val prefs = getSharedPreferences("usuario", MODE_PRIVATE)
        prefs.edit()
            .putBoolean("anonimo", true)
            .apply()
    }

    private fun navegarAMain() {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}