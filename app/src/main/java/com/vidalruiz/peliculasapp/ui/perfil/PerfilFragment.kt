package com.vidalruiz.peliculasapp.ui.perfil

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.vidalruiz.peliculasapp.databinding.FragmentPerfilBinding
import com.vidalruiz.peliculasapp.R

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
        val editSexo = dialogView.findViewById<EditText>(R.id.editSexo)
        val editCategoria = dialogView.findViewById<EditText>(R.id.editCategoria)

        // Prellenar con los valores actuales
        editNombre.setText(prefs.getString("nombre", ""))
        editSexo.setText(prefs.getString("sexo", ""))
        editCategoria.setText(prefs.getString("categoria", ""))

        AlertDialog.Builder(requireContext())
            .setTitle("Editar Perfil")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = editNombre.text.toString()
                val sexo = editSexo.text.toString()
                val categoria = editCategoria.text.toString()

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
}