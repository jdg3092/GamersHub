package com.example.gamershub.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar

class TemasDialog: DialogFragment() {
    private var listener: OnTemaCreadoListener? = null

    fun setListener(listener: OnTemaCreadoListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        val editText = EditText(requireContext()).apply {
            hint = "Escribe el nombre del tema"
            inputType = InputType.TYPE_CLASS_TEXT
        }
        builder.setTitle("Temas")
        builder.setView(editText)
        builder.setPositiveButton("OK"){_,_->
            val nombreTema = editText.text.toString().trim()
            if (nombreTema.isNotEmpty()) {
                listener?.onTemaCreado(nombreTema)
                Log.d("TemasDialog", "OK pulsado. Tema: $nombreTema")

            } else {
                Snackbar.make(requireView(), "El nombre del tema no puede estar vacío", Snackbar.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar"){_,_->
            dialog?.cancel()
        }


        return builder.create()
    }
    interface OnTemaCreadoListener {
        fun onTemaCreado(nombre: String)
    }
}