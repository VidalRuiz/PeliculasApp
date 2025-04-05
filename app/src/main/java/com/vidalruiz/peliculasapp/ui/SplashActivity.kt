package com.vidalruiz.peliculasapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.vidalruiz.peliculasapp.R

/**
 * Author: Vidal Ruiz
 * Created on: April 4, 2025
 * Description: Displays the splash screen and navigates to welcome or main screen based on user data.
 */

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_PeliculasApp)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            if (esPrimeraVez()) {
                startActivity(Intent(this, BienvenidaActivity::class.java))
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)            } else {
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)            }
            finish()
        }, 2000)
    }

    private fun esPrimeraVez(): Boolean {
        val prefs = getSharedPreferences("usuario", MODE_PRIVATE)
        return !prefs.contains("anonimo") // Si no hay dato guardado aún
    }
}