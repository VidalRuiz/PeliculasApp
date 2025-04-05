package com.vidalruiz.peliculasapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vidalruiz.peliculasapp.R
import com.vidalruiz.peliculasapp.ui.catalogo.CatalogoFragment
import com.vidalruiz.peliculasapp.ui.favoritos.FavoritosFragment
import com.vidalruiz.peliculasapp.ui.perfil.PerfilFragment

/**
 * Author: Vidal Ruiz
 * Created on: April 5, 2025
 * Description: Main activity with BottomNavigationView that hosts three fragments:
 * Catálogo, Favoritos, and Perfil. Uses fragment transactions to switch views.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottomNavigationView)

        // Set default fragment
        if (savedInstanceState == null) {
            replaceFragment(CatalogoFragment())
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_catalogo -> {
                    replaceFragment(CatalogoFragment())
                    true
                }
                R.id.nav_favoritos -> {
                    replaceFragment(FavoritosFragment())
                    true
                }
                R.id.nav_perfil -> {
                    replaceFragment(PerfilFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }
}