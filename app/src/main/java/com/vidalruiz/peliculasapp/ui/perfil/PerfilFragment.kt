package com.vidalruiz.peliculasapp.ui.perfil

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vidalruiz.peliculasapp.R
import com.vidalruiz.peliculasapp.databinding.FragmentPerfilBinding
import java.io.InputStreamReader
import com.vidalruiz.peliculasapp.data.model.Genero

class PerfilFragment : Fragment() {

    private lateinit var binding: FragmentPerfilBinding

    private val prefs by lazy {
        requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cargarDatos()

        binding.btnEditar.setOnClickListener {
            mostrarDialogoEditar()
        }
    }

    private fun cargarDatos() {
        val nombre = prefs.getString("nombre", "Invitado")
        val sexo = prefs.getString("sexo", "N/A")
        val categoria = prefs.getString("categoria", "N/A")

        binding.tvNombre.text = nombre
        binding.tvGenero.text = sexo
        binding.tvCategoria.text = categoria
    }

    private fun mostrarDialogoEditar() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_editar_perfil, null)

        val editNombre = dialogView.findViewById<EditText>(R.id.editNombre)
        val spinnerSexo = dialogView.findViewById<Spinner>(R.id.spinnerSexo)
        val spinnerGenero = dialogView.findViewById<Spinner>(R.id.spinnerGenero)

        editNombre.setText(prefs.getString("nombre", ""))

        // Catálogo de sexos
        val sexos = listOf("Masculino", "Femenino", "Otro")
        spinnerSexo.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, sexos)
        spinnerSexo.setSelection(sexos.indexOf(prefs.getString("sexo", "Masculino")))

        // Catálogo desde generos.json
        val generos = leerGenerosDesdeAssets().filterNotNull()
        val generoActual = prefs.getString("categoria", null) ?: generos.firstOrNull().orEmpty()

        val adapterGenero = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, generos)
        spinnerGenero.adapter = adapterGenero

        // Asegura que el género actual exista en la lista
        val selectedIndex = generos.indexOfFirst { it.equals(generoActual, ignoreCase = true) }.coerceAtLeast(0)
        spinnerGenero.setSelection(selectedIndex)

        AlertDialog.Builder(requireContext())
            .setTitle("Editar Perfil")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = editNombre.text.toString()
                val sexo = spinnerSexo.selectedItem.toString()
                val categoria = spinnerGenero.selectedItem.toString()

                prefs.edit()
                    .putString("nombre", nombre)
                    .putString("sexo", sexo)
                    .putString("categoria", categoria)
                    .apply()

                cargarDatos()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun leerGenerosDesdeAssets(): List<String> {
        return try {
            val inputStream = requireContext().assets.open("generos.json")
            val reader = InputStreamReader(inputStream)
            val tipoLista = object : TypeToken<List<Genero>>() {}.type
            val lista = Gson().fromJson<List<Genero>>(reader, tipoLista)
            lista.mapNotNull { it.nombre }
        } catch (e: Exception) {
            e.printStackTrace()
            listOf("Drama", "Acción", "Comedia") // fallback
        }
    }
}