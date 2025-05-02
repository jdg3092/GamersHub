package com.example.gamershub.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.gamershub.R
import com.google.firebase.auth.FirebaseAuth

class RegisterDialog:DialogFragment() {

    private lateinit var auth: FirebaseAuth

    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Registro de usuario correcto")
        builder.setMessage("Quieres realizar el Login directamente")
        builder.setPositiveButton("SI"){_,_->
            findNavController().navigate(R.id.action_registerDialog_to_mainFragment)
        }
        builder.setNegativeButton("NO"){_,_->
            findNavController().navigate(R.id.action_registerDialog_to_loginFragment)
            auth.signOut()
        }
        return builder.create()
    }
}