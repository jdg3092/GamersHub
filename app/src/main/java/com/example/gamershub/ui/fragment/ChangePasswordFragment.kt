package com.example.gamershub.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gamershub.R
import com.example.gamershub.databinding.FragmentChangepasswordBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangepasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangepasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.btnCambiarPassword.setOnClickListener {
            val currentUser = auth.currentUser
            val oldPassword = binding.editOldPassword.text.toString()
            val newPassword = binding.editNewPassword.text.toString()
            val repeatPassword = binding.editRepeatPassword.text.toString()

            if (newPassword != repeatPassword) {
                Snackbar.make(binding.root, "Las contraseñas no coinciden", Snackbar.LENGTH_SHORT)
                    .show()
                return@setOnClickListener

            }

            val email = currentUser?.email
            if (email != null) {
                val credential = EmailAuthProvider.getCredential(email, oldPassword)

                currentUser.reauthenticate(credential).addOnSuccessListener {
                    currentUser.updatePassword(newPassword).addOnSuccessListener {
                        Snackbar.make(
                            binding.root,
                            "Contraseña actualizada correctamente",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.action_changePasswordFragment_to_profileInfoAccountFragment)
                    }.addOnFailureListener {
                        Snackbar.make(
                            binding.root,
                            "Error al actualizar la contraseña",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }.addOnFailureListener {
                    Snackbar.make(
                        binding.root,
                        "Contraseña actual incorrecta",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}