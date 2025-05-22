package com.example.gamershub.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.gamershub.model.Tema

class EditarTemaDialog(private val tema: Tema) : DialogFragment() {
    private var listener: OnTemaEditadoListener? = null

    fun setListener(listener: OnTemaEditadoListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        val editText = EditText(requireContext()).apply {
            hint = "Nuevo nombre del tema"
            setText(tema.nombre)
            inputType = InputType.TYPE_CLASS_TEXT
        }

        builder.setTitle("Editar Tema")
        builder.setView(editText)
        builder.setPositiveButton("Guardar") { _, _ ->
            val nuevoNombre = editText.text.toString().trim()
            if (nuevoNombre.isNotEmpty()) {
                listener?.onTemaEditado(tema, nuevoNombre)
            }
        }
        builder.setNegativeButton("Cancelar") { _, _ ->
            dialog?.cancel()
        }


        return builder.create()
    }

    interface OnTemaEditadoListener {
        fun onTemaEditado(tema: Tema, nuevoNombre: String)
    }
}