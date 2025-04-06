package com.vidalruiz.peliculasapp.ui.perfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.vidalruiz.peliculasapp.R
import com.vidalruiz.peliculasapp.databinding.FragmentPerfilBinding
import com.vidalruiz.peliculasapp.utils.JsonUtils
import com.vidalruiz.peliculasapp.utils.PreferenceHelper

/**
 * Fragmento que muestra y permite editar el perfil del usuario.
 * Usa PreferenceHelper para leer y guardar preferencias.
 * Carga catálogos desde archivos JSON en assets mediante JsonUtils.
 */
class PerfilFragment : Fragment() {

    private lateinit var binding: FragmentPerfilBinding

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
        binding.btnReset.setOnClickListener {
            mostrarDialogoReset()
        }
    }
    private fun mostrarDialogoReset() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.restablecer_perfil))
            .setMessage(getString(R.string.esto_eliminar_tus_preferencias_guardadas_y_te_llevar_de_nuevo_a_la_pantalla_de_bienvenida))
            .setPositiveButton(getString(R.string.aceptar)) { _, _ ->
                PreferenceHelper.clearUserInfo(requireContext())
                // Reiniciamos app desde la bienvenida
                val intent = requireActivity().intent
                requireActivity().finish()
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.cancelar), null)
            .show()
    }
    /**
     * Carga los datos del usuario desde SharedPreferences y los muestra.
     */
    private fun cargarDatos() {
        val nombre = PreferenceHelper.getUserName(requireContext()) ?: getString(R.string.anonimo)
        val sexo = PreferenceHelper.getUserGender(requireContext())
        val categoria = PreferenceHelper.getUserCategory(requireContext())

        binding.tvNombre.text = nombre
        binding.tvGenero.text = sexo
        binding.tvCategoria.text = categoria
    }

    /**
     * Muestra un diálogo que permite editar los datos del perfil.
     * Carga catálogos desde assets usando JsonUtils y guarda los cambios con PreferenceHelper.
     */
    private fun mostrarDialogoEditar() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_editar_perfil, null)

        val editNombre = dialogView.findViewById<EditText>(R.id.editNombre)
        val spinnerSexo = dialogView.findViewById<Spinner>(R.id.spinnerSexo)
        val spinnerGenero = dialogView.findViewById<Spinner>(R.id.spinnerGenero)

        val context = requireContext()

        // Valores actuales del usuario
        editNombre.setText(PreferenceHelper.getUserName(context))
        val sexoActual = PreferenceHelper.getUserGender(context)
        val generoActual = PreferenceHelper.getUserCategory(context)

        // Catálogo de sexos desde JsonUtils
        val sexos = JsonUtils.cargarSexosDesdeAssets(context)
        val adapterSexo = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, sexos.map { it.nombre })
        spinnerSexo.adapter = adapterSexo
        val sexoIndex = sexos.indexOfFirst { it.nombre.equals(sexoActual, ignoreCase = true) }.coerceAtLeast(0)
        spinnerSexo.setSelection(sexoIndex)

        // Catálogo de géneros desde JsonUtils
        val generos = JsonUtils.cargarGenerosDesdeAssets(context).mapNotNull { it.nombre }
        val adapterGenero = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, generos)
        spinnerGenero.adapter = adapterGenero
        val generoIndex = generos.indexOfFirst { it.equals(generoActual, ignoreCase = true) }.coerceAtLeast(0)
        spinnerGenero.setSelection(generoIndex)

        // Diálogo para editar datos
        AlertDialog.Builder(context)
            .setTitle(R.string.editar_perfil)
            .setView(dialogView)
            .setPositiveButton(R.string.guardar) { _, _ ->
                val nuevoNombre = editNombre.text.toString()
                val nuevoSexo = spinnerSexo.selectedItem.toString()
                val nuevaCategoria = spinnerGenero.selectedItem.toString()

                PreferenceHelper.saveUserInfo(context, nuevoNombre, nuevoSexo, nuevaCategoria)
                cargarDatos()
            }
            .setNegativeButton(R.string.cancelar, null)
            .show()
    }
}