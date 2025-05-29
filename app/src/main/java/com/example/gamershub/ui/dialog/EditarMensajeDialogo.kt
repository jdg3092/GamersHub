package com.example.gamershub.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.gamershub.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase

class EditarMensajeDialogo : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mensajeTexto = requireArguments().getString("mensajeTexto") ?: ""
        val mensajeKey = requireArguments().getString("mensajeKey") ?: ""
        val temaId = requireArguments().getString("temaId") ?: ""

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val input = EditText(requireContext())
        input.setText(mensajeTexto)
        input.setSelection(mensajeTexto.length)

        builder.setTitle("Editar mensaje")
        builder.setView(input)
        builder.setPositiveButton("Guardar") { _, _ ->
            val nuevoTexto = input.text.toString().trim()
            if (nuevoTexto.isNotEmpty()) {
                val ref = FirebaseDatabase
                    .getInstance("https://gamershub-5a2e5-default-rtdb.europe-west1.firebasedatabase.app/")
                    .reference
                    .child("temas")
                    .child(temaId)
                    .child("mensajes")
                    .child(mensajeKey)
                    .child("texto")

                ref.setValue(nuevoTexto)
                    .addOnSuccessListener {
                        dismiss()
                    }
                    .addOnFailureListener {
                        Snackbar.make(
                            requireActivity().findViewById(android.R.id.content),
                            "Error al editar",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
            } else {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "El texto no puede estar vacío",
                    Snackbar.LENGTH_SHORT
                ).show()
            }


        }
        builder.setNegativeButton("Cancelar") { _, _ ->
            dialog?.cancel()
        }
        return builder.create()
    }
}